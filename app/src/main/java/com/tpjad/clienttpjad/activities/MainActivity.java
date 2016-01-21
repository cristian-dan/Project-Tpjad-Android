package com.tpjad.clienttpjad.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.tpjad.clienttpjad.R;
import com.tpjad.clienttpjad.fragments.ProductTab;
import com.tpjad.clienttpjad.fragments.ViewPagerAdapter;
import com.tpjad.clienttpjad.ui.SlidingTabLayout;
import com.tpjad.clienttpjad.utils.LoginHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static CharSequence mTitles[] = {"Category", "Product"};
    private final static int mNumberOfTabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        final ViewPagerAdapter mViewPagerAdapter=  new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mNumberOfTabs);
        final ViewPager mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragments) {
                    if (fragment.getClass().toString().equals("class com.tpjad.clienttpjad.fragments.ProductTab")) {
                        ProductTab productTab = (ProductTab)fragment;
                        productTab.refreshCategoriesSpinner();
                        productTab.refreshProductsList();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SlidingTabLayout mTabs = (SlidingTabLayout)findViewById(R.id.tabs);
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
}
