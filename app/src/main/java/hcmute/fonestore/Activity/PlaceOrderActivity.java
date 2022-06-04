package hcmute.fonestore.Activity;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hcmute.fonestore.Object.Bill;
import hcmute.fonestore.Object.CartItem;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.RandomString;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCart;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterPlaceOrder;
import hcmute.fonestore.fragment.user.OrderActivity;

public class PlaceOrderActivity extends AppCompatActivity {
    ArrayList<CartItem> lstCart = new ArrayList<>();
    RecyclerView recyclerView_cart;
    TextView txt_total;
    Button btn_place_order;
    ImageView btn_back;
    ProgressBar loading_view;
    long totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        recyclerView_cart = findViewById(R.id.recyclerView_cart);
        txt_total = findViewById(R.id.txt_total);
        btn_place_order = findViewById(R.id.btn_place_order);
        btn_back = findViewById(R.id.btn_back);
        loading_view = findViewById(R.id.loading_view);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                Bill bill = new Bill(lstCart, 0, dateFormat.format(date));
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("bills")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(new RandomString().nextString())
                        .setValue(bill)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                for (CartItem c : lstCart) {
                                    FirebaseDatabase
                                            .getInstance()
                                            .getReference()
                                            .child("cart")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child(c.getKey())
                                            .removeValue();
                                }

                                Intent intent = new Intent(PlaceOrderActivity.this, OrderActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
            }
        });
        totalPrice = 0;
        loadData();
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_cart.setLayoutManager(layoutManager);

        final RecyclerViewAdapterPlaceOrder myAdapter = new RecyclerViewAdapterPlaceOrder(PlaceOrderActivity.this, lstCart);
        recyclerView_cart.setAdapter(myAdapter);

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("cart")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        lstCart.clear();
                        loading_view.setVisibility(GONE);

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            CartItem c = ds.getValue(CartItem.class);
                            c.setKey(ds.getKey());
                            if (c.getProduct().getActive().equals("0"))
                                return;

                            totalPrice += c.getProduct().getPrice() * c.getQty();
                            lstCart.add(c);
                            myAdapter.notifyDataSetChanged();
                            txt_total.setText(String.format("Tổng tiền: %,3d VNĐ", totalPrice));
                        }
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PlaceOrderActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}