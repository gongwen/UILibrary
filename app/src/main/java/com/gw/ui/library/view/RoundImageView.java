package com.gw.ui.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;

import com.gw.ui.library.R;
import com.gw.ui.library.util.BitmapUtil;

/**
 * Created by GongWen on 17/5/15.
 * http://likfe.com/2016/08/25/use-supportv4-RoundedBitmapDrawable/
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    private RoundedBitmapDrawable mRoundedBitmapDrawable;
    private boolean circular;
    private float cornerRadius;

    public RoundImageView(Context context) {
        this(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);

        circular = a.getBoolean(R.styleable.RoundImageView_circular, circular);
        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_cornerRadius, (int) cornerRadius);

        a.recycle();

        if (getDrawable() != null) {
            setImageDrawable(getDrawable());
        }
    }


    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        Bitmap mBitmap = BitmapUtil.drawable2Bitmap(drawable);
        mRoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), mBitmap);
        if (circular) {
            mRoundedBitmapDrawable.setCircular(circular);
        } else {
            mRoundedBitmapDrawable.setCornerRadius(cornerRadius);
        }
        super.setImageDrawable(mRoundedBitmapDrawable);

    }

    public void setCircular(boolean circular) {
        this.circular = circular;
        if (mRoundedBitmapDrawable != null) {
            mRoundedBitmapDrawable.setCircular(circular);
        }
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        if (mRoundedBitmapDrawable != null) {
            mRoundedBitmapDrawable.setCornerRadius(cornerRadius);
        }
    }

    public RoundedBitmapDrawable getRoundedBitmapDrawable() {
        return mRoundedBitmapDrawable;
    }

    public boolean isCircular() {
        return circular;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }
}
