package com.gw.ui.library.util;

import android.app.Activity;
import android.content.Intent;

import com.gw.ui.library.activity.DrawableActivity;
import com.gw.ui.library.activity.SceneActivity;
import com.gw.ui.library.activity.SwipeBackActivity;

/**
 * Created by GongWen on 17/3/3.
 */

public class ActivityUtil {
    public static void goSwipeBackLayout(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, SwipeBackActivity.class));
    }

    public static void goSceneActivity(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, SceneActivity.class));
    }

    public static void goDrawableActivity(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, DrawableActivity.class));
    }
}
