package com.jianghua.headerandviewpager.listener;

import android.widget.AbsListView;

/**
 * Created by jianghua on 2018/4/12.
 * 将activity中监听到的listView滑动事件传递到fragment中。
 * 用在HeaderAndViewPager外围容器是fragment的情况
 */

public interface TransScrollFromAToFListener {

    void onScrollChanged(AbsListView view, int position);
}
