package hcmute.fonestore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import hcmute.fonestore.activity.categoryActivity;
import hcmute.fonestore.activity.favoriteActivity;
import hcmute.fonestore.activity.hotActivity;
import hcmute.fonestore.activity.searchActivity;
import hcmute.fonestore.animation.viewPagerAdapter;
import hcmute.fonestore.object.category;
import hcmute.fonestore.object.category2;
import hcmute.fonestore.object.product;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterCategory;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterHomeCategory;
import hcmute.fonestore.recyclerViewAdapter.RecyclerViewAdapterHomeSearch;

public class homeFragment extends Fragment implements View.OnClickListener {

    ArrayList<category> lstBtnHot;
    public ArrayList<product> lstHot, lstIphone, lstSamsung, lstOppo, lstVivo, lstXiaomi;
    List<category> lstBtnSearch;
    List<category2> lstCategory;
    LinearLayout layoutFavorite;
    Button search, iphone, samsung, oppo, vivo, xiaomi;
    ImageButton category;
    ViewPager viewPager;
    ProgressBar loadingView, loadingViewHot;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView detailsFavorite, detailsHot, detailsCategory;
    Random rd;
    Intent intent;

    public View root;
    RecyclerView recyclerViewHot;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        detailsFavorite = root.findViewById(R.id.home_seen_details);
        detailsHot = root.findViewById(R.id.home_hot_details);
        detailsCategory = root.findViewById(R.id.home_category_details);
        search = root.findViewById(R.id.home_search);
        swipeRefreshLayout = root.findViewById(R.id.refreshLayout);
        iphone = root.findViewById(R.id.btn_iphone);
        samsung = root.findViewById(R.id.btn_samsung);
        oppo = root.findViewById(R.id.btn_oppo);
        vivo = root.findViewById(R.id.btn_vivo);
        xiaomi = root.findViewById(R.id.btn_xiaomi);
        category = root.findViewById(R.id.home_btn_category);
        viewPager = root.findViewById(R.id.viewPager);
        loadingView = root.findViewById(R.id.loading_view);
        loadingViewHot = root.findViewById(R.id.loading_hot);
        layoutFavorite = root.findViewById(R.id.layout_favorite);

        setViewPager();

        search.setOnClickListener(this);
        iphone.setOnClickListener(this);
        samsung.setOnClickListener(this);
        oppo.setOnClickListener(this);
        vivo.setOnClickListener(this);
        xiaomi.setOnClickListener(this);
        detailsFavorite.setOnClickListener(this);
        detailsHot.setOnClickListener(this);
        detailsCategory.setOnClickListener(this);
        category.setOnClickListener(this);

        setCategory();
        setLstBtnHot();
        setLstBtnSearch();
        loadData();
        setLoadMoreAction();

