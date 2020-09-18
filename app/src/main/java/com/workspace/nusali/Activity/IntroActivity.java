package com.workspace.nusali.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.workspace.nusali.Adapter.PagerIntroAdapter;
import com.workspace.nusali.Model.ScreenItemModel;
import com.workspace.nusali.R;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    PagerIntroAdapter introAdapter;
    TabLayout tabLayout;
    Button btnNext, btnMulai;
    int position = 0;
    Animation btnAnim;
    TextView skip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(restorePrefData()){
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_intro);

//        getSupportActionBar().hide();

        btnNext = findViewById(R.id.btn_next);
        btnMulai = findViewById(R.id.btn_get_started);
        tabLayout = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        skip = findViewById(R.id.tv_skip);

        // fill list screen
        final List<ScreenItemModel> mList = new ArrayList<>();
        mList.add(new ScreenItemModel("Makanan Segar", "Dapatkan makanan segar, rasakan mudahnya mendapatkan makanan berkualitas dan segar", R.drawable.ricebox));
        mList.add(new ScreenItemModel("Pengantaran Xpress", "Fast Delivery, tidak usah repot memikirkan beli makan dimana makanan akan tiba", R.drawable.delivery1));
        mList.add(new ScreenItemModel("Pembayaran Mudah", "Easy Payment, rasakan mudahnya membayar dengan sekali klik dalam genggaman smartphone    ", R.drawable.img3));

        //setup view pager
        viewPager = findViewById(R.id.screen_viewpager);
        introAdapter = new PagerIntroAdapter(this, mList);
        viewPager.setAdapter(introAdapter);

        //setUp tab layout
        tabLayout.setupWithViewPager(viewPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < mList.size()){
                        position++;
                        viewPager.setCurrentItem(position);
                }
                if(position == mList.size()-1){
                    //when screen finishing
                    loadLastScreen();
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mList.size());
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    if(tab.getPosition() == mList.size()-1){
                        loadLastScreen();
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    startActivity(intent);
                    savePrefData();
                    finish();
            }
        });

    }

    private boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("openEnd", false);
    }

    private void  savePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("openEnd", true);
        editor.apply();
    }

    private void loadLastScreen(){
        btnNext.setVisibility(View.INVISIBLE);
        skip.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        btnMulai.setVisibility(View.VISIBLE);
        btnMulai.setAnimation(btnAnim);

    }
}