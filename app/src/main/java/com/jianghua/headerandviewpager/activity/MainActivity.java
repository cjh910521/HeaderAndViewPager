package com.jianghua.headerandviewpager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jianghua.headerandviewpager.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.create_in_activity).setOnClickListener(this);
        findViewById(R.id.create_in_fragment).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_in_activity:
                startActivity(new Intent(this, TestOneActivity.class));
                break;
            case R.id.create_in_fragment:
                startActivity(new Intent(this, TestTwoActivity.class));
                break;
        }
    }
}
