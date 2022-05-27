package hcmute.fonestore.categoryFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.fonestore.R;
import hcmute.fonestore.activity.categoryActivity;
import hcmute.fonestore.object.category2;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterIphone;

public class listIphoneFragment extends Fragment {
    ArrayList<category2> iphone, ipad, airpods;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_iphone, container, false);
        category = root.findViewById(R.id.btn_list_iphone);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("category", category.getText());
                startActivity(intent);
            }
        });

        iphone = new ArrayList<>();
        iphone.add(new category2("Điện thoại Iphone", R.drawable.img_iphone));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_iphone);
        RecyclerViewAdapterIphone myAdapter = new RecyclerViewAdapterIphone(getContext(),iphone);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        ipad = new ArrayList<>();
        ipad.add(new category2("Máy tính bảng Apple", R.drawable.img_ipad));

        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_ipad);
        RecyclerViewAdapterIphone myAdapter1 = new RecyclerViewAdapterIphone(getContext(),ipad);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);

        airpods = new ArrayList<>();
        airpods.add(new category2("Tai nghe Bluetooth", R.drawable.img_airpods));

        RecyclerView myrv2 = (RecyclerView) root.findViewById(R.id.recyclerView_airpods);
        RecyclerViewAdapterIphone myAdapter2 = new RecyclerViewAdapterIphone(getContext(),airpods);
        myrv2.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv2.setAdapter(myAdapter2);
        return root;
    }
}