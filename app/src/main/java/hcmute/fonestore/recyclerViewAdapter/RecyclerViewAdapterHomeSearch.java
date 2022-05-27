package hcmute.fonestore.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.activity.searchActivity;
import hcmute.fonestore.homeFragment;
import hcmute.fonestore.object.category;

public class RecyclerViewAdapterHomeSearch extends RecyclerView.Adapter<RecyclerViewAdapterHomeSearch.MyViewHolder> {

    private homeFragment mContext;
    private List<category> mData;
    List<LinearLayout> cardViewList = new ArrayList<>();


    public RecyclerViewAdapterHomeSearch(homeFragment mContext, List<category> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getActivity());
        view = mInflater.inflate(R.layout.button_home_hot, parent, false);
        return new MyViewHolder(view);

    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_category_title.setText(mData.get(position).getTitle());
        cardViewList.add(holder.cardView);
        cardViewList.get(0).setBackgroundResource(R.drawable.border2);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (LinearLayout cardView : cardViewList) {
                    cardView.setBackgroundResource(R.drawable.border1);
                }
                holder.cardView.setBackgroundResource(R.drawable.border2);

                Intent intent = new Intent(mContext.getContext(), searchActivity.class);

                // passing data to the book activity
                intent.putExtra("Search", mData.get(position).getTitle());
                // start the activity
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category_title;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_title = (TextView) itemView.findViewById(R.id.home_btn_title);
            cardView = (LinearLayout) itemView.findViewById(R.id.home_btn_hot);
        }
    }
}