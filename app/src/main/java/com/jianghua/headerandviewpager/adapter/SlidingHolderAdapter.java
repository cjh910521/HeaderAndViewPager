package com.jianghua.headerandviewpager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.jianghua.headerandviewpager.listener.ScrollTabListener;

import java.util.List;

/**
 * Created by App-Dev on 2016/7/12.
 * 有头部的viewPager的adapter
 */
public class SlidingHolderAdapter extends SlidingPagerAdapter {
    private SparseArrayCompat<ScrollTabListener> mScrollTabHolders;

    public SlidingHolderAdapter(FragmentManager fm, List<? extends Fragment> list, List<String> title) {
        super(fm, list, title);
        mScrollTabHolders = new SparseArrayCompat<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        mScrollTabHolders.put(position, (ScrollTabListener) object);
        return object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public SparseArrayCompat<ScrollTabListener> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}