package hcmute.fonestore.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.fragment.HomeFragment;
import hcmute.fonestore.Object.Category;


public class RecyclerViewAdapterHomeCategory extends RecyclerView.Adapter<RecyclerViewAdapterHomeCategory.MyViewHolder> {

    private HomeFragment mContext;
    private List<Category> mData;
    List<LinearLayout> cardViewList = new ArrayList<>();


    public RecyclerViewAdapterHomeCategory(HomeFragment mContext, List<Category> mData) {
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

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext.getActivity(), LinearLayoutManager.HORIZONTAL, false);
                final RecyclerView recyclerView = mContext.root.findViewById(R.id.recyclerView_home_hot);
                recyclerView.setLayoutManager(layoutManager);

                switch (mData.get(position).getTitle()) {
                    case "Tất cả":
                        FirebaseDatabase.getInstance().getReference("product").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstHot = new ArrayList<Product>();
                                List<Product> full = new ArrayList<>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);
                                    full.add(p);
                                }
                                Collections.shuffle(full);

                                for (int i = 0; i < 5; ++i)
                                    mContext.lstHot.add(full.get(i));

                                RecyclerViewAdapter myAdapterHot = new RecyclerViewAdapter(mContext.getContext(), mContext.lstHot);
                                recyclerView.setAdapter(myAdapterHot);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Điện thoại Iphone":
                        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Iphone").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstIphone = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);

                                    if (p.getActive().equals("0"))
                                        continue;

                                    p.setId(dataSnapshot1.getKey());
                                    mContext.lstIphone.add(p);
                                }
                                if (mContext.lstIphone.size() < mContext.LIMIT_PRODUCT + 1) {
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstIphone));
                                }
                                else {
                                    Collections.shuffle(mContext.lstIphone);
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstIphone.subList(0, mContext.LIMIT_PRODUCT)));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Điện thoại Samsung":
                        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Samsung").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstSamsung = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);

                                    if (p.getActive().equals("0"))
                                        continue;

                                    p.setId(dataSnapshot1.getKey());
                                    mContext.lstSamsung.add(p);
                                }
                                if (mContext.lstSamsung.size() < mContext.LIMIT_PRODUCT + 1) {
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstSamsung));
                                }
                                else {
                                    Collections.shuffle(mContext.lstSamsung);
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstSamsung.subList(0, mContext.LIMIT_PRODUCT)));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Điện thoại Oppo":
                        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Oppo").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstOppo = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);

                                    if (p.getActive().equals("0"))
                                        continue;

                                    p.setId(dataSnapshot1.getKey());
                                    mContext.lstOppo.add(p);
                                }
                                if (mContext.lstOppo.size() < mContext.LIMIT_PRODUCT + 1) {
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstOppo));
                                }
                                else {
                                    Collections.shuffle(mContext.lstOppo);
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstOppo.subList(0, mContext.LIMIT_PRODUCT)));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Điện thoại Xiaomi":
                        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Xiaomi").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstXiaomi = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);

                                    if (p.getActive().equals("0"))
                                        continue;

                                    p.setId(dataSnapshot1.getKey());
                                    mContext.lstXiaomi.add(p);
                                }
                                if (mContext.lstXiaomi.size() < mContext.LIMIT_PRODUCT + 1) {
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstXiaomi));
                                }
                                else {
                                    Collections.shuffle(mContext.lstXiaomi);
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstXiaomi.subList(0, mContext.LIMIT_PRODUCT)));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Điện thoại Vivo":
                        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Vivo").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstVivo = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);

                                    if (p.getActive().equals("0"))
                                        continue;

                                    p.setId(dataSnapshot1.getKey());
                                    mContext.lstVivo.add(p);
                                }
                                if (mContext.lstVivo.size() < mContext.LIMIT_PRODUCT + 1) {
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstVivo));
                                }
                                else {
                                    Collections.shuffle(mContext.lstVivo);
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstVivo.subList(0, mContext.LIMIT_PRODUCT)));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Phụ kiện":
                        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Phụ kiện điện thoại").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mContext.lstOppo = new ArrayList<Product>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Product p = dataSnapshot1.getValue(Product.class);

                                    if (p.getActive().equals("0"))
                                        continue;

                                    p.setId(dataSnapshot1.getKey());
                                    mContext.lstOppo.add(p);
                                }
                                if (mContext.lstOppo.size() < mContext.LIMIT_PRODUCT + 1) {
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstOppo));
                                }
                                else {
                                    Collections.shuffle(mContext.lstOppo);
                                    recyclerView.setAdapter(new RecyclerViewAdapter(mContext.getContext(), mContext.lstOppo.subList(0, mContext.LIMIT_PRODUCT)));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
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

        TextView tv_category_title;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_title = (TextView) itemView.findViewById(R.id.home_btn_title);
            cardView = (LinearLayout) itemView.findViewById(R.id.home_btn_hot);
        }
    }
}
