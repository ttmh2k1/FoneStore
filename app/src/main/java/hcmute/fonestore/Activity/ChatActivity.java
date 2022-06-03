package hcmute.fonestore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcmute.fonestore.Object.User;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.ChatListAdapter;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapter;

public class ChatActivity extends AppCompatActivity {

    List<User> userList;
    RecyclerView recyclerView;
    EditText searchView;

    ChatListAdapter chatAdapter;

    DatabaseReference ref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userList = new ArrayList<User>();
        recyclerView = findViewById(R.id.listUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.search);
        getAllUser();

        searchView.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (searchView != null) {
            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchUser(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void searchUser(String s) {
        String currentUser =  mAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("user");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(ChatActivity.this,currentUser , Toast.LENGTH_SHORT).show();

                userList.clear();
                User user = new User();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
//                    Toast.makeText(ChatActivity.this,dataSnapshot1.getKey() +"\n"+currentUser , Toast.LENGTH_SHORT).show();
                    user = dataSnapshot1.getValue(User.class);
                    if (!user.getUid().equals(currentUser))
                        if(user.getName().toLowerCase().contains(s.toLowerCase()) ||
                        user.getEmail().toLowerCase().contains(s.toLowerCase())) {
                            userList.add(user);
                        }
//                    }
                }
                chatAdapter = new ChatListAdapter(ChatActivity.this, userList);
                chatAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Error Loading User from Firebase..." , Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAllUser() {

        String currentUser =  mAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("user");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(ChatActivity.this,currentUser , Toast.LENGTH_SHORT).show();

                userList.clear();
                User user = new User();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
//                    Toast.makeText(ChatActivity.this,dataSnapshot1.getKey() +"\n"+currentUser , Toast.LENGTH_SHORT).show();
                    user = dataSnapshot1.getValue(User.class);
                    if (!user.getUid().equals(currentUser))
                        userList.add(user);
                }
                chatAdapter = new ChatListAdapter(ChatActivity.this, userList);
                recyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Error Loading User from Firebase..." , Toast.LENGTH_SHORT).show();
            }
        });
    }
}