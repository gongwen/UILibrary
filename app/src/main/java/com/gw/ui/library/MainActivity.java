package com.gw.ui.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gw.ui.library.view.TabIndicatorView;

public class MainActivity extends AppCompatActivity {

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
    }
}
