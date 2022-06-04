package hcmute.fonestore.fragment.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.Activity.FavoriteActivity;
import hcmute.fonestore.MainActivity;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCart;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCommon;

public class SeenProductActivity extends AppCompatActivity {
    RecyclerView recyclerView_recently_seen;
    LinearLayout layout_no_recent;
    ImageView btn_back;
    ProgressBar loading_view_recent;
    Button btn_continue;
    ArrayList<Product> lstProduct;
    RecyclerViewAdapterCommon myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen_product);

        recyclerView_recently_seen = findViewById(R.id.recyclerView_recently_seen);
        layout_no_recent = findViewById(R.id.layout_no_recent);
        btn_back = findViewById(R.id.btn_back);
        loading_view_recent = findViewById(R.id.loading_view_recent);
        btn_continue = findViewById(R.id.btn_continue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeenProductActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lstProduct = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        LinearLayoutManager layoutManagerRecent = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_recently_seen.setLayoutManager(layoutManagerRecent);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        lstProduct.clear();

        FirebaseDatabase.getInstance().getReference().child("seen").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) layout_no_recent.setVisibility(View.GONE);
                loading_view_recent.setVisibility(View.GONE);

                myAdapter = new RecyclerViewAdapterCommon(SeenProductActivity.this, lstProduct);

                for (DataSnapshot ds : snapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference().child("product").child(ds.getValue().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Product p = snapshot.getValue(Product.class);
                            if (p == null) {
                                snapshot.getRef().removeValue();
                                return;
                            }
                            if (p.getActive().equals("0"))
                                return;
                            p.setId(ds.getValue().toString());
                            lstProduct.add(p);
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(SeenProductActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                recyclerView_recently_seen.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SeenProductActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}