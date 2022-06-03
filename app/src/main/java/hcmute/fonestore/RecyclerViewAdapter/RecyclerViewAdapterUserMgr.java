package hcmute.fonestore.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Notification;
import hcmute.fonestore.Object.User;
import hcmute.fonestore.R;

public class RecyclerViewAdapterUserMgr extends RecyclerView.Adapter<RecyclerViewAdapterUserMgr.MyViewHolder> {
    private Context context;
    private ArrayList<User> data;

    public RecyclerViewAdapterUserMgr(Context context, ArrayList<User> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.cardview_user_mgr, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        User user = data.get(position);
        Glide.with(context).load(user.getAvatar()).placeholder(R.drawable.img_no_image).into(holder.userAvt);
        holder.txtEmail.setText("Email: " + user.getEmail());
        holder.txtUsername.setText("Tên người dùng: " + user.getName());
        holder.txtAddress.setText("Địa chỉ: " + user.getAddress());
        holder.txtDateJoined.setText("Thời gian tham gia: " + user.getJoinTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /*public ArrayList<User> getData() {
        return data;
    }*/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userAvt;
        TextView txtEmail, txtUsername, txtAddress, txtDateJoined;
        Button btnActivate, btnDel;

        public MyViewHolder(View itemView) {
            super(itemView);

            userAvt = itemView.findViewById(R.id.user_avt);
            txtEmail = itemView.findViewById(R.id.user_email);
            txtUsername = itemView.findViewById(R.id.user_name);
            txtAddress = itemView.findViewById(R.id.user_address);
            txtDateJoined = itemView.findViewById(R.id.user_join_date);

            btnActivate = itemView.findViewById(R.id.btn_activate);
            btnDel = itemView.findViewById(R.id.btn_del);
        }
    }
}
