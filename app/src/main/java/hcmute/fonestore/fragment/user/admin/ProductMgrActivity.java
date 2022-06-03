package hcmute.fonestore.fragment.user.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import hcmute.fonestore.Animation.SwipeToDeleteCallback;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterProductMgr;

public class ProductMgrActivity extends AppCompatActivity {
    RecyclerViewAdapterProductMgr adapterProductMgr;
    RecyclerView recyclerView_product_mgr;
    LinearLayout layout_no_product;
    ProgressBar loading_view_product;
    ImageView btn_back;
    ArrayList<Product> lstProduct;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_mgr);

        btn_back = findViewById(R.id.btn_back);
        recyclerView_product_mgr = findViewById(R.id.recyclerView_product_mgr);
        layout_no_product = findViewById(R.id.layout_no_product);
        loading_view_product = findViewById(R.id.loading_view_product);
        lstProduct = new ArrayList<>();

        loadData();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_product_mgr.setLayoutManager(layoutManager);

        FirebaseDatabase.getInstance().getReference().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) layout_no_product.setVisibility(View.GONE);
                loading_view_product.setVisibility(View.GONE);

                for (DataSnapshot ds :
                        snapshot.getChildren()) {
                    Product p = ds.getValue(Product.class);
                    p.setId(ds.getKey());
                    lstProduct.add(p);
                }

                adapterProductMgr = new RecyclerViewAdapterProductMgr(ProductMgrActivity.this, lstProduct);
                recyclerView_product_mgr.setAdapter(adapterProductMgr);

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(ProductMgrActivity.this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        int position = viewHolder.getAdapterPosition();
                        Product p = adapterProductMgr.getData().get(position);

                        new AlertDialog.Builder(ProductMgrActivity.this)
                                .setTitle("Xóa sản phẩm")
                                .setMessage("Bạn có chắc chắn xóa sản phẩm " + p.getName() + " không?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        FirebaseDatabase.getInstance().getReference().child("product").child(p.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    StorageReference photoRef = storage.getReferenceFromUrl(p.getImage());
                                                    photoRef.delete();
                                                    adapterProductMgr.removeItem(position);
                                                    adapterProductMgr.notifyItemChanged(position);
                                                    Toast.makeText(ProductMgrActivity.this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    adapterProductMgr.notifyItemChanged(position);
                                                    Toast.makeText(ProductMgrActivity.this, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }})
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        adapterProductMgr.notifyItemChanged(position);
                                    }
                                }).show();
                    }
                };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
                itemTouchhelper.attachToRecyclerView(recyclerView_product_mgr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductMgrActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}