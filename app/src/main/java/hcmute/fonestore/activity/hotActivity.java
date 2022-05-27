package hcmute.fonestore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;

import hcmute.fonestore.R;
import hcmute.fonestore.object.product;

public class hotActivity extends AppCompatActivity {
    ArrayList<product> lstHot;
    ImageView back;
    ProgressBar loadingView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);

        back = findViewById(R.id.hot_back);
        loadingView = findViewById(R.id.hot_loading);
        recyclerView = findViewById(R.id.recyclerView_hot);

        loadData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

//        reference = FirebaseDatabase.getInstance().getReference().child("Product");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstNoibat = new ArrayList<>();
//                loadingView.setVisibility(GONE);
//                List<Product> full = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    full.add(p);
//                }
//                Collections.shuffle(full);
//
//                for (int i = 0; i < 5; ++i)
//                    lstNoibat.add(full.get(i));
//
//                RecyclerViewAdapterGioHang myAdapter = new RecyclerViewAdapterGioHang(NoiBatActivity.this, lstNoibat);
//                recyclerView.setAdapter(myAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(NoiBatActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}