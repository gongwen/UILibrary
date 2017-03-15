package com.gw.ui.library.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.gw.ui.library.R;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;

/**
 * Created by GongWen on 17/3/10.
 */

public class SceneActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup sceneRoot;
    private Scene scene1;
    private Scene scene2;

    private boolean isScene2 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        initScene();
        findViewById(R.id.changeScene).setOnClickListener(this);
    }

    private void initScene() {
        sceneRoot = (ViewGroup) findViewById(R.id.sceneRoot);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_2, this);
        TransitionManager.go(scene1);
    }

    private void changeScene() {
        TransitionManager.go(isScene2 ? scene1 : scene2, new ChangeBounds());
        isScene2 = !isScene2;
        findViewById(R.id.changeScene).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        changeScene();
    }
}
