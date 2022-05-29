package hcmute.fonestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.fonestore.MainActivity;
import hcmute.fonestore.R;

public class categoryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back, giohang, home, background;
    Button search;
    TextView category, danhmuc;
    String danhMuc;
    RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        back = findViewById(R.id.category_back);
        search = findViewById(R.id.category_search);
        giohang = findViewById(R.id.btn_category_giohang);
        home = findViewById(R.id.btn_category_home);
        category = findViewById(R.id.category_text);
        danhmuc = findViewById(R.id.category_danhmuc);
        background = findViewById(R.id.img_category);

        // Recieve data
        Intent intent = getIntent();
        danhMuc = intent.getExtras().getString("DanhMuc");

        search.setText(danhMuc);
        danhmuc.setText(danhMuc);

        back.setOnClickListener(this);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(categoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        category.setOnClickListener(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(categoryActivity.this, searchActivity.class);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recyclerView_category);
        recyclerView.setLayoutManager(layoutManager);

        switch (danhMuc) {
            case "Điện thoại Iphone":
                background.setBackgroundResource(R.drawable.img_iphone);
                queryProduct();
                break;
            case "Điện thoại Samsung":
                background.setBackgroundResource(R.drawable.img_samsung);
                queryProduct();
                break;
            case "Điện thoại Oppo":
                background.setBackgroundResource(R.drawable.img_oppo);
                queryProduct();
                break;
            case "Điện thoại Vivo":
                background.setBackgroundResource(R.drawable.img_vivo);
                queryProduct();
                break;
            case "Điện thoại Xiaomi":
                background.setBackgroundResource(R.drawable.img_xiaomi);
                queryProduct();
                break;
        }
    }

    private void queryProduct() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category_back:
                finish();
                break;
//            case R.id.btn_category_giohang:
//                intent = new Intent(categoryActivity.this, GioHangActivity.class);
//                startActivity(intent);
//                break;
            case R.id.category_text:
                intent = new Intent(categoryActivity.this, MainActivity.class);
                intent.putExtra("Selection", "List");
                startActivity(intent);
        }
    }
}
