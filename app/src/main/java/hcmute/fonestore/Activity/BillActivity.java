package hcmute.fonestore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.BillAdapter;

public class BillActivity extends AppCompatActivity {

    Button btnChoose;
    ImageView back;
    RecyclerView recyclerView;
    ArrayList<Product> lstPro;

    FirebaseAuth mAuth;

    DatabaseReference ref;
    BillAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        lstPro = new ArrayList<>();

        btnChoose = findViewById(R.id.btnChoose);
        recyclerView = findViewById(R.id.recyclerView_bill);
        back = findViewById(R.id.cart_back);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager2);
        myAdapter = new BillAdapter(this, lstPro, new BillAdapter.IBtnDeleteClick() {
            @Override
            public void deletePro(Product product) {
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser currentUser = mAuth.getCurrentUser();
                FirebaseDatabase.getInstance().getReference("bills").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot i: snapshot.getChildren()){
                            if(i.child("id").getValue().toString().equals(product.getId()) && i.child("status").getValue().toString().equals("wait")!=false)
                                i.getRef().removeValue();
                            else {
                                Toast.makeText(BillActivity.this, "Bạn không được phép hủy đơn hàng nữa rồi!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        recyclerView.setAdapter(myAdapter);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMenu();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void ShowMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnChoose);
        popupMenu.getMenuInflater().inflate(R.menu.category_bill, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_iphone:
                        btnChoose.setText("Chờ Xác Nhận");
                        showView("wait");
                        break;
                    case R.id.menu_samsung:
                        btnChoose.setText("Đang Giao");
                        showView("delivering");
                        break;
                    case R.id.menu_oppo:
                        btnChoose.setText("Đã giao");
                        showView("received");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showView(String choosen) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference("bills").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstPro.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Product tempPro = ds.getValue(Product.class);
                    if (tempPro.getStatus().equals(choosen)) {
                        lstPro.add(tempPro);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
