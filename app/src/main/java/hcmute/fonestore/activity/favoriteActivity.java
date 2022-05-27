package hcmute.fonestore.activity;

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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import hcmute.fonestore.MainActivity;
import hcmute.fonestore.R;
import hcmute.fonestore.object.product;

public class favoriteActivity extends AppCompatActivity {
    ArrayList<product> lstYeuthich;
    ImageView back;
    ConstraintLayout layout;
    ProgressBar loadingView;
    LinearLayout linearLayout;
    Button btnContinue;
    RecyclerView recyclerView;
    boolean j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        layout = findViewById(R.id.layout_favorite);
        back = findViewById(R.id.favorite_back);
        loadingView = findViewById(R.id.loading_view_favorite);
        linearLayout = findViewById(R.id.layout_favorite_noProduct);
        btnContinue = findViewById(R.id.favorite_continue);
        recyclerView = findViewById(R.id.recyclerView_favorite);

        loadData();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(favoriteActivity.this, MainActivity.class);
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
        LinearLayoutManager layoutManagerGioHang = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerGioHang);

//        mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        reference = FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstYeuthich = new ArrayList<Product>();
//                loadingView.setVisibility(GONE);
//                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    lstYeuthich.add(p);
//                }
//                final RecyclerViewAdapterGioHang myAdapter = new RecyclerViewAdapterGioHang(YeuThichActivity.this, lstYeuthich);
//                recyclerView.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
//
//                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(YeuThichActivity.this) {
//                    @Override
//                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                        final int position = viewHolder.getAdapterPosition();
//                        final Product item = myAdapter.getData().get(position);
//                        j = true;
//
//                        myAdapter.removeItem(position);
//                        Snackbar snackbar = Snackbar
//                                .make(layout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                        snackbar.setAction("UNDO", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                j = false;
//                                myAdapter.restoreItem(item, position);
//                                recyclerView.scrollToPosition(position);
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
//                                    StorageReference photoRef = storage.getReferenceFromUrl(item.getHinhAnh());
//                                    delete = FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid()).child(item.getTen());
//                                    delete.removeValue();
//
//                                    photoRef.delete();
//                                }
//                            }
//                        }, 2900);
//                    }
//                };
//                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
//                itemTouchhelper.attachToRecyclerView(recyclerView);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(YeuThichActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
