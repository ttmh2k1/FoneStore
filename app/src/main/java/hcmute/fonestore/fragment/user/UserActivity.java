package hcmute.fonestore.fragment.user;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import hcmute.fonestore.R;

public class UserActivity extends AppCompatActivity {

    TextView email, address, date, id, cart, favourite, totalProduct, name;
    ImageView avatar;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = findViewById(R.id.text_name);
        cart = findViewById(R.id.text_cart);
        favourite = findViewById(R.id.text_favorite);
        totalProduct = findViewById(R.id.text_warehouse);
        email = findViewById(R.id.text_email);
        address = findViewById(R.id.text_address);
        date = findViewById(R.id.text_date);
        id = findViewById(R.id.text_id);
        avatar = findViewById(R.id.avatar);

        loadData();
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        /*ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                date.setText(dataSnapshot.child("date").getValue().toString());
                id.setText(dataSnapshot.child("uid").getValue().toString());

                Glide.with(UserActivity.this).load(dataSnapshot.child("avatar").getValue().toString())
                        .placeholder(R.drawable.img_no_image).into(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Tải lên thất bại!", Toast.LENGTH_SHORT).show();
            }
        });*/

        ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                date.setText(dataSnapshot.child("joinTime").getValue().toString());
                id.setText(dataSnapshot.child("uid").getValue().toString());
                address.setText(dataSnapshot.child("address").getValue().toString());

                Glide.with(UserActivity.this).load(dataSnapshot.child("avatar").getValue().toString())
                        .placeholder(R.drawable.img_no_image).into(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Tải lên thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    i++;
                }
                cart.setText(Integer.toString(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("favourite").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    i++;
                }
                favourite.setText(Integer.toString(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        query = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("seller").equalTo(currentUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    i++;
                }
                totalProduct.setText(Integer.toString(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
