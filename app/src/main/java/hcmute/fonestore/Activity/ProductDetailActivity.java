package hcmute.fonestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.fonestore.R;

public class ProductDetailActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        textView = findViewById(R.id.describe);

        // Recieve data
        Intent intent = getIntent();
        textView.setText(intent.getExtras().getString("describe"));
    }
}
