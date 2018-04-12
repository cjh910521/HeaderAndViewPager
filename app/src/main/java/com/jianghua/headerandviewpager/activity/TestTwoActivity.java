package com.jianghua.headerandviewpager.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.AbsListView;

import com.jianghua.headerandviewpager.R;
import com.jianghua.headerandviewpager.fragment.TestTwoFragment;
import com.jianghua.headerandviewpager.listener.ScrollTabListener;

public class TestTwoActivity extends TestTwoSuperActivity implements ScrollTabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, TestTwoFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {

    }

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (listener != null) {
            //在activity中将listView的滑动事件传递到fragment中
            listener.onScrollChanged(view, pagePosition);
        }
    }

}
