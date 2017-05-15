package com.gw.ui.library.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gw.ui.library.R;
import com.gw.ui.library.view.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawableActivity extends AppCompatActivity {

    @BindView(R.id.roundImageView)
    RoundImageView roundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        ButterKnife.bind(this);
    }
}
