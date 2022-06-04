package hcmute.fonestore.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import hcmute.fonestore.Object.CartItem;
import hcmute.fonestore.R;
import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Product;

public class RecyclerViewAdapterCart extends RecyclerView.Adapter<RecyclerViewAdapterCart.MyViewHolder> {
    private Context context;
    private ArrayList<CartItem> data;

    public RecyclerViewAdapterCart(Context context, ArrayList<CartItem> data) {
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
        CartItem c = data.get(position);

        holder.name.setText(c.getProduct().getName());
        holder.producer.setText("Cung cấp bởi " + c.getProduct().getProducer());
        holder.price.setText(c.getProduct().getFormattedPrice());
        Glide.with(context).load(c.getProduct().getImage()).placeholder(R.drawable.img_no_image).into(holder.image);

        holder.cart_qty.setText(String.valueOf(c.getQty()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("id", c.getProduct().getId());
                context.startActivity(intent);
            }
        });

        holder.btn_dec_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getQty() > 1) {
                    int bk = c.getQty();
                    c.setQty(c.getQty() - 1);
                    FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("cart")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(c.getKey())
                            .child("qty")
                            .setValue(bk - 1)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    c.setQty(c.getQty() + 1);
                                    Toast.makeText(context, "Không thể cập nhật giỏ hàng!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    holder.cart_qty.setText(String.valueOf(c.getQty()));
                }
            }
        });

        holder.btn_inc_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bk = c.getQty();
                c.setQty(c.getQty() + 1);
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("cart")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(c.getKey())
                        .child("qty")
                        .setValue(bk + 1)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                c.setQty(c.getQty() - 1);
                                Toast.makeText(context, "Không thể cập nhật giỏ hàng!", Toast.LENGTH_SHORT).show();
                            }
                        });
                holder.cart_qty.setText(String.valueOf(c.getQty()));
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

    public void restoreItem(CartItem item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<CartItem> getData() {
        return data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView producer, name, price, cart_qty;
        ImageView image;
        CardView cardView;
        Button btn_dec_qty, btn_inc_qty;

        public MyViewHolder(View itemView) {
            super(itemView);

            price = (TextView) itemView.findViewById(R.id.cart_price);
            producer = (TextView) itemView.findViewById(R.id.cart_producer);
            name = (TextView) itemView.findViewById(R.id.cart_name);
            image = (ImageView) itemView.findViewById(R.id.imageView_cart);
            cardView =  itemView.findViewById(R.id.card_cart);

            btn_dec_qty =  itemView.findViewById(R.id.btn_dec_qty);
            btn_inc_qty =  itemView.findViewById(R.id.btn_inc_qty);
            cart_qty =  itemView.findViewById(R.id.cart_qty);
        }
    }
}
