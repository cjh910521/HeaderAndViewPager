package com.jianghua.headerandviewpager.listener;

import android.widget.AbsListView;

/**
 * Created by desmond on 10/4/15.
 * listView滑动自定义监听
 */
public interface ScrollTabListener {

    void adjustScroll(int scrollHeight, int headerHeight);

    void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                          int totalItemCount, int pagePosition);

}
