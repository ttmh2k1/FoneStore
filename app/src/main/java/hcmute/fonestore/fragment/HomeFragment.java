package hcmute.fonestore.fragment;

import static android.view.View.GONE;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import hcmute.fonestore.Activity.CategoryActivity;
import hcmute.fonestore.Activity.FavoriteActivity;
import hcmute.fonestore.Activity.HotActivity;
import hcmute.fonestore.Activity.SearchActivity;
import hcmute.fonestore.Animation.viewPagerAdapter;
import hcmute.fonestore.MainActivity;
import hcmute.fonestore.Object.Category;
import hcmute.fonestore.Object.CategoryWithThumnail;
import hcmute.fonestore.Object.Product;
import hcmute.fonestore.R;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapter;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterCategory;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterHomeCategory;
import hcmute.fonestore.RecyclerViewAdapter.RecyclerViewAdapterHomeSearch;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ArrayList<Category> lstBtnHot;
    public ArrayList<Product> lstHot, lstFavourite, lstIphone, lstSamsung, lstOppo, lstVivo, lstXiaomi;
    List<Category> lstBtnSearch;
    List<CategoryWithThumnail> lstCategory;
    LinearLayout layoutFavorite;
    Button search, iphone, samsung, oppo, vivo, xiaomi;
    ImageButton category, btnFavourite;
    ViewPager viewPager;
    ProgressBar loadingView, loadingViewHot;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView detailsFavorite, detailsHot, detailsCategory;
    Random rd;
    Intent intent;

    public View root;
    RecyclerView recyclerViewHot;
    RecyclerViewAdapter myAdapterFavourite;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    public static final int LIMIT_PRODUCT = 5;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

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
        btnFavourite = root.findViewById(R.id.home_btn_favorite);
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
        btnFavourite.setOnClickListener(this);

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
        lstCategory = new ArrayList<CategoryWithThumnail>();
        lstCategory.add(new CategoryWithThumnail("Điện thoại Iphone", R.drawable.img_iphone));
        lstCategory.add(new CategoryWithThumnail("Điện thoại Samsung", R.drawable.img_samsung));
        lstCategory.add(new CategoryWithThumnail("Điện thoại Oppo", R.drawable.img_oppo));
        lstCategory.add(new CategoryWithThumnail("Điện thoại Vivo", R.drawable.img_vivo));
        lstCategory.add(new CategoryWithThumnail("Điện thoại Xiaomi", R.drawable.img_xiaomi));
        lstCategory.add(new CategoryWithThumnail("Điện thoại Realme", R.drawable.img_realme));
        lstCategory.add(new CategoryWithThumnail("Điện thoại Nokia", R.drawable.img_nokia));
        lstCategory.add(new CategoryWithThumnail("Phụ kiện", R.drawable.img_phukien));
        lstCategory.add(new CategoryWithThumnail("Khác", R.drawable.img_khac));

        GridLayoutManager layoutManagerCategory = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewCategory = (RecyclerView) root.findViewById(R.id.recyclerView_home_category);
        recyclerViewCategory.setLayoutManager(layoutManagerCategory);
        RecyclerViewAdapterCategory myAdapterCategory = new RecyclerViewAdapterCategory(this, lstCategory);
        recyclerViewCategory.setAdapter(myAdapterCategory);
    }

    private void setLstBtnHot() {
        lstBtnHot = new ArrayList<Category>();
        lstBtnHot.add(new Category("Tất cả"));
        lstBtnHot.add(new Category("Điện thoại Iphone"));
        lstBtnHot.add(new Category("Điện thoại Samsung"));
        lstBtnHot.add(new Category("Điện thoại Oppo"));
        lstBtnHot.add(new Category("Điện thoại Vivo"));
        lstBtnHot.add(new Category("Điện thoại Xiaomi"));
        lstBtnHot.add(new Category("Phụ kiện"));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewBtnHot = (RecyclerView) root.findViewById(R.id.recyclerView_home_button);
        recyclerViewBtnHot.setLayoutManager(layoutManager2);
        RecyclerViewAdapterHomeCategory myAdapterBtnHot = new RecyclerViewAdapterHomeCategory(this, lstBtnHot);
        recyclerViewBtnHot.setAdapter(myAdapterBtnHot);
    }

    private void setLstBtnSearch() {
        lstBtnSearch = new ArrayList<Category>();
        lstBtnSearch.add(new Category("Sạc dự phòng"));
        lstBtnSearch.add(new Category("Iphone"));
        lstBtnSearch.add(new Category("Cục sạc"));
        lstBtnSearch.add(new Category("Tai nghe bluetooth"));
        lstBtnSearch.add(new Category("Ốp lưng"));
        lstBtnSearch.add(new Category("Giá đỡ điện thoại"));
        lstBtnSearch.add(new Category("Đồng hồ thông minh"));

        LinearLayoutManager layoutManagerSearch = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewBtnSearch = root.findViewById(R.id.recyclerView_home_search);
        recyclerViewBtnSearch.setLayoutManager(layoutManagerSearch);
        RecyclerViewAdapterHomeSearch myAdapterBtnSearch = new RecyclerViewAdapterHomeSearch(this, lstBtnSearch);
        recyclerViewBtnSearch.setAdapter(myAdapterBtnSearch);
    }

    private void loadData() {
        rd = new Random();
        LinearLayoutManager layoutManagerHot = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHot = root.findViewById(R.id.recyclerView_home_hot);
        recyclerViewHot.setLayoutManager(layoutManagerHot);

        databaseReference = FirebaseDatabase.getInstance().getReference("product");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstHot = new ArrayList<>();
                loadingViewHot.setVisibility(GONE);
                List<Product> full = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(dataSnapshot1.getKey());
                    full.add(p);
                }
                Collections.shuffle(full);

                for (int i = 0; i < 5 && i < full.size(); i++)
                    lstHot.add(full.get(i));

                RecyclerViewAdapter myAdapterHot = new RecyclerViewAdapter(getContext(), lstHot);
                recyclerViewHot.setAdapter(myAdapterHot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerIphone = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewIphone = (RecyclerView) root.findViewById(R.id.recyclerView_home_iphone);
        recyclerViewIphone.setLayoutManager(layoutManagerIphone);

        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Iphone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstIphone = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(dataSnapshot1.getKey());
                    lstIphone.add(p);
                }
                if (lstIphone.size() < LIMIT_PRODUCT + 1) {
                    recyclerViewIphone.setAdapter(new RecyclerViewAdapter(getContext(), lstIphone));
                }
                else {
                    Collections.shuffle(lstIphone);
                    recyclerViewIphone.setAdapter(new RecyclerViewAdapter(getContext(), lstIphone.subList(0, LIMIT_PRODUCT)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerSamsung = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewSamsung = (RecyclerView) root.findViewById(R.id.recyclerView_home_samsung);
        recyclerViewSamsung.setLayoutManager(layoutManagerSamsung);

        FirebaseDatabase.getInstance().getReference().child("product").orderByChild("category").equalTo("Điện thoại Samsung").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstSamsung = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(dataSnapshot1.getKey());
                    lstSamsung.add(p);
                }

                if (lstSamsung.size() < LIMIT_PRODUCT + 1) {
                    recyclerViewSamsung.setAdapter(new RecyclerViewAdapter(getContext(), lstSamsung));
                }
                else {
                    Collections.shuffle(lstSamsung);
                    recyclerViewSamsung.setAdapter(new RecyclerViewAdapter(getContext(), lstSamsung.subList(0, LIMIT_PRODUCT)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerOppo = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewOppo = (RecyclerView) root.findViewById(R.id.recyclerView_home_oppo);
        recyclerViewOppo.setLayoutManager(layoutManagerOppo);

        FirebaseDatabase.getInstance().getReference().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstOppo = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(dataSnapshot1.getKey());
                    if (p.getCategory().equals("Điện thoại Oppo"))
                        lstOppo.add(p);
                }

                if (lstOppo.size() < LIMIT_PRODUCT + 1) {
                    recyclerViewOppo.setAdapter(new RecyclerViewAdapter(getContext(), lstOppo));
                }
                else {
                    Collections.shuffle(lstOppo);
                    recyclerViewOppo.setAdapter(new RecyclerViewAdapter(getContext(), lstOppo.subList(0, LIMIT_PRODUCT)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerVivo = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewVivo = (RecyclerView) root.findViewById(R.id.recyclerView_home_vivo);
        recyclerViewVivo.setLayoutManager(layoutManagerVivo);

        FirebaseDatabase.getInstance().getReference().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstVivo = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(dataSnapshot1.getKey());
                    if (p.getCategory().equals("Điện thoại Vivo"))
                        lstVivo.add(p);
                }

                if (lstVivo.size() < LIMIT_PRODUCT + 1) {
                    recyclerViewVivo.setAdapter(new RecyclerViewAdapter(getContext(), lstVivo));
                }
                else {
                    Collections.shuffle(lstVivo);
                    recyclerViewVivo.setAdapter(new RecyclerViewAdapter(getContext(), lstVivo.subList(0, LIMIT_PRODUCT)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerXiaomi = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewXiaomi = (RecyclerView) root.findViewById(R.id.recyclerView_home_xiaomi);
        recyclerViewXiaomi.setLayoutManager(layoutManagerXiaomi);

        FirebaseDatabase.getInstance().getReference().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstXiaomi = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product p = dataSnapshot1.getValue(Product.class);

                    if (p.getActive().equals("0"))
                        continue;

                    p.setId(dataSnapshot1.getKey());
                    if (p.getCategory().equals("Điện thoại Xiaomi"))
                        lstXiaomi.add(p);
                }

                if (lstXiaomi.size() < LIMIT_PRODUCT + 1) {
                    recyclerViewXiaomi.setAdapter(new RecyclerViewAdapter(getContext(), lstXiaomi));
                }
                else {
                    Collections.shuffle(lstXiaomi);
                    recyclerViewXiaomi.setAdapter(new RecyclerViewAdapter(getContext(), lstXiaomi.subList(0, LIMIT_PRODUCT)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManagerFavourite = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerViewFavourite = (RecyclerView) root.findViewById(R.id.recyclerView_home_seen);
        recyclerViewFavourite.setLayoutManager(layoutManagerFavourite);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("favourite").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadingView.setVisibility(GONE);
                lstFavourite = new ArrayList<Product>();
                if (dataSnapshot.exists())
                    layoutFavorite.setVisibility(GONE);

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (lstFavourite.size() >= LIMIT_PRODUCT)
                        break;
                    FirebaseDatabase.getInstance().getReference().child("product").child(dataSnapshot1.getValue().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (lstFavourite.size() >= LIMIT_PRODUCT)
                                return;
                            Product p = snapshot.getValue(Product.class);

                            if (p == null) {
                                dataSnapshot1.getRef().removeValue();
                                return;
                            }

                            if (p.getActive().equals("0"))
                                return;

                            p.setId(dataSnapshot1.getValue().toString());
                            lstFavourite.add(p);
                            myAdapterFavourite.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                myAdapterFavourite = new RecyclerViewAdapter(getContext(), lstFavourite);
                recyclerViewFavourite.setAdapter(myAdapterFavourite);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_iphone:
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", "Điện thoại Iphone");
                startActivity(intent);
                break;
            case R.id.btn_samsung:
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", "Điện thoại Samsung");
                startActivity(intent);
                break;
            case R.id.btn_oppo:
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", "Điện thoại Oppo");
                startActivity(intent);
                break;
            case R.id.btn_vivo:
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", "Điện thoại Vivo");
                startActivity(intent);
                break;
            case R.id.btn_xiaomi:
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("Category", "Điện thoại Xiaomi");
                startActivity(intent);
                break;
            case R.id.home_seen_details:
                intent = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.home_hot_details:
                intent = new Intent(getActivity(), HotActivity.class);
                startActivity(intent);
                break;
            case R.id.home_category_details:
            case R.id.home_btn_category:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("Selection", "List");
                startActivity(intent);
                break;
            case R.id.home_btn_favorite:
                intent = new Intent(getActivity(), FavoriteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
