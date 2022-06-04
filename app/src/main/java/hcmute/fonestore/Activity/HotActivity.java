package hcmute.fonestore.Activity;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapter;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCart;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCommon;

public class HotActivity extends AppCompatActivity {
    ArrayList<Product> lstHot;
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

        FirebaseDatabase.getInstance().getReference("product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstHot = new ArrayList<>();
                loadingView.setVisibility(GONE);
                List<Product> full = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(dataSnapshot1.getKey());
                    full.add(p);
                }
                Collections.shuffle(full);

                for (int i = 0; i < 5 && i < full.size(); i++)
                    lstHot.add(full.get(i));

                recyclerView.setAdapter(new RecyclerViewAdapterCommon(HotActivity.this, lstHot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HotActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}