package hcmute.fonestore.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder>{

    private Context context;
    private IBtnDeleteClick mClickBtnDelete;

    public interface IBtnDeleteClick {

        void deletePro(Product product);
    }

    public BillAdapter(Context context, ArrayList<Product> data, IBtnDeleteClick mClickBtnDelete) {
        this.context = context;
        this.data = data;
        this.mClickBtnDelete = mClickBtnDelete;
    }

    private ArrayList<Product> data;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bill, parent, false);
        return new BillAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = data.get(position);
        holder.name.setText(product.getName());
        holder.producer.setText("Cung cấp bởi " + product.getProducer());
        holder.price.setText(product.getFormattedPrice());
        Glide.with(context).load(product.getImage()).placeholder(R.drawable.img_no_image).into(holder.image);
        holder.tvQuantity1.setText(product.getQuantity().toString());
        holder.tvTotal.setText(String.format("%,d vnđ", product.getPrice()*product.getQuantity()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("id", product.getId());
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickBtnDelete.deletePro(product);
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
        TextView producer, name, price, tvQuantity, tvQuantity1, tvTotal, btnDelete;
        ImageView image;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            price = (TextView) itemView.findViewById(R.id.cart_price);
            producer = (TextView) itemView.findViewById(R.id.cart_producer);
            name = (TextView) itemView.findViewById(R.id.cart_name);
            image = (ImageView) itemView.findViewById(R.id.imageView_cart);
            cardView =  itemView.findViewById(R.id.card_cart);
            tvQuantity1 = itemView.findViewById(R.id.tvQuantity1);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
