package com.gw.ui.library.util;

import android.app.Activity;
import android.content.Intent;

import com.gw.ui.library.activity.SwipeBackActivity;

/**
 * Created by GongWen on 17/3/3.
 */

public class ActivityUtil {
    public static void goSwipeBackLayout(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, SwipeBackActivity.class));
    }
}
