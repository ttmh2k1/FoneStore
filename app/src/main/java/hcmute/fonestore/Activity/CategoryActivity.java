package hcmute.fonestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.MainActivity;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapter;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back, giohang, home, background;
    Button search;
    TextView category, danhmuc;
    String danhMuc;
    RecyclerView recyclerView;
    Intent intent;
    ArrayList<Product> lstCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        back = findViewById(R.id.category_back);
        search = findViewById(R.id.category_search);
        giohang = findViewById(R.id.btn_category_giohang);
        home = findViewById(R.id.btn_category_home);
        category = findViewById(R.id.category_text);
        danhmuc = findViewById(R.id.category_danhmuc);
        background = findViewById(R.id.img_category);

        // Recieve data
        Intent intent = getIntent();
        danhMuc = intent.getExtras().getString("Category");

        search.setText(danhMuc);
        danhmuc.setText(danhMuc);

        back.setOnClickListener(this);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        category.setOnClickListener(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recyclerView_category);
        recyclerView.setLayoutManager(layoutManager);

        switch (danhMuc) {
            case "Điện thoại Iphone":
                background.setBackgroundResource(R.drawable.img_iphone);
                queryProduct();
                break;
            case "Điện thoại Samsung":
                background.setBackgroundResource(R.drawable.img_samsung);
                queryProduct();
                break;
            case "Điện thoại Oppo":
                background.setBackgroundResource(R.drawable.img_oppo);
                queryProduct();
                break;
            case "Điện thoại Vivo":
                background.setBackgroundResource(R.drawable.img_vivo);
                queryProduct();
                break;
            case "Điện thoại Xiaomi":
                background.setBackgroundResource(R.drawable.img_xiaomi);
                queryProduct();
                break;
        }
    }

    private void queryProduct() {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("product")
                .orderByChild("category")
                .equalTo(danhMuc)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstCategory = new ArrayList<Product>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product p = ds.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(ds.getKey());
                    lstCategory.add(p);
                }
                final RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(CategoryActivity.this, lstCategory);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CategoryActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category_back:
                finish();
                break;
            case R.id.btn_category_giohang:
                intent = new Intent(CategoryActivity.this, CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.category_text:
                intent = new Intent(CategoryActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Selection", "List");
                startActivity(intent);
        }
    }
}
