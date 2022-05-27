package hcmute.fonestore.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.activity.categoryActivity;
import hcmute.fonestore.categoryFragment.listCategoryFragment;
import hcmute.fonestore.object.category2;

public class RecyclerViewAdapterList2 extends RecyclerView.Adapter<RecyclerViewAdapterList2.MyViewHolder> {

    private listCategoryFragment mContext;
    private List<category2> mData;
    Intent intent;

    public RecyclerViewAdapterList2(listCategoryFragment mContext, List<category2> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getActivity());
        view = mInflater.inflate(R.layout.cardview_category,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_category_title.setText(mData.get(position).getTitle());
        holder.img_category_thumbnail.setImageResource(mData.get(position).getThumbnail());
        intent = new Intent(mContext.getActivity(), categoryActivity.class);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mData.get(position).getTitle()) {
                    case "Điện thoại Iphone":
                        intent.putExtra("Category", "Điện thoại Iphone");
                        mContext.startActivity(intent);
                        break;
                    case "Điện thoại Samsung":
                        intent.putExtra("Category", "Điện thoại Samsung");
                        mContext.startActivity(intent);
                        break;
                    case "Điện thoại Oppo":
                        intent.putExtra("Category", "Điện thoại Oppo");
                        mContext.startActivity(intent);
                        break;
                    case "Điện thoại Xiaomi":
                        intent.putExtra("Category", "Điện thoại Xiaomi");
                        mContext.startActivity(intent);
                        break;
                    case "Phụ kiện":
                        intent.putExtra("Category", "Phụ kiện");
                        mContext.startActivity(intent);
                        break;
                }
            }
        });
    }

    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_category_title;
        ImageView img_category_thumbnail;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_title = (TextView) itemView.findViewById(R.id.category_title_id);
            img_category_thumbnail = (ImageView) itemView.findViewById(R.id.category_img_id);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardview_category);
        }
    }
}


