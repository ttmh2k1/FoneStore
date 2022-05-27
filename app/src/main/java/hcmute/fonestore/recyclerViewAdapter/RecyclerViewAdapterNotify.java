package hcmute.fonestore.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.fonestore.R;
import hcmute.fonestore.activity.productActivity;
import hcmute.fonestore.object.notification;

public class RecyclerViewAdapterNotify extends RecyclerView.Adapter<RecyclerViewAdapterNotify.MyViewHolder> {

    private Context mContext;
    private ArrayList<notification> mData;
    String image;

    public RecyclerViewAdapterNotify(Context mContext, ArrayList<notification> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_notify, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getTitle());
        String sourceString = "<b>" + mData.get(position).getCustomer() + "</b> " + " đã bình luận về sản phẩm của bạn";
        holder.comment.setText(Html.fromHtml(sourceString));
//        Glide.with(mContext).load(mData.get(position).getImage()).placeholder(R.drawable.noimage).into(holder.thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, productActivity.class);

                // passing data to the book activity
                intent.putExtra("Name", mData.get(position).getProduct());

                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<notification> getData() {
        return mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, comment;
        ImageView thumbnail;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.date_notify);
            comment = itemView.findViewById(R.id.comment);
            cardView = (LinearLayout) itemView.findViewById(R.id.card_notification);
            thumbnail = itemView.findViewById(R.id.notification_img);
        }
    }
}
