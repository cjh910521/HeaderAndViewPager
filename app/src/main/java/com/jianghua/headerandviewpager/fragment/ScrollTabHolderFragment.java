package com.jianghua.headerandviewpager.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.AbsListView;

import com.jianghua.headerandviewpager.listener.ScrollTabListener;

/**
 * Created by desmond on 12/4/15.
 *
 */
public class ScrollTabHolderFragment extends Fragment implements ScrollTabListener {

    protected ScrollTabListener mScrollTabHolder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mScrollTabHolder = (ScrollTabListener) context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ScrollTabHolder");
        }
    }

    @Override
    public void onDetach() {
        mScrollTabHolder = null;
        super.onDetach();
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {

    }

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {}

}
