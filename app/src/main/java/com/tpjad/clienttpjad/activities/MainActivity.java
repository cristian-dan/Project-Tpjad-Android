package com.tpjad.clienttpjad.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.adapters.CategoryAdapter;
import com.tpjad.clienttpjad.fragments.ViewPagerAdapter;
import com.tpjad.clienttpjad.models.Category;
import com.tpjad.clienttpjad.ui.SlidingTabLayout;
import com.tpjad.clienttpjad.utils.LoginHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    SlidingTabLayout mTabs;
    CharSequence mTitles[] = {"Category", "Product"};
    int mNumberOfTabs = 2;
    ListView mListView;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.categoryList);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mViewPagerAdapter=  new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mNumberOfTabs);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabs = (SlidingTabLayout)findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);

        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(MainActivity.this, R.color.tabsColorScroll);
            }
        });

        mTabs.setViewPager(mViewPager);

        setUsernameText();
    }

    private void setUsernameText() {
        LoginHelper loginHelper = LoginHelper.getInstance();
        setTitle(loginHelper.getUsername());
    }



    private void displayLoadingStatus() {
        mProgressDialog = ProgressDialog.show(this, getString(R.string.loading_title), getString(R.string.loading_message), true);
    }

    private void hideLoadingStatus() {
        mProgressDialog.dismiss();
    }
}
