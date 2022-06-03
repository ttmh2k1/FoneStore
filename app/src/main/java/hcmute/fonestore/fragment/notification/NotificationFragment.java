package hcmute.fonestore.fragment.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.fonestore.R;
import hcmute.fonestore.Activity.CartActivity;
import hcmute.fonestore.Object.CategoryWithThumnail;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterNotification;

public class NotificationFragment extends Fragment {
    List<CategoryWithThumnail> lstBtn;
    ImageView cart;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_thongbao, container, false);

        cart = root.findViewById(R.id.btn_thongbao_giohang);
        recyclerView = root.findViewById(R.id.recyclerView_thongbao);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.notification_host_fragment, new NotificationFragment1()).commit();

        lstBtn = new ArrayList<>();
        lstBtn.add(new CategoryWithThumnail(null, R.drawable.ic_home_fill));
        lstBtn.add(new CategoryWithThumnail(null, R.drawable.ic_favorite_fill));
        lstBtn.add(new CategoryWithThumnail(null, R.drawable.ic_deal_fill));
        lstBtn.add(new CategoryWithThumnail(null, R.drawable.ic_comment_fill));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterNotification myAdapter = new RecyclerViewAdapterNotification(this, lstBtn);
        recyclerView.setAdapter(myAdapter);

        return root;
    }

}