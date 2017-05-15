package com.gw.ui.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gw.ui.library.util.ActivityUtil;
import com.gw.ui.library.view.TabIndicatorView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabIndicatorView tabIndicatorView = (TabIndicatorView) findViewById(R.id.tabIndicatorView);
        tabIndicatorView.addOnTabSelectedListener(new TabIndicatorView.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Toast.makeText(MainActivity.this, "onTabSelected " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(int position) {
                Toast.makeText(MainActivity.this, "onTabReselected " + position, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.swipeBack).setOnClickListener(this);
        findViewById(R.id.mTransition).setOnClickListener(this);
        findViewById(R.id.mDrawable).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipeBack:
                ActivityUtil.goSwipeBackLayout(this);
                break;
            case R.id.mTransition:
                ActivityUtil.goSceneActivity(this);
                break;
            case R.id.mDrawable:
                ActivityUtil.goDrawableActivity(this);
                break;
        }
    }
}
