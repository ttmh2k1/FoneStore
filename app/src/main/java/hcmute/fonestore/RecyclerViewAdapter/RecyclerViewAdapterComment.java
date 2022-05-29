package hcmute.fonestore.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.Activity.productActivity;
import hcmute.fonestore.Object.Comment;

public class RecyclerViewAdapterComment extends RecyclerView.Adapter<RecyclerViewAdapterComment.MyViewHolder> {
    private productActivity mContext;
    private List<Comment> mData;

    public RecyclerViewAdapterComment(productActivity mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.customerName.setText(mData.get(position).getCustomerName());
        holder.comment.setText(mData.get(position).getComment());
//        Glide.with(mContext).load(mData.get(position).getImage()).placeholder(R.drawable.noimage).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView customerName, comment;
        ImageView avatar;

        public MyViewHolder(View itemView) {
            super(itemView);

            customerName = itemView.findViewById(R.id.customerName);
            comment = itemView.findViewById(R.id.comment);
            avatar = itemView.findViewById(R.id.user_comment);
        }
    }
}
