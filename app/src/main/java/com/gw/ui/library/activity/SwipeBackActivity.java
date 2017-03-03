package com.gw.ui.library.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gw.ui.library.R;
import com.gw.ui.library.view.SwipeBackLayout;

/**
 * Created by GongWen on 17/3/3.
 */

public class SwipeBackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.attachToActivity(this);
        setContentView(R.layout.swipe_back_activity);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

}
