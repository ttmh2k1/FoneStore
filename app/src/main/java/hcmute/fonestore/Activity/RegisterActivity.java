package hcmute.fonestore.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hcmute.fonestore.MainActivity;
import hcmute.fonestore.Object.User;
import hcmute.fonestore.R;
import hcmute.fonestore.Animation.LoadingDialog;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button register;
    EditText user, password, address, name;
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    int REQUEST_CODE_IMAGE = 1;
    ImageView imageAdd;
    LoadingDialog loadingDialog;

    int image = 0;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.register_user);
        password = findViewById(R.id.register_pass);
        name = findViewById(R.id.register_ten);
        register = findViewById(R.id.btn_register);
        address = findViewById(R.id.register_address);
        imageAdd = findViewById(R.id.cardview_avatar);

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equals("") || password.getText().toString().equals("") || name.getText().toString().equals("") || address.getText().toString().equals("") || image == 0) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng thêm đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!isEmailValid(user.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đúng địa chỉ email!", Toast.LENGTH_SHORT).show();
                } else {
                    DangKi();
                }
            }
        });
    }

    private void DangKi() {
        loadingDialog = new LoadingDialog(RegisterActivity.this);
        mAuth = FirebaseAuth.getInstance();

        loadingDialog.startLoadingDialog();
        String email = user.getText().toString();
        String pass = password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    final FirebaseUser user = mAuth.getCurrentUser();

                    mData = FirebaseDatabase.getInstance().getReference();
                    final StorageReference storageRef = storage.getReference();

                    final Date date = Calendar.getInstance().getTime();
                    final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    final StorageReference mountainsRef = storageRef.child("image" + date.getTime() + ".png");

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
                                User user1 = new User(user.getUid(), name.getText().toString(), String.valueOf(url),
                                        user.getEmail(), address.getText().toString(), dateFormat.format(date), "kh");

                                mData.child("user").child(user.getUid()).setValue(user1);

                                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        if (!task.isSuccessful()) {
                                            finish();
                                            loadingDialog.dismissDialog();
                                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            return;
                                        }

                                        // Get new Instance ID token
                                        String token = task.getResult();
                                        FirebaseDatabase.getInstance().getReference("user")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("token")
                                                .setValue(token);

                                        finish();
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(RegisterActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });

                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
}

