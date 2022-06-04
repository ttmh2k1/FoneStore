package hcmute.fonestore.fragment.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.Activity.CategoryActivity;
import hcmute.fonestore.Object.CategoryWithThumnail;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapter;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterXiaomi;

public class listXiaomiFragment extends Fragment {
    ArrayList<Product> lstXiaomi;
    Button category;
    TextView load_more;
    static final int LIMIT_PRODUCT = 6;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_xiaomi, container, false);
        category = root.findViewById(R.id.btn_list_xiaomi);
        load_more = root.findViewById(R.id.load_more);

        load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", category.getText());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", category.getText());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_xiaomi);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        FirebaseDatabase.getInstance().getReference().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstXiaomi = new ArrayList<Product>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product p = ds.getValue(Product.class);
                    if (p.getActive().equals("0"))
                        continue;
                    p.setId(ds.getKey());
                    if (p.getCategory().equals("Điện thoại Xiaomi"))
                        lstXiaomi.add(p);
                }

                if (lstXiaomi.size() < LIMIT_PRODUCT + 1) {
                    myrv.setAdapter(new RecyclerViewAdapter(getContext(), lstXiaomi));
                }
                else {
                    Collections.shuffle(lstXiaomi);
                    myrv.setAdapter(new RecyclerViewAdapter(getContext(), lstXiaomi.subList(0, LIMIT_PRODUCT)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}