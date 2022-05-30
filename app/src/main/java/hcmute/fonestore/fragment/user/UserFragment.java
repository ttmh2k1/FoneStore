package hcmute.fonestore.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.fonestore.R;
import hcmute.fonestore.Activity.CartActivity;
import hcmute.fonestore.Activity.FavoriteActivity;
import hcmute.fonestore.Activity.LoginActivity;

public class UserFragment extends Fragment implements View.OnClickListener {
    ImageView cart;
    Button btnManage, btnFavorite, btnLogout, btnBuylater, btnAccount, aboutus, setting;
    TextView email, name, address;
    ImageView img;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    Intent intent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        btnBuylater = root.findViewById(R.id.user_buylater);
        btnFavorite = root.findViewById(R.id.user_favorite);
        btnManage = root.findViewById(R.id.user_manage);
        btnAccount = root.findViewById(R.id.user_infoAccount);
        btnLogout = root.findViewById(R.id.user_logout);
        cart = root.findViewById(R.id.btn_user_cart);
        email = root.findViewById(R.id.user_email);
        name = root.findViewById(R.id.user_name);
        address = root.findViewById(R.id.user_address);
        img = root.findViewById(R.id.user_img1);
        aboutus = root.findViewById(R.id.user_support);
        setting = root.findViewById(R.id.user_setting);

        loadData();

        cart.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
        btnBuylater.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnAccount.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        setting.setOnClickListener(this);

        return root;
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Setting values
                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                address.setText(dataSnapshot.child("address").getValue().toString());

                Glide.with(getActivity()).load(dataSnapshot.child("avatar").getValue().toString())
                        .placeholder(R.drawable.img_no_image).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Tải lên thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_cart:
                intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
                break;
            case R.id.user_manage:
                intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
                break;
            case R.id.user_favorite:
                intent = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.user_buylater:
                intent = new Intent(getActivity(), BoughtActivity.class);
                startActivity(intent);
                break;
            case R.id.user_logout:
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.user_infoAccount:
                intent = new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
                break;
            case R.id.user_support:
                intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.user_setting:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}