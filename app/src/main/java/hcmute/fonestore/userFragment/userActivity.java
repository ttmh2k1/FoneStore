package hcmute.fonestore.userFragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.fonestore.R;

public class userActivity extends AppCompatActivity {

    TextView email, address, date, id, cart, favorite, bought, name;
    ImageView avatar;
//    FirebaseAuth mAuth;
//    DatabaseReference ref;
//    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = findViewById(R.id.text_name);
        cart = findViewById(R.id.text_cart);
        favorite = findViewById(R.id.text_favorite);
        bought = findViewById(R.id.text_warehouse);
        email = findViewById(R.id.text_email);
        address = findViewById(R.id.text_address);
        date = findViewById(R.id.text_date);
        id = findViewById(R.id.text_id);
        avatar = findViewById(R.id.avatar);

        loadData();
    }

    private void loadData() {
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                name.setText(dataSnapshot.child("name").getValue().toString());
//                email.setText(dataSnapshot.child("email").getValue().toString());
//                date.setText(dataSnapshot.child("date").getValue().toString());
//                id.setText(dataSnapshot.child("uid").getValue().toString());
//
//                Glide.with(userActivity.this).load(dataSnapshot.child("avatar").getValue().toString())
//                        .placeholder(R.drawable.img_no_image).into(avatar);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(userActivity.this, "Tải lên thất bại!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                name.setText(dataSnapshot.child("name").getValue().toString());
//                email.setText(dataSnapshot.child("email").getValue().toString());
//                date.setText(dataSnapshot.child("date").getValue().toString());
//                id.setText(dataSnapshot.child("uid").getValue().toString());
//                address.setText(dataSnapshot.child("address").getValue().toString());
//
//                Glide.with(userActivity.this).load(dataSnapshot.child("avatar").getValue().toString())
//                        .placeholder(R.drawable.img_no_image).into(avatar);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(userActivity.this, "Tải lên thất bại!", Toast.LENGTH_SHORT).show();
//            }
//        });

//        ref = FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<product> full = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    full.add(p);
//                }
//                cart.setText(Integer.toString(full.size()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//        ref = FirebaseDatabase.getInstance().getReference().child("favorite").child(currentUser.getUid());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<product> full = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    full.add(p);
//                }
//                favorite.setText(Integer.toString(full.size()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//        query = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("seller").equalTo(currentUser.getUid());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<product> full = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    full.add(p);
//                }
//                bought.setText(Integer.toString(full.size()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
    }
}
