package hcmute.fonestore.fragment.user.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.Object.User;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterUserMgr;

public class UserMgrActivity extends AppCompatActivity {
    RecyclerView recyclerView_user_mgr;
    LinearLayout layout_no_account;
    ProgressBar loading_view_account;
    ArrayList<User> lstUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_mgr);

        recyclerView_user_mgr = findViewById(R.id.recyclerView_user_mgr);
        layout_no_account = findViewById(R.id.layout_no_account);
        loading_view_account = findViewById(R.id.loading_view_account);
        lstUser = new ArrayList<>();

        loadData();
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_user_mgr.setLayoutManager(layoutManager);

        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) layout_no_account.setVisibility(View.GONE);
                loading_view_account.setVisibility(View.GONE);
                lstUser.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user.getRole().equals("kh")) {
                        lstUser.add(user);
                    }
                }

                RecyclerViewAdapterUserMgr adapterUserMgr = new RecyclerViewAdapterUserMgr(UserMgrActivity.this, lstUser);
                recyclerView_user_mgr.setAdapter(adapterUserMgr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserMgrActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}