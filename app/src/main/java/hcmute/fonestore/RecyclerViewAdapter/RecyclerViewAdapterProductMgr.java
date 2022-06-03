package hcmute.fonestore.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;

public class RecyclerViewAdapterProductMgr extends RecyclerView.Adapter<RecyclerViewAdapterProductMgr.MyViewHolder> {
    private Context context;
    private ArrayList<Product> data;

    public RecyclerViewAdapterProductMgr(Context context, ArrayList<Product> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product_mgr, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Product product = data.get(position);

        holder.txtProductName.setText(product.getName());
        holder.txtProductBrand.setText("Thương hiệu: " + product.getBrand());
        holder.txtProductCategory.setText("Loại: " + product.getCategory());
        holder.txtProductCreator.setText("Người tạo: " + product.getCreator());
        holder.txtProductPrice.setText("Giá: " + product.getFormattedPrice());

        Glide.with(context).load(product.getImage()).placeholder(R.drawable.img_no_image).into(holder.productImage);

        if (product.getActive().equals("1"))
            holder.btnActivate.setBackgroundColor(Color.GREEN);
        else
            holder.btnActivate.setBackgroundColor(Color.RED);

        holder.btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getActive().equals("1")) {
                    product.setActive("0");

                    FirebaseDatabase.getInstance().getReference().child("product").child(product.getId()).child("active").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Vô hiệu hóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            product.setActive("1");
                            Toast.makeText(context, "Opsss.... Something is wrong (" + e.getMessage() + ")", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    product.setActive("1");
                    FirebaseDatabase.getInstance().getReference().child("product").child(product.getId()).child("active").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Kích hoạt sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            product.setActive("0");
                            Toast.makeText(context, "Opsss.... Something is wrong (" + e.getMessage() + ")", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        holder.product_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("id", product.getId());
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
        TextView txtProductName, txtProductBrand, txtProductCategory, txtProductCreator, txtProductPrice;
        Button btnActivate;
        ImageView productImage;
        CardView product_card;

        public MyViewHolder(View itemView) {
            super(itemView);

            product_card = itemView.findViewById(R.id.product_card);
            productImage = itemView.findViewById(R.id.product_image);
            txtProductName = itemView.findViewById(R.id.product_name);
            txtProductBrand = itemView.findViewById(R.id.product_brand);
            txtProductCategory = itemView.findViewById(R.id.product_category);
            txtProductCreator = itemView.findViewById(R.id.product_creator);
            txtProductPrice = itemView.findViewById(R.id.product_price);

            btnActivate = itemView.findViewById(R.id.btn_activate);
        }
    }
}
