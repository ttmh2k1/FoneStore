package hcmute.fonestore.notificationFragment;

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
import hcmute.fonestore.activity.cartActivity;
import hcmute.fonestore.object.category2;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterNotification;

public class notificationFragment extends Fragment {
    List<category2> lstBtn;
    ImageView cart;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notification, container, false);

        cart = root.findViewById(R.id.btn_notification_cart);
        recyclerView = root.findViewById(R.id.recyclerView_notification);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cartActivity.class);
                startActivity(intent);
            }
        });

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.notification_host_fragment, new notificationFragment()).commit();

        lstBtn = new ArrayList<>();
        lstBtn.add(new category2(null, R.drawable.ic_home_fill));
        lstBtn.add(new category2(null,R.drawable.ic_favorite_fill));
        lstBtn.add(new category2(null,R.drawable.ic_deal_fill));
        lstBtn.add(new category2(null,R.drawable.ic_comment_fill));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterNotification myAdapter = new RecyclerViewAdapterNotification(this,lstBtn);
        recyclerView.setAdapter(myAdapter);

        return root;
    }

}