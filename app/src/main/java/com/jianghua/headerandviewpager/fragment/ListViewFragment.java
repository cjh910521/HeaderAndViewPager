package com.jianghua.headerandviewpager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jianghua.headerandviewpager.R;

/**
 * A simple {@link Fragment} subclass.
 * 将其中的listView滑动事件，通过mScrollTabHolder传递给它外面的activity
 */
public class ListViewFragment extends ScrollTabHolderFragment {

    public static final String TAG = ListViewFragment.class.getSimpleName();
    private static final String ARG_POSITION = "position";

    private ListView mListView;
    private int mPosition;

    public static Fragment newInstance(int position) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public ListViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);

        View placeHolderView = inflater.inflate(R.layout.header_placeholder, mListView, false);
        mListView.addHeaderView(placeHolderView);

        mPosition = getArguments().getInt(ARG_POSITION);

        setAdapter();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onListViewScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
                }
            }
        });

        return view;
    }

    private void setAdapter() {
        if (getActivity() == null) return;

        int size = 17;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = "" + i;
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringArray);

        mListView.setAdapter(adapter);
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mListView == null) return;

        if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        //此处作用是左右切换时，让切换前后的listView自动滑动到相同的位置，避免跳动
        mListView.setSelectionFromTop(1, scrollHeight);
    }
}
