package hcmute.fonestore.Activity;

import static android.view.View.GONE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.Animation.SwipeToDeleteCallback;
import hcmute.fonestore.MainActivity;
import hcmute.fonestore.Object.CartItem;
import hcmute.fonestore.R;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCart;

public class CartActivity extends AppCompatActivity {
    ArrayList<CartItem> lstCart = new ArrayList<>();
    ImageView back;
    ConstraintLayout constraintLayout;
    RecyclerView recyclerViewCart;
    ProgressBar loadingView;
    LinearLayout linearLayout;
    Button btnContinue, order;
    boolean check;

    FirebaseAuth mAuth;

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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PlaceOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(layoutManager2);
        final RecyclerViewAdapterCart myAdapter = new RecyclerViewAdapterCart(CartActivity.this, lstCart);
        recyclerViewCart.setAdapter(myAdapter);

        FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstCart.clear();
                loadingView.setVisibility(GONE);
                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartItem c = ds.getValue(CartItem.class);
                    c.setKey(ds.getKey());
                    lstCart.add(c);
                    myAdapter.notifyDataSetChanged();
                }
                myAdapter.notifyDataSetChanged();

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(CartActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        int position = viewHolder.getAdapterPosition();
                        CartItem c = myAdapter.getData().get(position);

                        new AlertDialog.Builder(CartActivity.this)
                                .setTitle("Xóa sản phẩm khỏi giỏ hàng")
                                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm " + c.getProduct().getName() + " khỏi giỏ hàng không?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        FirebaseDatabase
                                                .getInstance()
                                                .getReference()
                                                .child("cart")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child(c.getKey())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    myAdapter.removeItem(position);
                                                    myAdapter.notifyItemChanged(position);
                                                    Toast.makeText(CartActivity.this, "Xóa sản phẩm khỏi giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    myAdapter.notifyItemChanged(position);
                                                    Toast.makeText(CartActivity.this, "Xóa sản phẩm khỏi giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }})
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        myAdapter.notifyItemChanged(position);
                                    }
                                }).show();
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
}
