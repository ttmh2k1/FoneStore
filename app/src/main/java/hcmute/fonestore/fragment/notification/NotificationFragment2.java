package hcmute.fonestore.fragment.notification;

import static android.view.View.GONE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.Animation.SwipeToDeleteCallback;
import hcmute.fonestore.MainActivity;
import hcmute.fonestore.Object.Notification;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterNotification;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterNotify;

public class NotificationFragment2 extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference ref, delete;
    FirebaseAuth mAuth;
    ArrayList<Notification> lstNotifications;

    LinearLayout linearLayout, notification_layout;
    Button tieptuc;

    boolean check;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = root.findViewById(R.id.recyclerView_notification);
        linearLayout = root.findViewById(R.id.background_notification);
        notification_layout = root.findViewById(R.id.recyclerView_layout);
        tieptuc = root.findViewById(R.id.notification_continue);

        loadData();

        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return root;
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ref = FirebaseDatabase.getInstance().getReference().child("notifications").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);

                lstNotifications = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Notification p = ds.getValue(Notification.class);
                    p.setId(ds.getKey());
                    lstNotifications.add(p);
                }

                final RecyclerViewAdapterNotify myAdapter = new RecyclerViewAdapterNotify(getContext(), lstNotifications);
                recyclerView.setAdapter(myAdapter);

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Notification item = myAdapter.getData().get(position);

                        myAdapter.removeItem(position);
                        check = true;
                        Snackbar snackbar = Snackbar
                                .make(notification_layout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                check = false;
                                myAdapter.restoreItem(item, position);
                                recyclerView.scrollToPosition(position);
                            }
                        });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (check) {
                                    FirebaseDatabase
                                            .getInstance()
                                            .getReference()
                                            .child("notifications")
                                            .child(currentUser.getUid())
                                            .child(item.getId())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(getActivity(), "Xóa thông báo thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        }, 2000);
                    }
                };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
                itemTouchhelper.attachToRecyclerView(recyclerView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
