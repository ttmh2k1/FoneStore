package hcmute.fonestore.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.categoryFragment.listCategoryFragment;
import hcmute.fonestore.categoryFragment.listFragment;
import hcmute.fonestore.categoryFragment.listIphoneFragment;
import hcmute.fonestore.categoryFragment.listPhukienFragment;
import hcmute.fonestore.categoryFragment.listSamsungFragment;
import hcmute.fonestore.categoryFragment.listVivoFragment;
import hcmute.fonestore.categoryFragment.listXiaomiFragment;
import hcmute.fonestore.object.category2;

public class RecyclerViewAdapterList extends RecyclerView.Adapter<RecyclerViewAdapterList.MyViewHolder> {

    private listFragment mContext ;
    private List<category2> mData ;
    List<LinearLayout> cardViewList = new ArrayList<>();

    public RecyclerViewAdapterList(listFragment mContext, List<category2> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(mContext.getActivity()).inflate(R.layout.button_list,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(view);
        return vHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.list_title.setText(mData.get(position).getTitle());
        holder.img_list.setImageResource(mData.get(position).getThumbnail());
        cardViewList.add(holder.cardView);
        cardViewList.get(0).setBackgroundResource(R.color.white);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                for(LinearLayout cardView : cardViewList){
                    cardView.setBackgroundResource(R.color.background);
                }
                holder.cardView.setBackgroundResource(R.color.white);
                if (position == 0) {
                    Fragment newFragment = new listCategoryFragment();
                    FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                    transaction.replace(R.id.list_host_fragment, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if (position == 1) {
                        Fragment newFragment = new listIphoneFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                }
                else if (position == 2){
                        Fragment newFragment = new listSamsungFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                else if (position == 3){
                        Fragment newFragment = new listVivoFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                else if (position == 4){
                        Fragment newFragment = new listXiaomiFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                else if (position == 5){
                        Fragment newFragment = new listPhukienFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView list_title;
        ImageView img_list;
        LinearLayout cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            list_title = (TextView) itemView.findViewById(R.id.list_title_id) ;
            img_list = (ImageView) itemView.findViewById(R.id.list_img_id);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardview_list);
        }
    }


}
