package hcmute.fonestore.fragment.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.Activity.CategoryActivity;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapter;

public class listVivoFragment extends Fragment {
    ArrayList<Product> lstVivo;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_vivo, container, false);
        category = root.findViewById(R.id.btn_list_vivo);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", category.getText());
                startActivity(intent);
            }
        });

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_vivo);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        FirebaseDatabase.getInstance().getReference().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstVivo = new ArrayList<Product>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product p = ds.getValue(Product.class);
                    p.setId(ds.getKey());
                    if (p.getCategory().equals("Điện thoại Vivo"))
                        lstVivo.add(p);
                }

                RecyclerViewAdapter myAdapterVivo = new RecyclerViewAdapter(getContext(), lstVivo);
                myrv.setAdapter(myAdapterVivo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}