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

public class listPhukienFragment extends Fragment {
    ArrayList<category2> sacDP, sacDT, taiPhone, opLung, khac;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_phukien, container, false);
        category = root.findViewById(R.id.btn_list_phukien);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("category", category.getText());
                startActivity(intent);
            }
        });

        sacDP = new ArrayList<>();
        sacDP.add(new category2("Sạc dự phòng", R.drawable.img_sacdp));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_sacDP);
        RecyclerViewAdapterIphone myAdapter = new RecyclerViewAdapterIphone(getContext(),sacDP);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        sacDT = new ArrayList<>();
        sacDT.add(new category2("Sạc điện thoại", R.drawable.img_sacdt));

        RecyclerView myrv1 = (RecyclerView) root.findViewById(R.id.recyclerView_sacDT);
        RecyclerViewAdapterIphone myAdapter1 = new RecyclerViewAdapterIphone(getContext(),sacDT);
        myrv1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv1.setAdapter(myAdapter1);

        taiPhone = new ArrayList<>();
        taiPhone.add(new category2("Tai nghe", R.drawable.img_taiphone));

        RecyclerView myrv2 = (RecyclerView) root.findViewById(R.id.recyclerView_taiPhone);
        RecyclerViewAdapterIphone myAdapter2 = new RecyclerViewAdapterIphone(getContext(),taiPhone);
        myrv2.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv2.setAdapter(myAdapter2);

        opLung = new ArrayList<>();
        opLung.add(new category2("Ốp điện thoại", R.drawable.img_oplung));

        RecyclerView myrv3 = (RecyclerView) root.findViewById(R.id.recyclerView_opLung);
        RecyclerViewAdapterIphone myAdapter3 = new RecyclerViewAdapterIphone(getContext(),opLung);
        myrv3.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv3.setAdapter(myAdapter3);

        khac = new ArrayList<>();
        khac.add(new category2("Phụ kiện khác", R.drawable.img_khac));

        RecyclerView myrv4 = (RecyclerView) root.findViewById(R.id.recyclerView_khac);
        RecyclerViewAdapterIphone myAdapter4 = new RecyclerViewAdapterIphone(getContext(),khac);
        myrv4.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv4.setAdapter(myAdapter4);

        return root;
    }
}