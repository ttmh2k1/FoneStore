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
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterSamsung;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterVivo;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterXiaomi;

public class listXiaomiFragment extends Fragment {
    ArrayList<category2> xiaomi, pad;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_xiaomi, container, false);
        category = root.findViewById(R.id.btn_list_xiaomi);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("category", category.getText());
                startActivity(intent);
            }
        });

        xiaomi = new ArrayList<>();
        xiaomi.add(new category2("Điện thoại Xiaomi", R.drawable.img_xiaomi));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_xiaomi);
        RecyclerViewAdapterXiaomi myAdapter = new RecyclerViewAdapterXiaomi(getContext(),xiaomi);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        pad = new ArrayList<>();
        pad.add(new category2("Xiaomi pad", R.drawable.img_pad));

        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_pad);
        RecyclerViewAdapterXiaomi myAdapter1 = new RecyclerViewAdapterXiaomi(getContext(),pad);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);

        return root;
    }
}