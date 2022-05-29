package hcmute.fonestore.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.fonestore.MainActivity;
import hcmute.fonestore.R;
import hcmute.fonestore.Object.Product;

public class boughtActivity extends AppCompatActivity {

//    DatabaseReference delete;
//    Query reference, delete1;
    ArrayList<Product> lstBought = new ArrayList<>();
    ImageView back;
//    FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    RecyclerView recyclerViewCart;
//    FirebaseStorage storage = FirebaseStorage.getInstance();
    ProgressBar loadingView;
    LinearLayout linearLayout;
    Button next;
    boolean j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought);

        back = findViewById(R.id.bought_back);
        constraintLayout = findViewById(R.id.layout_bought);
        recyclerViewCart = findViewById(R.id.recyclerView_bought);
        loadingView = findViewById(R.id.loading_view_bought);
        linearLayout = findViewById(R.id.layoutdb_noProduct);
        next = findViewById(R.id.bought_continue);

        loadData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(boughtActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
//        mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser currentUser = mAuth.getCurrentUser();

//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerViewCart.setLayoutManager(layoutManager2);
//
//        reference = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("seller").equalTo(currentUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstDaban.clear();
//                loadingView.setVisibility(GONE);
//                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    lstBought.add(p);
//                }
//                final RecyclerViewAdapterCart myAdapter = new RecyclerViewAdapterCart(boughtActivity.this, lstBought);
//                recyclerViewCart.setAdapter(myAdapter);
//
//                swipeToDeleteCallback swipeToDeleteCallback = new swipeToDeleteCallback(boughtActivity.this) {
//                    @Override
//                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                        final int position = viewHolder.getAdapterPosition();
//                        final product item = myAdapter.getData().get(position);
//                        j = true;
//
//                        myAdapter.removeItem(position);
//                        Snackbar snackbar = Snackbar
//                                .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                        snackbar.setAction("UNDO", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                j = false;
//                                myAdapter.restoreItem(item, position);
//                                recyclerViewCart.scrollToPosition(position);
//                            }
//                        });
//                        snackbar.setActionTextColor(Color.YELLOW);
//                        snackbar.show();
//
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (j) {
//                                    StorageReference photoRef = storage.getReferenceFromUrl(item.getImage());
//
//                                    delete = FirebaseDatabase.getInstance().getReference().child("product").child(item.getName());
//                                    delete.removeValue();
//
//                                    delete1 = FirebaseDatabase.getInstance().getReference().child("notifications").child(currentUser.getUid()).orderByChild("product").equalTo(item.getName());
//                                    delete1.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            for (DataSnapshot data : dataSnapshot.getChildren())
//                                                data.getRef().removeValue();
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//
//                                    photoRef.delete();
//                                }
//                            }
//                        }, 2900);
//
//                    }
//                };
//                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
//                itemTouchhelper.attachToRecyclerView(recyclerViewCart);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(boughtActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
