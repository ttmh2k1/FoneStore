package hcmute.fonestore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hcmute.fonestore.Object.Chats;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.ChatMessageAdapter;
import hcmute.fonestore.RecyclerViewAdapter.ChatMessageOfUserAdapter;

public class ChatForUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewChat;
    ImageView user_img1;
    TextView tvUsername;
    TextView tvStatus;
    EditText etMessage;
    ImageButton btnSend;

    DatabaseReference ref;
    FirebaseAuth mAuth;
    String UserID, otherID, otherImage;
    //
    //checking if seen or not yet
    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;

    List<Chats> chatsList;
    ChatMessageOfUserAdapter chatMessageOfUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_for_user);

        toolbar = findViewById(R.id.toolbar);
//        setSupportActionToolbar(toolbar);
        recyclerViewChat = findViewById(R.id.listChat);
        user_img1 = findViewById(R.id.user_img1);
        tvUsername = findViewById(R.id.tvUsername);
        tvStatus = findViewById(R.id.tvStatus);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        //linear layout for recycleview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);

        //recyclerview properties
        recyclerViewChat.setHasFixedSize(true);
        recyclerViewChat.setLayoutManager(layoutManager);

        UserID =  mAuth.getInstance().getCurrentUser().getUid();
        otherID = "admin" ;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString().trim();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(ChatForUserActivity.this, "Cannot send with null message" , Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(message);
                }
            }
        });


        ReadMessage();
        seenMessage();
    }

    private void seenMessage() {
        userRefForSeen = FirebaseDatabase.getInstance().getReference("chats");
        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()){
                    Chats chat = i.getValue(Chats.class);
                    if(chat.getReceiver().equals(UserID) && chat.getSender().equals(otherID)){
                        HashMap<String , Object> hashMap = new HashMap<>();
                        hashMap.put("isSeen", true);
                        i.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ReadMessage() {
        chatsList = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    Chats chat = item.getValue(Chats.class);
                    if(chat.getSender().equals(UserID) && chat.getReceiver().equals(otherID) ||
                            chat.getReceiver().equals(UserID) && chat.getSender().equals(otherID)){
                        chatsList.add(chat);
                    }
                }
                chatMessageOfUserAdapter = new ChatMessageOfUserAdapter(ChatForUserActivity.this, chatsList);
                chatMessageOfUserAdapter.notifyDataSetChanged();
                recyclerViewChat.setAdapter(chatMessageOfUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message) {
        ref = FirebaseDatabase.getInstance().getReference();
        String Time = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("sender", UserID);
        hashmap.put("receiver", "admin");
        hashmap.put("message", message);
        hashmap.put("time", Time );
        hashmap.put("isSeen", false);

        ref.child("chats").push().setValue(hashmap);

        etMessage.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        userRefForSeen.removeEventListener(seenListener);
    }
}