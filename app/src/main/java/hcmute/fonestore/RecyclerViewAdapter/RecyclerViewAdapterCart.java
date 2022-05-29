package hcmute.fonestore.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.fonestore.R;
import hcmute.fonestore.Activity.productActivity;
import hcmute.fonestore.Object.Product;

public class RecyclerViewAdapterCart extends RecyclerView.Adapter<RecyclerViewAdapterCart.MyViewHolder> {
    private Context mContext;
    private ArrayList<Product> mData;

    public RecyclerViewAdapterCart(Context mContext, ArrayList<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(mData.get(position).getName());
        holder.producer.setText("Cung cấp bởi " + mData.get(position).getProducer());
        holder.price.setText(mData.get(position).getPrice());
//        Glide.with(mContext).load(mData.get(position).getHinhAnh()).placeholder(R.drawable.img_no_image).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, productActivity.class);

                // passing data to the book activity
                intent.putExtra("Ten", mData.get(position).getName());

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

    public void restoreItem(Product item, int position) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Product> getData() {
        return mData;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView producer, name, price;
        ImageView image;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            price = (TextView) itemView.findViewById(R.id.cart_price);
            producer = (TextView) itemView.findViewById(R.id.cart_producer);
            name = (TextView) itemView.findViewById(R.id.cart_name);
            image = (ImageView) itemView.findViewById(R.id.imageView_cart);
            cardView =  itemView.findViewById(R.id.card_cart);
        }
    }
}
