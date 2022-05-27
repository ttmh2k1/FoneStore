package hcmute.fonestore.animation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(700);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
//                    FirebaseAuth auth = FirebaseAuth.getInstance();
//                    if (auth.getCurrentUser() == null)
//                        startActivity(new Intent(splashScreen.this, loginActivity.class));
//                    else
//                        startActivity(new Intent(splashScreen.this, MainActivity.class));
//                    finish();
                }
            }
        };
        timer.start();
    }
}
