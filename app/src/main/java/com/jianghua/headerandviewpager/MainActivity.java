package com.jianghua.headerandviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ScrollTabHolder{

    private ViewPager mViewPager;
    private View mHeader;
    private ImageView mTopImage;
    private SlidingTabLayout mNavigBar;
    private ViewPagerAdapter mAdapter;

    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private int mNumFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValues();

        mTopImage = (ImageView) findViewById(R.id.image);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mNavigBar = (SlidingTabLayout) findViewById(R.id.navig_tab);
        mHeader = findViewById(R.id.header);

        setupAdapter();
    }

    private void initValues() {
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight;

        mNumFragments = 4;
    }

    private void setupAdapter() {
        List<String> title = new ArrayList<>();
        title.add("全部");
        title.add("游记");
        title.add("攻略");
        title.add("旅友");

        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mNumFragments, title);
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mNumFragments);
        mNavigBar.setOnPageChangeListener(getViewPagerPageChangeListener());
        mNavigBar.setViewPager(mViewPager);
    }

    private ViewPager.OnPageChangeListener getViewPagerPageChangeListener() {
        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewPager.getCurrentItem();
                if (positionOffsetPixels > 0) {
                    SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

                    ScrollTabHolder fragmentContent;
                    if (position < currentItem) {
                        // Revealed the previous page
                        fragmentContent = scrollTabHolders.valueAt(position);
                    } else {
                        // Revealed the next page
                        fragmentContent = scrollTabHolders.valueAt(position + 1);
                    }

//                    Log.d(TAG, "header height " + mHeader.getHeight() + " translationY " + mHeader.getTranslationY());
                    fragmentContent.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()),
                            mHeader.getHeight());
                }
            }

            @Override
            public void onPageSelected(int position) {
                SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

                if (scrollTabHolders == null || scrollTabHolders.size() != mNumFragments) {
                    return;
                }

                ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
                currentHolder.adjustScroll(
                        (int) (mHeader.getHeight() + mHeader.getTranslationY()),
                        mHeader.getHeight());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };

        return listener;
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
    }

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(getScrollY(view));
        }
    }

    private void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
//        mTopImage.setTranslationY(-translationY / 3);
    }

    private int getScrollY(AbsListView view) {
        View child = view.getChildAt(0);
        if (child == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = child.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * child.getHeight() + headerHeight;
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private int mNumFragments;
        private List<String> mTitles;

        public ViewPagerAdapter(FragmentManager fm, int numFragments, List<String> titles) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<>();
            mNumFragments = numFragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = ListViewFragment.newInstance(position);
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);

            mScrollTabHolders.put(position, (ScrollTabHolder) object);

            return object;
        }

        @Override
        public int getCount() {
            return mNumFragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }
    }

}
