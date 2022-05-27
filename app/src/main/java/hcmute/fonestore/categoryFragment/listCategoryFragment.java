package hcmute.fonestore.categoryFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.fonestore.R;
import hcmute.fonestore.object.category2;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterList2;

import java.util.ArrayList;
import java.util.List;

public class listCategoryFragment extends Fragment {
    List<category2> lstCategory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        lstCategory = new ArrayList<>();
        lstCategory.add(new category2("Điện thoại Iphone", R.drawable.img_iphone));
        lstCategory.add(new category2("Điện thoại Samsung",R.drawable.img_samsung));
        lstCategory.add(new category2("Điện thoại Realme",R.drawable.img_realme));
        lstCategory.add(new category2("Phụ kiện điện thoại",R.drawable.img_phukien));

        GridLayoutManager layoutManagerCategory = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewCategory = (RecyclerView) root.findViewById(R.id.recyclerView_list);
        recyclerViewCategory.setLayoutManager(layoutManagerCategory);
        RecyclerViewAdapterList2 myAdapterCategory = new RecyclerViewAdapterList2(this,lstCategory);
        recyclerViewCategory.setAdapter(myAdapterCategory);

        return root;
    }
}
