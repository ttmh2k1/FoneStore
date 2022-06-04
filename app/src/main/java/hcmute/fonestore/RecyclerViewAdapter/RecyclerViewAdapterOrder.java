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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Bill;
import hcmute.fonestore.Object.CartItem;
import hcmute.fonestore.R;
import hcmute.fonestore.fragment.user.admin.OrderMgrActivity;

public class RecyclerViewAdapterOrder extends RecyclerView.Adapter<RecyclerViewAdapterOrder.MyViewHolder> {
    private Context context;
    private ArrayList<Bill> data;
    private RecyclerViewAdapterOrderDetail recyclerViewAdapterOrderDetail;
    private boolean isManager;

    public RecyclerViewAdapterOrder(Context context, ArrayList<Bill> data, boolean isManager) {
        this.context = context;
        this.data = data;
        this.isManager = isManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Bill b = data.get(position);


        if (!isManager) {
            holder.txt_bill_id.setText("Mã đơn hàng: " + b.getId() + "\nGiá tiền: " + String.format("%,3d VNĐ", calcPrice(b.getOrderProducts())) + "\nNgày đặt hàng: " + b.getOrderTime() + "\nTrạng thái: " + b.getStringStatus());
            holder.btn_admin_order_status_control.setVisibility(View.GONE);
        }
        else {
            if (b.getStatus() == 0) {
                holder.btn_admin_order_status_control.setText("Xác nhận đơn hàng");
                holder.btn_admin_order_status_control.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.setStatus(1);
                        holder.btn_cancel_order.setVisibility(View.GONE);
                        holder.btn_admin_order_status_control.setText("Xác nhận đã giao hàng");
                        FirebaseDatabase.getInstance().getReference().child("bills").child(b.getUid())
                                .child(b.getId())
                                .child("status")
                                .setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder.txt_bill_id.setText("Mã đơn hàng: " + b.getId() + "\nGiá tiền: " + String.format("%,3d VNĐ", calcPrice(b.getOrderProducts())) + "\nNgày đặt hàng: " + b.getOrderTime() + "\nTrạng thái: " + b.getStringStatus());
                                        Toast.makeText(context, "Đã xác nhận đơn hàng", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Xác nhận đơn hàng thất bại", Toast.LENGTH_LONG).show();
                                        b.setStatus(0);
                                    }
                                });
                    }
                });
            }
            else if (b.getStatus() == 1) {
                holder.btn_admin_order_status_control.setText("Xác nhận đã giao hàng");
                holder.btn_admin_order_status_control.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.setStatus(2);
                        holder.btn_admin_order_status_control.setVisibility(View.GONE);
                        FirebaseDatabase.getInstance().getReference().child("bills").child(b.getUid())
                                .child(b.getId())
                                .child("status")
                                .setValue(2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder.txt_bill_id.setText("Mã đơn hàng: " + b.getId() + "\nGiá tiền: " + String.format("%,3d VNĐ", calcPrice(b.getOrderProducts())) + "\nNgày đặt hàng: " + b.getOrderTime() + "\nTrạng thái: " + b.getStringStatus());
                                        Toast.makeText(context, "Đã xác nhận giao hàng", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Xác nhận giao hàng", Toast.LENGTH_LONG).show();
                                        b.setStatus(1);
                                    }
                                });
                    }
                });
            }
            else if (b.getStatus() == 2) {
                holder.btn_admin_order_status_control.setVisibility(View.GONE);
            }

            FirebaseDatabase.getInstance().getReference().child("user").child(b.getUid()).child("name")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    holder.txt_bill_id.setText("Mã đơn hàng: " + b.getId() + "\nGiá tiền: " + String.format("%,3d VNĐ", calcPrice(b.getOrderProducts())) + "\nNgày đặt hàng: " + b.getOrderTime() + "\nTrạng thái: " + b.getStringStatus());
                                    holder.txt_bill_id.setText("Người đặt hàng: " + snapshot.getValue(String.class) + "\nMã đơn hàng: " + b.getId() + "\nGiá tiền: " + String.format("%,3d VNĐ", calcPrice(b.getOrderProducts())) + "\nNgày đặt hàng: " + b.getOrderTime() + "\nTrạng thái: " + b.getStringStatus());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
        }

        if (b.getStatus() == 0) {
            holder.btn_cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase.getInstance().getReference().child("bills").child(currentUser.getUid())
                            .child(b.getId())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Hủy đơn hàng thành công!", Toast.LENGTH_LONG);
                                        data.remove(position);
                                        notifyDataSetChanged();
                                    }
                                    else {
                                        Toast.makeText(context, "Hủy đơn hàng thất bại!", Toast.LENGTH_LONG);
                                    }

                                }
                            });
                }
            });
        }
        else {
            holder.btn_cancel_order.setVisibility(View.GONE);
        }


        LinearLayoutManager layoutManagerRecent = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerView_order_detail.setLayoutManager(layoutManagerRecent);
        RecyclerViewAdapterOrderDetail recyclerViewAdapterOrderDetail = new RecyclerViewAdapterOrderDetail(context, b.getOrderProducts());
        holder.recyclerView_order_detail.setAdapter(recyclerViewAdapterOrderDetail);

        //recyclerViewAdapterOrderDetail.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Bill item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Bill> getData() {
        return data;
    }

    private Long calcPrice(ArrayList<CartItem> data) {
        long p = 0;
        for (CartItem c : data) {
            p += c.getProduct().getPrice() * c.getQty();
        }
        return p;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_bill_id;
        RecyclerView recyclerView_order_detail;
        Button btn_cancel_order, btn_admin_order_status_control;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_bill_id = itemView.findViewById(R.id.txt_bill_id);
            recyclerView_order_detail = itemView.findViewById(R.id.recyclerView_order_detail);
            btn_cancel_order = itemView.findViewById(R.id.btn_cancel_order);
            btn_admin_order_status_control = itemView.findViewById(R.id.btn_admin_order_status_control);
        }
    }
}
