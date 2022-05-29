package hcmute.fonestore.fragment.category;

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
import hcmute.fonestore.Activity.categoryActivity;
import hcmute.fonestore.Object.CategoryWithThumnail;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterSamsung;

public class listSamsungFragment extends Fragment {
    ArrayList<CategoryWithThumnail> samsung, tablet;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_samsung, container, false);
        category = root.findViewById(R.id.btn_list_samsung);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("category", category.getText());
                startActivity(intent);
            }
        });

        samsung = new ArrayList<>();
        samsung.add(new CategoryWithThumnail("Điện thoại Samsung", R.drawable.img_samsung));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_samsung);
        RecyclerViewAdapterSamsung myAdapter = new RecyclerViewAdapterSamsung(getContext(),samsung);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        tablet = new ArrayList<>();
        tablet.add(new CategoryWithThumnail("Máy tính bảng Samsung", R.drawable.img_tablet));

        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_tablet);
        RecyclerViewAdapterSamsung myAdapter1 = new RecyclerViewAdapterSamsung(getContext(),tablet);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);

        return root;
    }
}