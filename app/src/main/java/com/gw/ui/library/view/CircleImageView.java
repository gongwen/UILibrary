package com.gw.ui.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;

import com.gw.ui.library.util.BitmapUtil;

/**
 * Created by GongWen on 17/5/15.
 * http://likfe.com/2016/08/25/use-supportv4-RoundedBitmapDrawable/
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

    public CircleImageView(Context context) {
        this(context, null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        Bitmap mBitmap = BitmapUtil.drawable2Bitmap(drawable);
        RoundedBitmapDrawable mRoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), mBitmap);
        mRoundedBitmapDrawable.setCircular(true);
        super.setImageDrawable(mRoundedBitmapDrawable);
    }
}
