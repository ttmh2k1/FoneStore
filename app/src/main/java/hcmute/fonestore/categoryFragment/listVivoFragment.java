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
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterVivo;

public class listVivoFragment extends Fragment {
    ArrayList<category2> vivo;
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_vivo, container, false);
        category = root.findViewById(R.id.btn_list_vivo);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("category", category.getText());
                startActivity(intent);
            }
        });

        vivo = new ArrayList<>();
        vivo.add(new category2("Điện thoại Vivo", R.drawable.img_vivo));

        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recyclerView_vivo);
        RecyclerViewAdapterVivo myAdapter = new RecyclerViewAdapterVivo(getContext(),vivo);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);

        return root;
    }
}