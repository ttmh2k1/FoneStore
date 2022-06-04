package hcmute.fonestore.Activity;

import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import hcmute.fonestore.Animation.SwipeToDeleteCallback;
import hcmute.fonestore.MainActivity;
import hcmute.fonestore.R;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.RandomString;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCart;

public class CartActivity extends AppCompatActivity {
    ArrayList<Product> lstCart = new ArrayList<>();
    ImageView back;
    ConstraintLayout constraintLayout;
    RecyclerView recyclerViewCart;
    ProgressBar loadingView;
    LinearLayout linearLayout;
    Button btnContinue, order;
    boolean check;

    FirebaseAuth mAuth;

    DatabaseReference ref;

    RandomString randomString = new RandomString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        back = findViewById(R.id.cart_back);
        constraintLayout = findViewById(R.id.layout_cart);
        recyclerViewCart = findViewById(R.id.recyclerView_cart);
        loadingView = findViewById(R.id.loading_view_cart);
        linearLayout = findViewById(R.id.layout_noProduct);
        btnContinue = findViewById(R.id.cart_continue);
        order = findViewById(R.id.cart_order);

        loadData();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lstCart.size()>0){
                    orderCart(lstCart);
                }
                else
                    Toast.makeText(CartActivity.this, "Không có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void orderCart(ArrayList<Product> lstPro){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_bill);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        dialog.show();

        Button btnOk = dialog.findViewById(R.id.btnOK);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser currentUser = mAuth.getCurrentUser();
                ref = FirebaseDatabase.getInstance().getReference().child("bills").child(currentUser.getUid());
                for(Product item : lstPro) {
                    FirebaseDatabase.getInstance().getReference().child("bills").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean check = false; // false add true update
                            for(DataSnapshot i : snapshot.getChildren()){
                                if(i.child("id").getValue().equals(item.getId()) && i.child("status").getValue().equals("wait")){
                                    Log.e("addtoBill", "111111111");
                                    i.getRef().child("quantity").setValue(Long.parseLong(i.child("quantity").getValue().toString()) + item.getQuantity(), new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            deleteFromCart(item.getId());
                                            Intent intent = new Intent(CartActivity.this, BillActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    check = true;
                                }
                            }
                            if (check == false){

                                String key = randomString.nextString();
                                ref.child(key).setValue(item);
                                ref.child(key).child("status").setValue("wait", new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        deleteFromCart(item.getId());
                                        Toast.makeText(CartActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(CartActivity.this, BillActivity.class);
                                        startActivity(intent);
                                    }
                    });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(layoutManager2);
        final RecyclerViewAdapterCart myAdapter = new RecyclerViewAdapterCart(CartActivity.this, lstCart, new RecyclerViewAdapterCart.IBtnQuantityClick() {
            @Override
            public void addQuantity(Product product) {
                Log.e("start add", product.getId());
                ref.child("cart").child(currentUser.getUid()).child(product.getId()).child("quantity").setValue(product.getQuantity()+1);
            }

            @Override
            public void subQuantity(Product product) {
                if(product.getQuantity() > 1){
                    ref.child("cart").child(currentUser.getUid()).child(product.getId()).child("quantity").setValue(product.getQuantity()-1);
                }

            }
        });
        recyclerViewCart.setAdapter(myAdapter);

        FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstCart.clear();
                loadingView.setVisibility(GONE);
                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);

//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                    FirebaseDatabase.getInstance().getReference().child("product").child(dataSnapshot1.getValue().toString()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Product p = snapshot.getValue(Product.class);
//                            p.setId(dataSnapshot1.getValue().toString());
//                            lstCart.add(p);
//                            myAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(CartActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Product tempPro = ds.getValue(Product.class);
                    Log.e("product: ", tempPro.getName());
                    lstCart.add(tempPro);
                }
                myAdapter.notifyDataSetChanged();

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(CartActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Product item = myAdapter.getData().get(position);
                        check = true;

                        myAdapter.removeItem(position);
                        Snackbar snackbar = Snackbar
                                .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                check = false;
                                myAdapter.restoreItem(item, position);
                                recyclerViewCart.scrollToPosition(position);
                            }
                        });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (check) {
                                    FirebaseDatabase
                                            .getInstance()
                                            .getReference()
                                            .child("cart")
                                            .child(currentUser.getUid())
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot i : snapshot.getChildren()){
                                                if (i.getKey().equals(item.getId())) {
                                                    i.getRef().removeValue();
                                                    Log.e("delete Item", "swipe to delete");
                                                }
                                            }
//                                            for (DataSnapshot ds : snapshot.getChildren()) {
//                                                if (ds.getValue().toString().equals(item.getId())) {
//                                                    FirebaseDatabase
//                                                            .getInstance()
//                                                            .getReference()
//                                                            .child("cart")
//                                                            .child(currentUser.getUid()).child(ds.getKey()).removeValue()
//                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            if (task.isSuccessful()) {
//                                                                Toast.makeText(CartActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                            else {
//                                                                Toast.makeText(CartActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        }
//                                                    });
//                                                }
//                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(CartActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }, 2900);
                    }
                };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
                itemTouchhelper.attachToRecyclerView(recyclerViewCart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CartActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void deleteFromCart(String proID){
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid()).child(proID).removeValue();
    }
}
