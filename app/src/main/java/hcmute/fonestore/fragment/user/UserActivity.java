package hcmute.fonestore.fragment.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hcmute.fonestore.R;
import hcmute.fonestore.fragment.user.admin.ProductMgrActivity;

public class UserActivity extends AppCompatActivity {
    Button user_update;
    TextView email, address, date, id, cart, favourite, numPostedProduct, name;
    LinearLayout layout_posted_product;
    ImageView avatar;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    Query query;
    Uri imageURI;
    String avtUrl;
    String currentAddress;

    final int REQUEST_CODE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = findViewById(R.id.text_name);
        cart = findViewById(R.id.text_cart);
        favourite = findViewById(R.id.text_favorite);
        numPostedProduct = findViewById(R.id.txt_posted_product);
        email = findViewById(R.id.text_email);
        address = findViewById(R.id.text_address);
        date = findViewById(R.id.text_date);
        id = findViewById(R.id.text_id);
        avatar = findViewById(R.id.avatar);
        layout_posted_product = findViewById(R.id.layout_posted_product);
        user_update = findViewById(R.id.user_update);

        imageURI = null;

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE);
            }
        });

        user_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageURI != null) {
                    Date date = Calendar.getInstance().getTime();
                    StorageReference file = FirebaseStorage.getInstance().getReference().child("image" + date.getTime() + ".png");

                    UploadTask uploadTask = file.putFile(imageURI);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                            }
                            return file.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String url = task.getResult().toString();
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference()
                                    .child("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("avatar")
                                    .setValue(url)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseStorage.getInstance().getReferenceFromUrl(avtUrl).delete();
                                            avtUrl = url;
                                            Toast.makeText(UserActivity.this, "Cập nhật ảnh đại diện thành công!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UserActivity.this, "Cập nhật ảnh đại diện thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                    imageURI = null;
                }

                String addr = address.getText().toString();
                if (!addr.equals("") && !currentAddress.equals(addr)) {
                    FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("address")
                            .setValue(addr)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(UserActivity.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UserActivity.this, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        loadData();
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentAddress = dataSnapshot.child("address").getValue().toString();
                avtUrl = dataSnapshot.child("avatar").getValue().toString();

                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                date.setText(dataSnapshot.child("joinTime").getValue().toString());
                id.setText(dataSnapshot.child("uid").getValue().toString());
                address.setText(currentAddress);

                Glide.with(UserActivity.this).load(avtUrl)
                        .placeholder(R.drawable.img_no_image).into(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    i++;
                }
                cart.setText(Integer.toString(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("favourite").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    i++;
                }
                favourite.setText(Integer.toString(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid()).child("role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(String.class).equals("admin")) {
                    query = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("creator").equalTo(currentUser.getUid());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int i = 0;
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                i++;
                            }
                            numPostedProduct.setText(Integer.toString(i));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(UserActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    layout_posted_product.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            imageURI = data.getData();
            avatar.setImageURI(imageURI);
        }
    }
}
