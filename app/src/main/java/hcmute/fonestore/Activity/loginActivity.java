package hcmute.fonestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.fonestore.R;
import hcmute.fonestore.Animation.loadingDialog;

public class loginActivity extends AppCompatActivity {
    TextView forgot, register;
    Button login;
    EditText user, password;
    hcmute.fonestore.Animation.loadingDialog loadingDialog;
    private boolean doubleClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.login_user);
        password = findViewById(R.id.login_pass);
        register = findViewById(R.id.text_register);
        login = findViewById(R.id.btn_login);
        forgot = findViewById(R.id.forgot_password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangNhap();
            }
        });
    }

    private void DangNhap() {
        if (user.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(loginActivity.this, "Thêm đầy đủ thông tin tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            loadingDialog = new loadingDialog(loginActivity.this);
//            mAuth = FirebaseAuth.getInstance();
            loadingDialog.startLoadingDialog();
            String email = user.getText().toString();
            String pass = password.getText().toString();
//            mAuth.signInWithEmailAndPassword(email, pass)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//
//                                FirebaseInstanceId.getInstance().getInstanceId()
//                                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                                if (!task.isSuccessful()) {
//                                                    finish();
//                                                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
//                                                    startActivity(intent);
//                                                    Toast.makeText(loginActivity.this, "Đăng nhập thành công!",
//                                                            Toast.LENGTH_SHORT).show();
//                                                    loadingDialog.dismissDialog();
//                                                    return;
//                                                }
//
//                                                // Get new Instance ID token
//                                                String token = task.getResult().getToken();
//                                                FirebaseDatabase.getInstance().getReference("User")
//                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                        .child("token")
//                                                        .setValue(token);
//
//                                                finish();
//                                                Intent intent = new Intent(loginActivity.this, MainActivity.class);
//                                                startActivity(intent);
//                                                Toast.makeText(loginActivity.this, "Đăng nhập thành công!",
//                                                        Toast.LENGTH_SHORT).show();
//                                                loadingDialog.dismissDialog();
//                                            }
//                                        });
//                            } else {
//                                Toast.makeText(loginActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                loadingDialog.dismissDialog();
//                            }
//                        }
//                    });
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleClick)
            finish();
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        doubleClick = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClick = false;
            }
        }, 1500);
    }
}

