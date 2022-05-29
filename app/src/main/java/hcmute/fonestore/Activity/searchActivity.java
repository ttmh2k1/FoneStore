package hcmute.fonestore.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.fonestore.R;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCart;

public class searchActivity extends AppCompatActivity {
    EditText searchText;
    ImageView back, cart;
    ArrayList<Product> lstSearch;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.edt_search);
        back = findViewById(R.id.btn_search_back);
        cart = findViewById(R.id.btn_search_cart);
        recyclerView = findViewById(R.id.recyclerView_search);

        loadData();

        searchText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (searchText != null) {
            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    search(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(searchActivity.this, cartActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void search(String s) {
        ArrayList<Product> myList = new ArrayList<>();
        for (Product object : lstSearch) {
            if (object.getName().toLowerCase().contains(s.toLowerCase()) || object.getProducer().toLowerCase().contains(s.toLowerCase())) {
                myList.add(object);
            }
        }
        LinearLayoutManager layoutManagerCart = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerViewCart = (RecyclerView) findViewById(R.id.recyclerView_search);
        recyclerViewCart.setLayoutManager(layoutManagerCart);
        RecyclerViewAdapterCart myAdapterCart = new RecyclerViewAdapterCart(searchActivity.this, myList);
        recyclerViewCart.setAdapter(myAdapterCart);
    }

    private void loadData() {
        String search = "";

        Bundle getSearch = getIntent().getExtras();
        if (getSearch != null) {
            search = getSearch.getString("Search");
            searchText.setText(search);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerViewAdapterCart myAdapter = new RecyclerViewAdapterCart(searchActivity.this, lstSearch);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
