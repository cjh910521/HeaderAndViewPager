package com.jianghua.headerandviewpager.activity;

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

import com.jianghua.headerandviewpager.R;
import com.jianghua.headerandviewpager.adapter.SlidingHolderAdapter;
import com.jianghua.headerandviewpager.fragment.ListViewFragment;
import com.jianghua.headerandviewpager.listener.ScrollTabListener;
import com.jianghua.headerandviewpager.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 在activity中
 */

public class TestOneActivity extends AppCompatActivity implements ScrollTabListener {

    private ViewPager mViewPager;
    private View mHeader;
    private ImageView mTopImage;
    private SlidingTabLayout mNavigBar;
    private SlidingHolderAdapter mAdapter;

    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private int mNumFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_one);
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

        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < mNumFragments; i++) {
            mFragments.add(ListViewFragment.newInstance(i));
        }
        mAdapter = new SlidingHolderAdapter(getSupportFragmentManager(), mFragments, title);

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
                    SparseArrayCompat<ScrollTabListener> scrollTabHolders = mAdapter.getScrollTabHolders();

                    ScrollTabListener fragmentContent;
                    if (position < currentItem) {
                        // Revealed the previous page
                        fragmentContent = scrollTabHolders.valueAt(position);
                    } else {
                        // Revealed the next page
                        fragmentContent = scrollTabHolders.valueAt(position + 1);
                    }

                    fragmentContent.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()),
                            mHeader.getHeight());
                }
            }

            @Override
            public void onPageSelected(int position) {
                SparseArrayCompat<ScrollTabListener> scrollTabHolders = mAdapter.getScrollTabHolders();

                if (scrollTabHolders == null || scrollTabHolders.size() != mNumFragments) {
                    return;
                }

                ScrollTabListener currentHolder = scrollTabHolders.valueAt(position);
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
}
