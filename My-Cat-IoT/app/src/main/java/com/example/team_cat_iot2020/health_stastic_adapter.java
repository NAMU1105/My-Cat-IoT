package com.example.team_cat_iot2020;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class health_stastic_adapter  extends FragmentPagerAdapter {
    String TAG = getClass().getSimpleName();

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public health_stastic_adapter(FragmentManager fm) {
        super(fm);
        Log.e(TAG, "book_pager_adapter");
    }

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        Log.e(TAG, "FragmentList.add(fragment);");

        mFragmentTitleList.add(title);
        Log.e(TAG, "mFragmentTitleList.add(title);");

    }
    public CharSequence getPageTitle(int position) {
        Log.e(TAG, "getPageTitle");
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(TAG, "getItem");
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        Log.e(TAG, "getCount");
        return mFragmentList.size() ;
    }


}