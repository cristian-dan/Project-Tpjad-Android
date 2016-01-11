package com.tpjad.clienttpjad.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence mTitles[];
    int mNumberOfTabs;

    public ViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int numberOfTabs) {
        super(fragmentManager);

        this.mTitles = titles;
        this.mNumberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CategoryTab();
        }

        return new ProductTab();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
