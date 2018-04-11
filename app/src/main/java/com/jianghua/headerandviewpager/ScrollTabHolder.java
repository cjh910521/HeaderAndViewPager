package com.jianghua.headerandviewpager;

import android.widget.AbsListView;

/**
 * Created by desmond on 10/4/15.
 */
public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight, int headerHeight);

    void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                          int totalItemCount, int pagePosition);

}
