package hcmute.fonestore.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.fragment.notification.notificationFragment;
import hcmute.fonestore.fragment.notification.notify1Fragment;
import hcmute.fonestore.Object.CategoryWithThumnail;


public class RecyclerViewAdapterNotification extends RecyclerView.Adapter<RecyclerViewAdapterNotification.MyViewHolder> {

    private notificationFragment mContext;
    private List<CategoryWithThumnail> mData;
    List<LinearLayout> cardViewList = new ArrayList<>();
    Fragment newFragment;
    FragmentTransaction transaction;

    public RecyclerViewAdapterNotification(notificationFragment mContext, List<CategoryWithThumnail> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getActivity());
        view = mInflater.inflate(R.layout.button_notify, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        holder.img_category_thumbnail.setImageResource(mData.get(position).getThumbnail());
        cardViewList.add(holder.cardView);
        cardViewList.get(0).setBackgroundResource(R.color.white);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (LinearLayout cardView : cardViewList) {
                    cardView.setBackgroundResource(R.color.gray);
                }
                holder.cardView.setBackgroundResource(R.color.white);
                switch (position) {
                    case 0:
                        newFragment = new notificationFragment();
                        transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.notification_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case 1:
                    case 2:
                    case 3:
                        newFragment = new notify1Fragment();
                        transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.notification_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_category_thumbnail;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_category_thumbnail = (ImageView) itemView.findViewById(R.id.notification_img_id);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardview_notification);
        }
    }
}
