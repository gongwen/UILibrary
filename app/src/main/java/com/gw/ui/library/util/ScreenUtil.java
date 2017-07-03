package com.gw.ui.library.util;

import android.content.Context;
import android.os.PowerManager;

import com.gw.ui.library.MainApplication;

/**
 * Created by GongWen on 17/7/3.
 */

public class ScreenUtil {
    public static boolean isScreenOn() {
        return ((PowerManager) MainApplication.getInstance().getSystemService(Context.POWER_SERVICE)).isScreenOn();
    }
}
