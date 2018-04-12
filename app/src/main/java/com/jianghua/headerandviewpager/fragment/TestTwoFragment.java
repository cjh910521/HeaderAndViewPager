package com.jianghua.headerandviewpager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.jianghua.headerandviewpager.R;
import com.jianghua.headerandviewpager.activity.TestTwoSuperActivity;
import com.jianghua.headerandviewpager.adapter.SlidingHolderAdapter;
import com.jianghua.headerandviewpager.listener.ScrollTabListener;
import com.jianghua.headerandviewpager.listener.TransScrollFromAToFListener;
import com.jianghua.headerandviewpager.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 以fragment为容器
 */
public class TestTwoFragment extends Fragment {

    private ViewPager mViewPager;
    private View mHeader;
    private ImageView mTopImage;
    private SlidingTabLayout mNavigBar;
    private SlidingHolderAdapter mAdapter;

    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private int mNumFragments;

    public TestTwoFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        TestTwoFragment fragment = new TestTwoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_continer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initValues();

        mTopImage = (ImageView) view.findViewById(R.id.image);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mNavigBar = (SlidingTabLayout) view.findViewById(R.id.navig_tab);
        mHeader = view.findViewById(R.id.header);

        setupAdapter();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof TestTwoSuperActivity) {
                ((TestTwoSuperActivity) context).setListener(new TransScrollFromAToFListener() {
                    @Override
                    public void onScrollChanged(AbsListView view, int pagePosition) {
                        if (mViewPager.getCurrentItem() == pagePosition) {
                            scrollHeader(getScrollY(view));
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mAdapter = new SlidingHolderAdapter(getChildFragmentManager(), mFragments, title);

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

                    //左右滑动切换时，让左右的listView滑动到相同位置
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

                //点击tab切换时，让跳转前后的listView滑动到相同位置
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

    /**
     * 滑动头部
     */
    private void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
    }

    /**
     * 计算header需要滑动的距离
     */
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
