package hcmute.fonestore.RecyclerViewAdapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hcmute.fonestore.Object.Chats;
import hcmute.fonestore.R;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MyHolder>{

    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_Right=1;

    Context context;
    List<Chats> chatsList;
    String imageUrl;

    FirebaseUser fUSer;

    public ChatMessageAdapter(Context context, List<Chats> chatsList, String imageUrl) {
        this.context = context;
        this.chatsList = chatsList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == MSG_TYPE_LEFT){
            view = LayoutInflater.from(context).inflate(R.layout.row_message_for_detail_chat, parent, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.row_send_message_for_detail_chat, parent, false);
        }
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String message = chatsList.get(position).getMessage();
        String Date = chatsList.get(position).getTime();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(Date));

        String time = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();

        holder.tvmessage.setText(message);
        holder.tvDate.setText(time);
        Glide.with(context).load(imageUrl).placeholder(R.drawable.img_no_image).into(holder.avatar);

        if(position == chatsList.size() - 1){
//            Toast.makeText(context, String.valueOf( chatsList.get(position).isSeen()), Toast.LENGTH_SHORT).show();
//            chatsList.get(position).setSeen(true);
            if(chatsList.get(position).isSeen() == true){
                holder.isSeen.setText("Seen");
//                Toast.makeText(context, "seen", Toast.LENGTH_SHORT).show();
            }else {
//                Toast.makeText(context, "Delivered", Toast.LENGTH_SHORT).show();
                holder.isSeen.setText("Delivered");
            }
        }
        else {
            holder.isSeen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fUSer = FirebaseAuth.getInstance().getCurrentUser();
        if(chatsList.get(position).getSender().equals(fUSer.getUid())){
            return MSG_TYPE_Right;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView tvmessage, tvDate, isSeen;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            tvmessage = itemView.findViewById(R.id.tvmessage);
            tvDate = itemView.findViewById(R.id.tvDate);
            isSeen = itemView.findViewById(R.id.isSeen) ;
        }
    }
}
