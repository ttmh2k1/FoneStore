package hcmute.fonestore.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

//                switch (mData.get(position).getTitle()) {
//                    case "Tất cả":
//                        mContext.reference = FirebaseDatabase.getInstance().getReference().child("Product");
//                        mContext.reference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                mContext.lstHot = new ArrayList<Product>();
//                                List<Product> full = new ArrayList<>();
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    Product p = dataSnapshot1.getValue(Product.class);
//                                    full.add(p);
//                                }
//                                Collections.shuffle(full);
//
//                                for (int i = 0; i < 5; ++i)
//                                    mContext.lstHot.add(full.get(i));
//
//                                RecyclerViewAdapter myAdapterHot = new RecyclerViewAdapter(mContext.getContext(), mContext.lstHot);
//                                recyclerView.setAdapter(myAdapterHot);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        break;
//                    case "Điện thoại Iphone":
//                        mContext.refIphone = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("category").equalTo("Điện thoại Iphone");
//                        mContext.refIphone.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                mContext.lstIphone = new ArrayList<Product>();
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    Product p = dataSnapshot1.getValue(Product.class);
//                                    mContext.lstIphone.add(p);
//                                }
//                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.refIphone);
//                                recyclerView.setAdapter(myAdapter);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        break;
//                    case "Điện thoại Samsung":
//                        mContext.refSamsung = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("category").equalTo("Điện thoại Samsung");
////                        mContext.refSamsung.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                mContext.lstSamsung = new ArrayList<Product>();
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    Product p = dataSnapshot1.getValue(Product.class);
//                                    mContext.lstSamsung.add(p);
//                                }
//                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstSamsung);
//                                recyclerView.setAdapter(myAdapter);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        break;
//                    case "Điện thoại Oppo":
//                        mContext.refOppo = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("category").equalTo("Điện thoại Oppo");
////                        mContext.refOppo.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                mContext.lstOppo = new ArrayList<Product>();
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    Product p = dataSnapshot1.getValue(Product.class);
//                                    mContext.lstOppo.add(p);
//                                }
//                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstOppo);
//                                recyclerView.setAdapter(myAdapter);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        break;
//                    case "Điện thoại Xiaomi":
//                        mContext.refXiaomi = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("category").equalTo("Điện thoại Xiaomi");
////                        mContext.refXiaomi.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                mContext.lstXiaomi = new ArrayList<Product>();
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    Product p = dataSnapshot1.getValue(Product.class);
//                                    mContext.lstXiaomi.add(p);
//                                }
//                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstXiaomi);
//                                recyclerView.setAdapter(myAdapter);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        break;
//                    case "Phụ kiện điện thoại":
//                        mContext.refPhukien = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("category").equalTo("Phụ kiện điện thoại");
//                        mContext.refPhukien.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                mContext.lstOppo = new ArrayList<Product>();
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    Product p = dataSnapshot1.getValue(Product.class);
//                                    mContext.lstOppo.add(p);
//                                }
//                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext.getContext(), mContext.lstOppo);
//                                recyclerView.setAdapter(myAdapter);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(mContext.getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        break;
//                }
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
