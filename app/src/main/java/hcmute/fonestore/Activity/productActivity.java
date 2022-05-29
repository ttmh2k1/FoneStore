package hcmute.fonestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import hcmute.fonestore.R;
import hcmute.fonestore.Object.Comment;

public class productActivity extends AppCompatActivity implements View.OnClickListener {
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
    BottomSheetDialog bottomDialod1;
    String sup, produceLocate, seller, id, customerName, imageLink, imageUser;
    ArrayList<Comment> lstComment;
    RecyclerView recyclerView;

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
//        name = intent.getExtras().getString("name");

//        loadData();
//        loadComment();
//        getCurrentUser();

        back.setOnClickListener(this);
        guarantee.setOnClickListener(this);
        like.setOnClickListener(this);
        cart.setOnClickListener(this);
        choosen.setOnClickListener(this);
        writeComment.setOnClickListener(this);
        detailsDescribe.setOnClickListener(this);
    }

    {
//        private void createBottomDialog () {
//        if (bottomDialod1 == null) {
//            View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
//            final TextView supplier, guaranteeLocate, seller;
//            supplier = view.findViewById(R.id.guarantee_sup);
//            guaranteeLocate = view.findViewById(R.id.guarantee_guaranteeLocate);
//            seller = view.findViewById(R.id.guarantee_seller);
//
//            ref = FirebaseDatabase.getInstance().getReference().child("user").child(seller);
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    seller.setText(dataSnapshot.child("name").getValue().toString());
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(productActivity.this, "Tải thất bại!", Toast.LENGTH_SHORT).show();
//                }
//            });
//            supplier.setText(sup);
//            guaranteeLocate.setText(produceLocate);
//
//            bottomDialod1 = new BottomSheetDialog(this);
//            bottomDialod1.setContentView(view);
//        }
//    }
//
//        private void createComment () {
//        if (edtComment.getText().equals("")) {
//            Toast.makeText(productActivity.this, "Vui lòng nhập nhận xét!", Toast.LENGTH_SHORT).show();
//        } else {
//            mData = FirebaseDatabase.getInstance().getReference();
//
//            Date date = Calendar.getInstance().getTime();
//            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//            comment comment = new comment(edtComment.getText().toString(), id, customerName, imageUser);
//            mData.child("product").child(name).child("comment").child(dateFormat.format(date)).setValue(comment);
//            edtComment.setText(null);
//
//            if (!seller.equals(id)) {
//                notification notification = new notification(dateFormat.format(date), id, customerName, imageLink);
//                mData = FirebaseDatabase.getInstance().getReference();
//                mData.child("notifications").child(seller).child(dateFormat.format(date)).setValue(notification);
//            }
//            Toast.makeText(productActivity.this, "Đã gửi nhận xét!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//        private void loadData () {
//        ref = FirebaseDatabase.getInstance().getReference().child("product").child(name);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue() == null) {
//                    finish();
//                    Toast.makeText(productActivity.this, "Sản phẩm này đã bị xóa!", Toast.LENGTH_SHORT).show();
//                } else {
//                    name.setText(dataSnapshot.child("name").getValue().toString());
//                    price.setText(dataSnapshot.child("price").getValue().toString());
//                    category.setText(dataSnapshot.child("category").getValue().toString());
//                    producer.setText(dataSnapshot.child("producer").getValue().toString());
//                    brand.setText(dataSnapshot.child("brand").getValue().toString());
//                    origin.setText(dataSnapshot.child("origin").getValue().toString());
//                    describe.setText(dataSnapshot.child("describe").getValue().toString());
//                    Glide.with(productActivity.this).load(dataSnapshot.child("image").getValue().toString())
//                            .placeholder(R.drawable.img_no_image).into(img);
//
//                    seller = dataSnapshot.child("seller").getValue().toString();
//                    sup = dataSnapshot.child("sup").getValue().toString();
//                    produceLocate = dataSnapshot.child("produceLocate").getValue().toString();
//                    imageLink = dataSnapshot.child("image").getValue().toString();
//
//                    final String tempUrl = dataSnapshot.child("image").getValue().toString();
//                    img.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            final Dialog dialog = new Dialog(productActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//                            PhotoView photoView = new PhotoView(productActivity.this);
//                            Glide.with(productActivity.this).load(tempUrl).into(photoView);
//                            photoView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
//                            dialog.addContentView(photoView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                            dialog.setCanceledOnTouchOutside(true);
//                            dialog.show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(productActivity.this, "Tải thất bại!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//        private void loadComment () {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setNestedScrollingEnabled(false);
//
//        ref = FirebaseDatabase.getInstance().getReference().child("product").child(name).child("comment");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstComment = new ArrayList<comment>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    comment p = dataSnapshot1.getValue(comment.class);
//                    lstComment.add(p);
//                }
//                final RecyclerViewAdapterComment myAdapter = new RecyclerViewAdapterComment(productActivity.this, lstComment);
//                recyclerView.setAdapter(myAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(productActivity.this, "Không thể tải comment!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//        private void addLike () {
//        final loadingDialog loadingDialog = new loadingDialog(productActivity.this);
//        loadingDialog.startLoadingDialog();
//        mData = FirebaseDatabase.getInstance().getReference();
//        final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");
//        Calendar calendar = Calendar.getInstance();
//        final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
//
//        // Get the data from an ImageView as bytes
//        img.setDrawingCacheEnabled(true);
//        img.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//
//        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                }
//                return mountainsRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    String url = task.getResult().toString();
//                    product product = new product(name.getText().toString(), String.valueOf(url), price.getText().toString(),
//                            category.getText().toString(), producer.getText().toString(), brand.getText().toString(),
//                            origin.getText().toString(), describe.getText().toString(), seller);
//                    mData.child("favorite").child(id).child(name.getText().toString()).setValue(product);
//                    loadingDialog.dismissDialog();
//                    Toast.makeText(productActivity.this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
//                } else {
//                    loadingDialog.dismissDialog();
//                    Toast.makeText(productActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//        private void addToCart () {
//        final LoadingDialog loadingDialog = new LoadingDialog(ProductActivity.this);
//        loadingDialog.startLoadingDialog();
//
//        mData = FirebaseDatabase.getInstance().getReference();
//
//        final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");
//        Calendar calendar = Calendar.getInstance();
//        final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
//
//        // Get the data from an ImageView as bytes
//        img.setDrawingCacheEnabled(true);
//        img.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//
//        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                }
//                return mountainsRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    String url = task.getResult().toString();
//                    product product = new product(name.getText().toString(), String.valueOf(url), price.getText().toString(),
//                            category.getText().toString(), producer.getText().toString(), brand.getText().toString(),
//                            origin.getText().toString(), describe.getText().toString(), seller);
//                    mData.child("cart").child(id).child(name.getText().toString()).setValue(product);
//                    loadingDialog.dismissDialog();
//                    Toast.makeText(productActivity.this, "Đã thêm vào Giỏ hàng!", Toast.LENGTH_SHORT).show();
//                } else {
//                    loadingDialog.dismissDialog();
//                    Toast.makeText(productActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//        private void getCurrentUser () {
//        mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//        id = currentUser.getUid();
//
//        ref = FirebaseDatabase.getInstance().getReference().child("User").child(id);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                currentUserName.setText(dataSnapshot.child("name").getValue().toString());
//                Glide.with(productActivity.this).load(dataSnapshot.child("avatar").getValue().toString()).placeholder(R.drawable.img_no_image).into(currentUserImage);
//                customerName = dataSnapshot.child("name").getValue().toString();
//                imageUser = dataSnapshot.child("avatar").getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(productActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_product_back:
                finish();
                break;
            case R.id.txt_detailsGuarantee:
//                createBottomDialog();
                bottomDialod1.show();
                break;
            case R.id.btn_product_like:
                if (seller.equals(id))
                    Toast.makeText(productActivity.this, "Đây là sản phẩm bạn đăng bán", Toast.LENGTH_SHORT).show();
//                else addLike();
                break;
            case R.id.btn_cart:
                Intent intent = new Intent(productActivity.this, cartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_choosen:
                if (seller.equals(id))
                    Toast.makeText(productActivity.this, "Đây là sản phẩm bạn đăng bán", Toast.LENGTH_SHORT).show();
//                else addToCart();
                break;
            case R.id.btn_post:
//                createComment();
                break;
            case R.id.details_describe:
                Intent intent1 = new Intent(productActivity.this, productDetailActivity.class);
                intent1.putExtra("describe", describe.getText());
                startActivity(intent1);
                break;
        }
    }
}