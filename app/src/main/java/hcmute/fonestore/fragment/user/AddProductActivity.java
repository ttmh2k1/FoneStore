package hcmute.fonestore.fragment.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import hcmute.fonestore.Animation.LoadingDialog;
import hcmute.fonestore.R;
import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.RandomString;

public class AddProductActivity extends AppCompatActivity {
    ImageView btnSave, btnRefresh, btnBack;
    Button btnChoose;
    ImageView imageAdd;
    EditText edtProductName, edtPrice, edtProducer, edtBrand, edtOrigin, edtDescribe;
    int REQUEST_CODE_IMAGE = 1;
    BottomSheetDialog bottomDialog;

    String userId;
    String productName, category;
    Product product;
    LoadingDialog loadingDialog;

    Uri uri = null;
    int image = 0;

    RandomString randomString = new RandomString();

    FirebaseAuth mAuth;
    DatabaseReference mData, delete;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnBack = findViewById(R.id.btn_add_back);
        btnSave = findViewById(R.id.btn_save);
        btnRefresh = findViewById(R.id.btn_refresh);
        imageAdd = findViewById(R.id.image_add);
        edtProductName = findViewById(R.id.edt_productName);
        edtPrice = findViewById(R.id.edt_price);
        btnChoose = findViewById(R.id.edt_category);
        edtProducer = findViewById(R.id.edt_producer);
        edtBrand = findViewById(R.id.edt_brand);
        edtOrigin = findViewById(R.id.edt_origin);
        edtDescribe = findViewById(R.id.edt_describe);
        imageAdd.setBackgroundResource(R.drawable.img_no_image);

        getCurrentUser();

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE);//one can be replaced with any action code
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtProductName.getText().toString().equals("") || edtPrice.getText().toString().equals("") ||
                        btnChoose.getText().toString().equals("") || edtProducer.getText().toString().equals("") ||
                        edtBrand.getText().toString().equals("") || edtOrigin.getText().toString().equals("") ||
                        edtDescribe.getText().toString().equals("") || image == 0) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog = new LoadingDialog(AddProductActivity.this);
                    loadingDialog.startLoadingDialog();
                    addProduct();
                }
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void reset() {
        imageAdd.setBackgroundResource(R.drawable.img_no_image);
        edtProductName.setText(null);
        edtPrice.setText(null);
        btnChoose.setText("Chọn");
        edtProducer.setText(null);
        edtBrand.setText(null);
        edtOrigin.setText(null);
        edtDescribe.setText(null);
    }

    private void ShowMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnChoose);
        popupMenu.getMenuInflater().inflate(R.menu.category_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_iphone:
                        btnChoose.setText("Điện thoại Iphone");
                        break;
                    case R.id.menu_samsung:
                        btnChoose.setText("Điện thoại Samsung");
                        break;
                    case R.id.menu_oppo:
                        btnChoose.setText("Điện thoại Oppo");
                        break;
                    case R.id.menu_vivo:
                        btnChoose.setText("Điện thoại Vivo");
                        break;
                    case R.id.menu_xiaomi:
                        btnChoose.setText("Điện thoại Xiaomi");
                        break;
                    case R.id.menu_realme:
                        btnChoose.setText("Điện thoại Realme");
                        break;
                    case R.id.menu_nokia:
                        btnChoose.setText("Điện thoại Nokia");
                        break;
                    case R.id.menu_phukien:
                        btnChoose.setText("Phụ kiện điện thoại");
                        break;
                    case R.id.menu_other:
                        btnChoose.setText("Khác");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void addProduct() {
        mData = FirebaseDatabase.getInstance().getReference();
        productName = String.valueOf(edtProductName.getText());
        category = String.valueOf(btnChoose.getText());

        final StorageReference storageRef = storage.getReference();
        Calendar calendar = Calendar.getInstance();
        final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

        UploadTask uploadTask = mountainsRef.putFile(uri);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                }
                return mountainsRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String url = task.getResult().toString();

                    product = new Product(
                            randomString.nextString(),
                            edtProductName.getText().toString(),
                            url,
                            Integer.parseInt(edtPrice.getText().toString()),
                            btnChoose.getText().toString(),
                            edtProducer.getText().toString(),
                            edtBrand.getText().toString(),
                            edtOrigin.getText().toString(),
                            edtDescribe.getText().toString(),
                            userId);

                    mData.child("product").child(product.getId()).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddProductActivity.this, "Tải lên thành công!", Toast.LENGTH_SHORT).show();
                                createBottomDialog();
                                bottomDialog.show();
                            }
                            loadingDialog.dismissDialog();
                        }
                    });
                } else {
                    Toast.makeText(AddProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            }
        });

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddProductActivity.this, "Tải lên thất bại!", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
        });/*.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
                Toast.makeText(AddProductActivity.this, "Tải lên thành công!", Toast.LENGTH_SHORT).show();

                // tạo node trên Database
                Product product = new product(edtProductName.getText().toString(),
                            String.valueOf(url),
                            edtPrice.getText().toString() + " đ",
                            btnChoose.getText().toString(),
                            edtProducer.getText().toString(),
                            edtBrand.getText().toString(),
                            edtOrigin.getText().toString(),
                            edtDescribe.getText().toString(),
                        userId);
                mData.child("product").child(edtProductName.getText().toString()).setValue(product);
                loadingDialog.dismissDialog();
            }
        });*/

    }

    private void createBottomDialog() {
        if (bottomDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_add, null);
            LinearLayout linearLayout;
            TextView name, producer, price;
            Button add, delete;
            ImageView addImage;

            linearLayout = view.findViewById(R.id.layout_details);
            name = view.findViewById(R.id.add_name);
            producer = view.findViewById(R.id.add_producer);
            price = view.findViewById(R.id.add_price);
            add = view.findViewById(R.id.add_new);
            delete = view.findViewById(R.id.add_delete);
            addImage = view.findViewById(R.id.add_image);

            name.setText(edtProductName.getText());
            producer.setText(edtProducer.getText());
            price.setText(edtPrice.getText() + " đ");
            addImage.setImageURI(uri);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddProductActivity.this, ProductActivity.class);
                    intent.putExtra("id", product.getId());
                    startActivity(intent);
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomDialog.dismiss();
                    reset();
                    edtProductName.requestFocus();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StorageReference photoRef = storage.getReferenceFromUrl(product.getImage());

                    FirebaseDatabase.getInstance().getReference().child("product").child(product.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddProductActivity.this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AddProductActivity.this, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    photoRef.delete();
                    bottomDialog.dismiss();
                    reset();
                    edtProductName.requestFocus();
                }
            });
            bottomDialog = new BottomSheetDialog(this);
            bottomDialog.setContentView(view);
        }
    }

    private void getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            imageAdd.setImageURI(uri);
            image = 1;
        }
    }
}
