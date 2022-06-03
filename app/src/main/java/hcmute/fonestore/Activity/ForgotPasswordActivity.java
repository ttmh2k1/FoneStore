package hcmute.fonestore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hcmute.fonestore.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText txt_reset_email;
    Button btn_reset, btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        txt_reset_email = findViewById(R.id.txt_reset_email);
        btn_reset = findViewById(R.id.btn_reset);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_reset_email.getText().toString();
                if (email.equals("") || !isEmailValid(email)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập email hợp lệ!", Toast.LENGTH_LONG).show();
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Một email đặt lại mật khẩu đã được gửi đến địa chỉ email " + email, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ForgotPasswordActivity.this, "Có lỗi xảy ra, vui lòng thử lại (" + task.getException().getMessage() + ")", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
}