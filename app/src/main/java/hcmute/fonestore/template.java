//package hcmute.fonestore;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.util.List;
//
//public class template extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
//        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                navigationView.getMenu().findItem(R.id.home).setIcon(R.drawable.ic_home);
//                navigationView.getMenu().findItem(R.id.notification).setIcon(R.drawable.ic_notification);
//                navigationView.getMenu().findItem(R.id.category).setIcon(R.drawable.ic_category);
//                navigationView.getMenu().findItem(R.id.profile).setIcon(R.drawable.ic_profile);
//                navigationView.getMenu().findItem(R.id.setting).setIcon(R.drawable.ic_setting);
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        navigationView.getMenu().findItem(R.id.home).setIcon(R.drawable.ic_home_fill);
//                        break;
//
//                    case R.id.notification:
//                        navigationView.getMenu().findItem(R.id.notification).setIcon(R.drawable.ic_notifications_fill);
//                        break;
//
//                    case R.id.category:
//                        navigationView.getMenu().findItem(R.id.category).setIcon(R.drawable.ic_category_fill);
//                        break;
//
//                    case R.id.profile:
//                        navigationView.getMenu().findItem(R.id.profile).setIcon(R.drawable.ic_profile_fill);
//                        break;
//
//                    case R.id.setting:
//                        navigationView.getMenu().findItem(R.id.setting).setIcon(R.drawable.ic_setting_fill);
//                        break;
//                }
//                return true;
//            }
//        });
//    }
//}