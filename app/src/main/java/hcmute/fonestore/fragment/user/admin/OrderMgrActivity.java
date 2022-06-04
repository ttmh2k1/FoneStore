package hcmute.fonestore.fragment.user.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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

import hcmute.fonestore.Object.Bill;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterOrder;
import hcmute.fonestore.fragment.user.OrderActivity;

public class OrderMgrActivity extends AppCompatActivity {
    ArrayList<Bill> lstBill;
    RecyclerView recyclerView_order_mgr;
    LinearLayout layout_no_order;
    ImageView btn_back;
    ProgressBar loading_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_mgr);

        recyclerView_order_mgr = findViewById(R.id.recyclerView_order_mgr);
        layout_no_order = findViewById(R.id.layout_no_order);
        btn_back = findViewById(R.id.btn_back);
        loading_view = findViewById(R.id.loading_view);
        lstBill = new ArrayList<>();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadData();
    }

    private void loadData() {
        loading_view.setVisibility(View.GONE);
        LinearLayoutManager layoutManagerRecent = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_order_mgr.setLayoutManager(layoutManagerRecent);
        lstBill.clear();

        RecyclerViewAdapterOrder recyclerViewAdapterOrder = new RecyclerViewAdapterOrder(OrderMgrActivity.this, lstBill, true);
        recyclerView_order_mgr.setAdapter(recyclerViewAdapterOrder);
        recyclerView_order_mgr.setNestedScrollingEnabled(false);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("bills")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) layout_no_order.setVisibility(View.GONE);
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String uid = ds.getKey();

                            FirebaseDatabase.getInstance().getReference().child("bills").child(uid)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                Bill b = ds.getValue(Bill.class);
                                                b.setId(ds.getKey());
                                                b.setUid(uid);
                                                lstBill.add(b);
                                                recyclerViewAdapterOrder.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(OrderMgrActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}