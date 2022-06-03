package hcmute.fonestore.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hcmute.fonestore.Animation.LoadingDialog;
import hcmute.fonestore.Object.Notification;
import hcmute.fonestore.R;
import hcmute.fonestore.Object.Comment;
import hcmute.fonestore.RandomString;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterComment;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView name;
    private TextView price;
    private TextView category;
    private TextView producer;
    private TextView brand;
    private TextView origin;
    private TextView describe;
    private TextView guarantee;
    private TextView currentUserName;
    private ImageView img;
    ImageView back, like, cart, writeComment, currentUserImage;
    EditText edtComment;
    Button choosen, detailsDescribe;
    BottomSheetDialog bottomDialog1;
    String sup, produceLocate, seller, userId, customerName, imageLink, imageUser;
    ArrayList<Comment> lstComment;
    RecyclerView recyclerView;

    String pid;

    DatabaseReference ref;
    FirebaseAuth mAuth;

    RandomString randomString = new RandomString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        name = findViewById(R.id.txt_name);
        price = findViewById(R.id.txt_price);
        category = findViewById(R.id.txt_catgory);
        producer = findViewById(R.id.txt_producer);
        brand = findViewById(R.id.txt_brand);
        origin = findViewById(R.id.txt_origin);
        describe = findViewById(R.id.txt_describe);
        guarantee = findViewById(R.id.txt_detailsGuarantee);
        recyclerView = findViewById(R.id.recyclerView_comment);
        edtComment = findViewById(R.id.edt_currentuser_comment);
        choosen = findViewById(R.id.btn_choosen);
        img = findViewById(R.id.category_thumbnail);
        back = findViewById(R.id.btn_product_back);
        like = findViewById(R.id.btn_product_like);
        cart = findViewById(R.id.btn_cart);
        writeComment = findViewById(R.id.btn_post);
        currentUserName = findViewById(R.id.currentuser_name);
        currentUserImage = findViewById(R.id.currentuser_comment);
        detailsDescribe = findViewById(R.id.details_describe);

        // Recieve data
        Intent intent = getIntent();
        pid = intent.getExtras().getString("id");

        loadData();
        loadComment();
        getCurrentUser();

        back.setOnClickListener(this);
        guarantee.setOnClickListener(this);
        like.setOnClickListener(this);
        cart.setOnClickListener(this);
        choosen.setOnClickListener(this);
        writeComment.setOnClickListener(this);
        detailsDescribe.setOnClickListener(this);
    }

    private void createBottomDialog() {
        if (bottomDialog1 == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
            final TextView txtSupplier, txtGuaranteeLocate, txtSeller;
            txtSupplier = view.findViewById(R.id.guarantee_sup);
            txtGuaranteeLocate = view.findViewById(R.id.guarantee_guaranteeLocate);
            txtSeller = view.findViewById(R.id.guarantee_seller);

            ref = FirebaseDatabase.getInstance().getReference().child("user").child(seller);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    txtSeller.setText(dataSnapshot.child("name").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProductActivity.this, "Tải thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
            txtSupplier.setText(sup);
            txtGuaranteeLocate.setText(produceLocate);

            bottomDialog1 = new BottomSheetDialog(this);
            bottomDialog1.setContentView(view);
        }
    }

    private void createComment() {
        if (edtComment.getText().equals("")) {
            Toast.makeText(ProductActivity.this, "Vui lòng nhập nhận xét!", Toast.LENGTH_SHORT).show();
        } else {
            if (!seller.equals(userId)) {
                ref = FirebaseDatabase.getInstance().getReference();

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                Comment comment = new Comment(randomString.nextString(), edtComment.getText().toString(), userId, customerName, imageUser);
                ref.child("product").child(pid).child("comment").child(comment.getId()).setValue(comment);
                edtComment.setText(null);

                Notification notification = new Notification(dateFormat.format(date), pid, customerName, imageLink);
                ref = FirebaseDatabase.getInstance().getReference();
                ref.child("notifications").child(seller).child(randomString.nextString()).setValue(notification).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProductActivity.this, "Đã gửi nhận xét!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(ProductActivity.this, "Bạn không thể nhận xét sản phẩm của mình!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void loadData() {
        ref = FirebaseDatabase.getInstance().getReference().child("product").child(pid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    finish();
                    Toast.makeText(ProductActivity.this, "Sản phẩm này đã bị xóa!", Toast.LENGTH_SHORT).show();
                } else {
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    price.setText(dataSnapshot.child("price").getValue().toString());
                    category.setText(dataSnapshot.child("category").getValue().toString());
                    producer.setText(dataSnapshot.child("producer").getValue().toString());
                    brand.setText(dataSnapshot.child("brand").getValue().toString());
                    origin.setText(dataSnapshot.child("origin").getValue().toString());
                    describe.setText(dataSnapshot.child("describe").getValue().toString());
                    Glide.with(ProductActivity.this).load(dataSnapshot.child("image").getValue().toString())
                            .placeholder(R.drawable.img_no_image).into(img);

//                    seller = dataSnapshot.child("seller").getValue().toString();
                    sup = dataSnapshot.child("brand").getValue().toString();
                    produceLocate = dataSnapshot.child("producer").getValue().toString();
                    imageLink = dataSnapshot.child("image").getValue().toString();

                    final String tempUrl = dataSnapshot.child("image").getValue().toString();
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final Dialog dialog = new Dialog(ProductActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            PhotoView photoView = new PhotoView(ProductActivity.this);
                            Glide.with(ProductActivity.this).load(tempUrl).into(photoView);
                            photoView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.addContentView(photoView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductActivity.this, "Tải thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComment() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        ref = FirebaseDatabase.getInstance().getReference().child("product").child(pid).child("comment");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstComment = new ArrayList<Comment>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Comment p = dataSnapshot1.getValue(Comment.class);
                    p.setId(dataSnapshot1.getKey());
                    lstComment.add(p);
                }
                final RecyclerViewAdapterComment myAdapter = new RecyclerViewAdapterComment(ProductActivity.this, lstComment);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductActivity.this, "Không thể tải comment!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLike() {
        final LoadingDialog loadingDialog = new LoadingDialog(ProductActivity.this);
        loadingDialog.startLoadingDialog();

        ref = FirebaseDatabase.getInstance().getReference();

        ref.child("favourite").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean existed = false;

                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (ds.getValue().toString().equals(pid)) {
                        existed = true;
                        break;
                    }
                }

                if (!existed) {
                    ref.child("favourite").child(userId).child(randomString.nextString()).setValue(pid).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductActivity.this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismissDialog();
                        }
                    });
                }
                else {
                    Toast.makeText(ProductActivity.this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
        });
    }

    private void addToCart() {
        final LoadingDialog loadingDialog = new LoadingDialog(ProductActivity.this);
        loadingDialog.startLoadingDialog();

        ref = FirebaseDatabase.getInstance().getReference();

        ref.child("cart").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean existed = false;

                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (ds.getValue().toString().equals(pid)) {
                        existed = true;
                        break;
                    }
                }

                if (!existed) {
                    ref.child("cart").child(userId).child(randomString.nextString()).setValue(pid).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductActivity.this, "Đã thêm vào Giỏ hàng!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismissDialog();
                        }
                    });
                }
                else {
                    Toast.makeText(ProductActivity.this, "Đã thêm vào Giỏ hàng!", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
        });
    }

    private void getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();

            ref = FirebaseDatabase.getInstance().getReference().child("user").child(userId);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currentUserName.setText(dataSnapshot.child("name").getValue().toString());
                    Glide.with(ProductActivity.this).load(dataSnapshot.child("avatar").getValue().toString()).placeholder(R.drawable.img_no_image).into(currentUserImage);
                    customerName = dataSnapshot.child("name").getValue().toString();
                    imageUser = dataSnapshot.child("avatar").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProductActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_product_back:
                finish();
                break;
            case R.id.txt_detailsGuarantee:
                createBottomDialog();
                bottomDialog1.show();
                break;
            case R.id.btn_product_like:
                if (seller.equals(userId))
                    Toast.makeText(ProductActivity.this, "Đây là sản phẩm bạn đăng bán", Toast.LENGTH_SHORT).show();
                else addLike();
                break;
            case R.id.btn_cart:
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_choosen:
                if (seller.equals(userId))
                    Toast.makeText(ProductActivity.this, "Đây là sản phẩm bạn đăng bán", Toast.LENGTH_SHORT).show();
                else addToCart();
                break;
            case R.id.btn_post:
                createComment();
                break;
            case R.id.details_describe:
                Intent intent1 = new Intent(ProductActivity.this, ProductDetailActivity.class);
                intent1.putExtra("describe", describe.getText());
                startActivity(intent1);
                break;
        }
    }
}