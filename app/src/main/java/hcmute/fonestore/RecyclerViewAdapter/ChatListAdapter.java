package hcmute.fonestore.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.fonestore.Activity.DetailChatActivity;
import hcmute.fonestore.Object.User;
import hcmute.fonestore.R;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.Myholder>{

    Context context;
    List<User> userList;

    public ChatListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_user_for_chat, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        String userID= userList.get(position).getUid();
//        String userImage = userList.get(position).getAvatar();
        String usesrname = userList.get(position).getName();
        String email = userList.get(position).getEmail();

        holder.mNameTv.setText(usesrname);
        holder.mEmailTv.setText(email);
        Glide.with(context).load(userList.get(position).getAvatar()).placeholder(R.drawable.img_no_image).into(holder.AvatarIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+usesrname , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailChatActivity.class);
                intent.putExtra("otherID", userID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class Myholder extends RecyclerView.ViewHolder {

        ImageView AvatarIv;
        TextView mNameTv, mEmailTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            AvatarIv = itemView.findViewById(R.id.AvatarIv);
            mEmailTv = itemView.findViewById(R.id.mEmailTv);
            mNameTv = itemView.findViewById(R.id.mNameTv);
        }
    }
}
