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
import hcmute.fonestore.fragment.user.admin.AddProductActivity;
import hcmute.fonestore.fragment.user.admin.OrderMgrActivity;
import hcmute.fonestore.fragment.user.admin.ProductMgrActivity;
import hcmute.fonestore.fragment.user.admin.UserMgrActivity;

public class UserFragment extends Fragment implements View.OnClickListener {
    ImageView cart;
    Button btnFavorite, btnLogout, btnAccount, btnOrder, aboutus, setting, btnSeenProduct;
    Button btnAdminAccountMgr, btnAdminProductMgr, btnAdminCreateProduct, btnAdminOrderMgr;
    TextView email, name, address;
    ImageView img;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    Intent intent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        btnSeenProduct = root.findViewById(R.id.btn_seen_product);
        btnFavorite = root.findViewById(R.id.user_favorite);
        btnAccount = root.findViewById(R.id.user_infoAccount);
        btnLogout = root.findViewById(R.id.user_logout);
        btnOrder = root.findViewById(R.id.btn_user_order);

        btnAdminAccountMgr = root.findViewById(R.id.btn_admin_account_mgr);
        btnAdminProductMgr = root.findViewById(R.id.btn_admin_product_mgr);
        btnAdminCreateProduct = root.findViewById(R.id.btn_admin_create_product);
        btnAdminOrderMgr = root.findViewById(R.id.btn_admin_order_mgr);

        cart = root.findViewById(R.id.btn_user_cart);
        email = root.findViewById(R.id.user_email);
        name = root.findViewById(R.id.user_name);
        address = root.findViewById(R.id.user_address);
        img = root.findViewById(R.id.user_img1);
        aboutus = root.findViewById(R.id.user_support);
        setting = root.findViewById(R.id.user_setting);

        loadData();

        cart.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnAccount.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        setting.setOnClickListener(this);
        btnAdminAccountMgr.setOnClickListener(this);
        btnAdminProductMgr.setOnClickListener(this);
        btnAdminCreateProduct.setOnClickListener(this);
        btnSeenProduct.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        btnAdminOrderMgr.setOnClickListener(this);

        return root;
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid()).child("role").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(String.class).equals("kh")) {
                    btnAdminAccountMgr.setVisibility(View.GONE);
                    btnAdminProductMgr.setVisibility(View.GONE);
                    btnAdminCreateProduct.setVisibility(View.GONE);
                    btnAdminOrderMgr.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                address.setText(dataSnapshot.child("address").getValue().toString());

                Glide.with(getActivity()).load(dataSnapshot.child("avatar").getValue().toString())
                        .placeholder(R.drawable.img_no_image).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
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
            case R.id.user_favorite:
                intent = new Intent(getActivity(), FavoriteActivity.class);
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
            case R.id.btn_seen_product:
                intent = new Intent(getActivity(), SeenProductActivity.class);
                startActivity(intent);
                break;
            case R.id.user_setting:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_admin_account_mgr:
                intent = new Intent(getActivity(), UserMgrActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_admin_product_mgr:
                intent = new Intent(getActivity(), ProductMgrActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_admin_create_product:
                intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_user_order:
                intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_admin_order_mgr:
                intent = new Intent(getActivity(), OrderMgrActivity.class);
                startActivity(intent);
                break;
        }
    }
}