package hcmute.fonestore.categoryFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.activity.cartActivity;
import hcmute.fonestore.activity.searchActivity;
import hcmute.fonestore.object.category2;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterList;

public class listFragment extends Fragment {
    List<category2> lstProduct;
    ImageView cart;
    Button search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        search = root.findViewById(R.id.list_search);
        cart = root.findViewById(R.id.btn_list_cart);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cartActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), searchActivity.class);
                startActivity(intent);
            }
        });

        getFragmentManager().beginTransaction().add(R.id.list_host_fragment, new listCategoryFragment()).commit();

        lstProduct = new ArrayList<>();
        lstProduct.add(new category2("Gợi ý cho bạn", R.drawable.img_recommend));
        lstProduct.add(new category2("Điện thoại Iphone", R.drawable.img_iphone));
        lstProduct.add(new category2("Điện thoại Samsung",R.drawable.img_samsung));
        lstProduct.add(new category2("Điện thoại Vivo",R.drawable.img_vivo));
        lstProduct.add(new category2("Điện thoại Xiaomi",R.drawable.img_xiaomi));
        lstProduct.add(new category2("Phụ kiện điện thoại",R.drawable.img_phukien));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_list_category);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterList myAdapter = new RecyclerViewAdapterList(this, lstProduct);
        recyclerView.setAdapter(myAdapter);
        return root;
    }
}