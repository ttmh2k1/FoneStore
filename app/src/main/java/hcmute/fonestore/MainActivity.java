package hcmute.fonestore;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hcmute.fonestore.fragment.category.listFragment;
import hcmute.fonestore.fragment.HomeFragment;
import hcmute.fonestore.fragment.notification.NotificationFragment;
import hcmute.fonestore.fragment.user.UserFragment;

public class MainActivity extends AppCompatActivity {
    private boolean doubleClick = false;
    BottomNavigationView bottomNav;
    int newPosition, startingPosition;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragment = null;
                newPosition = 0;
                bottomNav.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.ic_home);
                bottomNav.getMenu().findItem(R.id.nav_category).setIcon(R.drawable.ic_category);
                bottomNav.getMenu().findItem(R.id.nav_utilities).setIcon(R.drawable.ic_utilities);
                bottomNav.getMenu().findItem(R.id.nav_notification).setIcon(R.drawable.ic_notification);
                bottomNav.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.ic_profile);

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        bottomNav.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.ic_home_fill);
                        fragment = new HomeFragment();
                        newPosition = 1;
                        break;

                    case R.id.nav_category:
                        bottomNav.getMenu().findItem(R.id.nav_category).setIcon(R.drawable.ic_category_fill);
                        fragment = new listFragment();
                        newPosition = 2;
                        break;

                    case R.id.nav_utilities:
                        bottomNav.getMenu().findItem(R.id.nav_utilities).setIcon(R.drawable.ic_utilities_fill);
                        fragment = new UtilitiesFragment();
                        newPosition = 3;
                        break;

                    case R.id.nav_notification:
                        bottomNav.getMenu().findItem(R.id.nav_notification).setIcon(R.drawable.ic_notifications_fill);
                        fragment = new NotificationFragment();
                        newPosition = 4;
                        break;

                    case R.id.nav_profile:
                        bottomNav.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.ic_profile_fill);
                        fragment = new UserFragment();
                        newPosition = 5;
                        break;
                }
                return loadFragment(fragment, newPosition);
            }
        });

        Bundle getSelection = getIntent().getExtras();
        if (getSelection == null) {
            loadFragment(new HomeFragment(), 1);
        } else {
            loadFragment(new listFragment(), 2);
            bottomNav.setSelectedItemId(R.id.nav_category);
        }
    }

    private boolean loadFragment(Fragment fragment, int newPosition) {
        if (fragment != null) {
            if (startingPosition > newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
            if (startingPosition < newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
            startingPosition = newPosition;
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleClick)
            finish();
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        doubleClick = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClick = false;
            }
        }, 2000);
    }
}