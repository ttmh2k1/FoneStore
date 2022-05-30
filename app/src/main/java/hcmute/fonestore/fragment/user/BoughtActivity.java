package hcmute.fonestore.fragment.user;

import static android.view.View.GONE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import hcmute.fonestore.Animation.SwipeToDeleteCallback;
import hcmute.fonestore.MainActivity;
import hcmute.fonestore.R;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCart;

public class BoughtActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference delete;
    Query reference, delete1;

    ArrayList<Product> lstBought = new ArrayList<>();
    ImageView back;

    ConstraintLayout constraintLayout;
    RecyclerView recyclerViewCart;

    ProgressBar loadingView;
    LinearLayout linearLayout;
    Button next;
    boolean j;

    FirebaseStorage storage = FirebaseStorage.getInstance();

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
                Intent intent = new Intent(BoughtActivity.this, MainActivity.class);
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
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(layoutManager2);

        reference = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("seller").equalTo(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstBought.clear();
                loadingView.setVisibility(GONE);
                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product p = ds.getValue(Product.class);
                    p.setId(ds.getKey());
                    lstBought.add(p);
                }
                final RecyclerViewAdapterCart myAdapter = new RecyclerViewAdapterCart(BoughtActivity.this, lstBought);
                recyclerViewCart.setAdapter(myAdapter);

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(BoughtActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Product item = myAdapter.getData().get(position);
                        j = true;

                        myAdapter.removeItem(position);
                        Snackbar snackbar = Snackbar
                                .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                j = false;
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
                                if (j) {
                                    StorageReference photoRef = storage.getReferenceFromUrl(item.getImage());

                                    FirebaseDatabase.getInstance().getReference().child("product").child(item.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(BoughtActivity.this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(BoughtActivity.this, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    photoRef.delete();
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
                Toast.makeText(BoughtActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
