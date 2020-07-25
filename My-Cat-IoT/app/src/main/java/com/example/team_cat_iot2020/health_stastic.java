package com.example.team_cat_iot2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class health_stastic extends AppCompatActivity {

    private ViewPager hs_view_pager;
    health_stastic_adapter health_stastic_adapter = new health_stastic_adapter(getSupportFragmentManager());
    String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_stastic);

        hs_view_pager = findViewById(R.id.hs_view_pager);
        final TabLayout hs_tab_layout = findViewById(R.id.hs_tab_layout);

        setupViewPager(hs_view_pager);
        Log.e(TAG, "setupViewPager");

        hs_tab_layout.setupWithViewPager(hs_view_pager);
        Log.e(TAG, "테이블 레이아웃 setupWithViewPager");


    }


    private void setupViewPager(ViewPager hs_view_pager) {
        Log.e(TAG, "setupViewPager");

        health_stastic_adapter.addFragment(new health_stastic_visit(), "방문");
        Log.e(TAG, "addFragment1");
        health_stastic_adapter.addFragment(new health_stastic_stay(), "체류");
        hs_view_pager.setAdapter(health_stastic_adapter);
        Log.e(TAG, "addFragment2");


    }


}
