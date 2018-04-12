package com.jianghua.headerandviewpager.activity;

import android.support.v7.app.AppCompatActivity;

import com.jianghua.headerandviewpager.listener.TransScrollFromAToFListener;


/**
 * Created by App-Dev on 2018/4/12.
 * fragment中包含HeaderAndViewPager的外围activity的基类，需要通过fragment设置listener监听
 */

public class TestTwoSuperActivity extends AppCompatActivity {

    protected TransScrollFromAToFListener listener;

    public void setListener(TransScrollFromAToFListener listener) {
        this.listener = listener;
    }

}
