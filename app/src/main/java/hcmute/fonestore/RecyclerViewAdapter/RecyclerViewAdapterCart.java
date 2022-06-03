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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hcmute.fonestore.R;
import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Product;

public class RecyclerViewAdapterCart extends RecyclerView.Adapter<RecyclerViewAdapterCart.MyViewHolder> {
    private Context context;
    private ArrayList<Product> data;

    public RecyclerViewAdapterCart(Context context, ArrayList<Product> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Product p = data.get(position);

        holder.name.setText(p.getName());
        holder.producer.setText("Cung cấp bởi " + p.getProducer());
        holder.price.setText(p.getFormattedPrice());
        Glide.with(context).load(p.getImage()).placeholder(R.drawable.img_no_image).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("id", p.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Product item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Product> getData() {
        return data;
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
