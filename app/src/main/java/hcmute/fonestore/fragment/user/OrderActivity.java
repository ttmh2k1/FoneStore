package hcmute.fonestore.fragment.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.Object.Bill;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterOrder;

public class OrderActivity extends AppCompatActivity {
    ArrayList<Bill> lstBill;
    RecyclerView recyclerView_cart;
    ImageView btn_back;
    ProgressBar loading_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView_cart = findViewById(R.id.recyclerView_cart);
        btn_back = findViewById(R.id.btn_back);
        loading_view = findViewById(R.id.loading_view);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lstBill = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        loading_view.setVisibility(View.GONE);
        LinearLayoutManager layoutManagerRecent = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_cart.setLayoutManager(layoutManagerRecent);
        lstBill.clear();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("bills").child(currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Bill b = ds.getValue(Bill.class);
                            b.setId(ds.getKey());
                            lstBill.add(b);
                        }
                        RecyclerViewAdapterOrder recyclerViewAdapterOrder = new RecyclerViewAdapterOrder(OrderActivity.this, lstBill, false);
                        recyclerView_cart.setAdapter(recyclerViewAdapterOrder);
                        recyclerView_cart.setNestedScrollingEnabled(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(OrderActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}