        return root;
    }

    private void setViewPager() {
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void setCategory() {
        lstCategory = new ArrayList<category2>();
        lstCategory.add(new category2("Điện thoại Iphone", R.drawable.img_iphone));
        lstCategory.add(new category2("Điện thoại Samsung", R.drawable.img_samsung));
        lstCategory.add(new category2("Điện thoại Oppo", R.drawable.img_oppo));
        lstCategory.add(new category2("Điện thoại Vivo", R.drawable.img_vivo));
        lstCategory.add(new category2("Điện thoại Xiaomi", R.drawable.img_xiaomi));
        lstCategory.add(new category2("Điện thoại Realme", R.drawable.img_realme));
        lstCategory.add(new category2("Điện thoại Nokia", R.drawable.img_nokia));
        lstCategory.add(new category2("Phụ kiện", R.drawable.img_phukien));
        lstCategory.add(new category2("Khác", R.drawable.img_khac));

        GridLayoutManager layoutManagerCategory = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewCategory = (RecyclerView) root.findViewById(R.id.recyclerView_home_category);
        recyclerViewCategory.setLayoutManager(layoutManagerCategory);
        RecyclerViewAdapterCategory myAdapterCategory = new RecyclerViewAdapterCategory(this, lstCategory);
        recyclerViewCategory.setAdapter(myAdapterCategory);
    }

    private void setLstBtnHot() {
        lstBtnHot = new ArrayList<category>();
        lstBtnHot.add(new category("Tất cả"));
        lstBtnHot.add(new category("Điện thoại Iphone"));
        lstBtnHot.add(new category("Điện thoại Samsung"));
        lstBtnHot.add(new category("Điện thoại Oppo"));
        lstBtnHot.add(new category("Điện thoại Vivo"));
        lstBtnHot.add(new category("Điện thoại Xiaomi"));
        lstBtnHot.add(new category("Phụ kiện"));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewBtnHot = (RecyclerView) root.findViewById(R.id.recyclerView_home_button);
        recyclerViewBtnHot.setLayoutManager(layoutManager2);
        RecyclerViewAdapterHomeCategory myAdapterBtnHot = new RecyclerViewAdapterHomeCategory(this, lstBtnHot);
        recyclerViewBtnHot.setAdapter(myAdapterBtnHot);
    }

    private void setLstBtnSearch() {
        lstBtnSearch = new ArrayList<category>();
        lstBtnSearch.add(new category("Sạc dự phòng"));
        lstBtnSearch.add(new category("Iphone"));
        lstBtnSearch.add(new category("Cục sạc"));
        lstBtnSearch.add(new category("Tai nghe bluetooth"));
        lstBtnSearch.add(new category("Ốp lưng"));
        lstBtnSearch.add(new category("Giá đỡ điện thoại"));
        lstBtnSearch.add(new category("Đồng hồ thông minh"));

        LinearLayoutManager layoutManagerSearch = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewBtnSearch = root.findViewById(R.id.recyclerView_home_search);
        recyclerViewBtnSearch.setLayoutManager(layoutManagerSearch);
        RecyclerViewAdapterHomeSearch myAdapterBtnSearch = new RecyclerViewAdapterHomeSearch(this, lstBtnSearch);
        recyclerViewBtnSearch.setAdapter(myAdapterBtnSearch);
    }

    private void loadData() {
        rd = new Random();
        LinearLayoutManager layoutManagerHot = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHot = (RecyclerView) root.findViewById(R.id.recyclerView_home_hot);
        recyclerViewHot.setLayoutManager(layoutManagerHot);

//        reference = FirebaseDatabase.getInstance().getReference().child("Product");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstHot = new ArrayList<>();
//                loadingViewHot.setVisibility(GONE);
//                List<product> full = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    full.add(p);
//                }
//                Collections.shuffle(full);
//
//                for (int i = 0; i < 5; ++i)
//                    lstHot.add(full.get(i));
//
//                RecyclerViewAdapter myAdapterHot = new RecyclerViewAdapter(getContext(), lstHot);
//                recyclerViewHot.setAdapter(myAdapterHot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

        LinearLayoutManager layoutManagerIphone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewIphone = (RecyclerView) root.findViewById(R.id.recyclerView_home_iphone);
        recyclerViewIphone.setLayoutManager(layoutManagerIphone);

//        refIphone = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Iphone");
//        refIphone.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstIphone = new ArrayList<product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    lstIphone.add(p);
//                }
//                RecyclerViewAdapter myAdapterIphone = new RecyclerViewAdapter(getContext(), lstIphone);
//                recyclerViewIphone.setAdapter(myAdapterIphone);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

        LinearLayoutManager layoutManagerSamsung = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewSamsung = (RecyclerView) root.findViewById(R.id.recyclerView_home_samsung);
        recyclerViewSamsung.setLayoutManager(layoutManagerSamsung);

//        refSamsung = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Samsung");
//        refSamsung.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstSamsung = new ArrayList<product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    lstSamsung.add(p);
//                }
//                RecyclerViewAdapter myAdapterQuanao = new RecyclerViewAdapter(getContext(), lstSamsung);
//                recyclerViewSamsung.setAdapter(myAdapterQuanao);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

        LinearLayoutManager layoutManagerOppo = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewOppo = (RecyclerView) root.findViewById(R.id.recyclerView_home_oppo);
        recyclerViewOppo.setLayoutManager(layoutManagerOppo);

//        refOppo = FirebaseDatabase.getInstance().getReference().child("Product");
//        refOppo.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstOppo = new ArrayList<product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    if (p.getCategory().equals("Điện thoại Oppo"))
//                        lstOppo.add(p);
//                }
//                RecyclerViewAdapter myAdapterOppo = new RecyclerViewAdapter(getContext(), lstOppo);
//                recyclerViewOppo.setAdapter(myAdapterOppo);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

        LinearLayoutManager layoutManagerVivo = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewVivo = (RecyclerView) root.findViewById(R.id.recyclerView_home_vivo);
        recyclerViewVivo.setLayoutManager(layoutManagerVivo);

//        refLamdep = FirebaseDatabase.getInstance().getReference().child("Product");
//        refLamdep.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstVivo = new ArrayList<product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    if (p.getCategory().equals("Điện thoại Vivo"))
//                        lstVivo.add(p);
//                }
//                RecyclerViewAdapter myAdapterVivo = new RecyclerViewAdapter(getContext(), lstVivo);
//                recyclerViewVivo.setAdapter(myAdapterVivo);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

        LinearLayoutManager layoutManagerXiaomi = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewXiaomi = (RecyclerView) root.findViewById(R.id.recyclerView_home_xiaomi);
        recyclerViewXiaomi.setLayoutManager(layoutManagerXiaomi);

//        refXiaomi = FirebaseDatabase.getInstance().getReference().child("Product");
//        refXiaomi.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstXiaomi = new ArrayList<product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    if (p.getCategory().equals("Điện thoại Xiaomi"))
//                        lstXiaomi.add(p);
//                }
//                RecyclerViewAdapter myAdapterXiaomi = new RecyclerViewAdapter(getContext(), lstXiaomi);
//                recyclerViewXiaomi.setAdapter(myAdapterXiaomi);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

        LinearLayoutManager layoutManagerSeen = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewSeen = (RecyclerView) root.findViewById(R.id.recyclerView_home_seen);
        recyclerViewSeen.setLayoutManager(layoutManagerSeen);

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        refSeen = FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid());
//        refSeen.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                loadingView.setVisibility(GONE);
//                lstSeen = new ArrayList<product>();
//                if (dataSnapshot.exists()) layoutFavorite.setVisibility(GONE);
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    product p = dataSnapshot1.getValue(product.class);
//                    lstSeen.add(p);
//                }
//                RecyclerViewAdapter myAdapterSeen = new RecyclerViewAdapter(getContext(), lstSeen);
//                recyclerViewSeen.setAdapter(myAdapterSeen);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void setLoadMoreAction() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_search:
                intent = new Intent(getActivity(), searchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_iphone:
                intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("Category", "Điện thoại Iphone");
                startActivity(intent);
                break;
            case R.id.btn_samsung:
                intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("Category", "Điện thoại Samsung");
                startActivity(intent);
                break;
            case R.id.btn_oppo:
                intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("Category", "Điện thoại Oppo");
                startActivity(intent);
                break;
            case R.id.btn_vivo:
                intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("Category", "Điện thoại Vivo");
                startActivity(intent);
                break;
            case R.id.btn_xiaomi:
                intent = new Intent(getActivity(), categoryActivity.class);
                intent.putExtra("Category", "Điện thoại Xiaomi");
                startActivity(intent);
                break;
            case R.id.home_seen_details:
                intent = new Intent(getActivity(), favoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.home_hot_details:
                intent = new Intent(getActivity(), hotActivity.class);
                startActivity(intent);
                break;
            case R.id.home_category_details:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("Selection", "List");
                startActivity(intent);
                break;
            case R.id.home_btn_category:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("Selection", "List");
                startActivity(intent);
                break;
        }
    }
}
