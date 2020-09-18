package com.workspace.nusali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.workspace.nusali.Adapter.PagerOrderAdapter;
import com.workspace.nusali.Fragment.FragmentAccount;
import com.workspace.nusali.Fragment.FragmentChart;
import com.workspace.nusali.Fragment.FragmentHome;
import com.workspace.nusali.Fragment.FragmentOrder;
import com.workspace.nusali.Fragment.FragmentPayment;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    FirebaseAuth mAuth;
    String USER = "";
    FrameLayout frameLayout;
    TabLayout tab;
    LinearLayout frameTab;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  getUserID();
        frameLayout = findViewById(R.id.container);
        tab = findViewById(R.id.tab);
        frameTab = findViewById(R.id.frame_tab);
        viewPager = findViewById(R.id.viewPager);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navigationListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentHome()).commit();
        
        PagerOrderAdapter pagerOrderAdapter = new PagerOrderAdapter(getSupportFragmentManager(), tab.getTabCount());

        viewPager.setAdapter(pagerOrderAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        //<-- SETTING FRAGMENT LIBRARY -->


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment setFragment = null;
            switch (item.getItemId()) {
                case R.id.navHome:
                    setFragment = new FragmentHome();
                    frameTab.setVisibility(View.GONE);
                    break;
                case R.id.navOrder:
                    setFragment = new FragmentOrder();
                    frameTab.setVisibility(View.VISIBLE);
                    break;
                case R.id.navChart:
                    setFragment = new FragmentChart();
                    frameTab.setVisibility(View.GONE);
                    break;
                case R.id.navPayment:
                    setFragment = new FragmentPayment();
                    frameTab.setVisibility(View.GONE);
                    break;
                case R.id.navAccount:
                    setFragment = new FragmentAccount();
                    frameTab.setVisibility(View.GONE);
                    break;
            }
            assert setFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.container, setFragment).commit();
            return true;

        }
    };

    public void getUserID(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        USER = firebaseUser.getUid();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            moveTaskToBack(true);
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}