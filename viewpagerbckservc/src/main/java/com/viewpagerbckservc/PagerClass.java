package com.viewpagerbckservc;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohanraj.S on 05/01/17.
 */

public class PagerClass extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager parent_viewpager;
    ViewPagerAdapter pageAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        parent_viewpager = (ViewPager) findViewById(R.id.parent_viewpager);
        setupViewPager(parent_viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(parent_viewpager);
        tabLayout.setFillViewport(true);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        parent_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(onTabSelectedListener(parent_viewpager));
        setupTabIcons();
    }


    /*Configuration of ViewPager is Begin*/
    private void setupTabIcons() {
        int[] tabIcons = {R.drawable.ic_cloud,R.drawable.ic_date};

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#1FFF3B"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    private void setupViewPager(ViewPager viewPager) {
        pageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pageAdapter.addFrag(new DownLoadFragment(),"Download");
        pageAdapter.addFrag(new DateTimeFragment(),"Date");
        parent_viewpager.setAdapter(pageAdapter);

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);

        }

        public void addFrag(Fragment fragment,String title) {
            mFragmentList.add(fragment);
            //mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon 8644960886
            return null;
        }
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager mPager) {

        return new TabLayout.OnTabSelectedListener() {

            int currentPosition = 0;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#1FFF3B"), PorterDuff.Mode.SRC_IN);
                /*FragmentLifecycle fragmentToHide = (FragmentLifecycle)pageAdapter.getItem(currentPosition);
                fragmentToHide.onPauseFragment();

                FragmentLifecycle fragmentToShow = (FragmentLifecycle)pageAdapter.getItem(tab.getPosition());
                fragmentToShow.onResumeFragment();*/

                currentPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }
}
