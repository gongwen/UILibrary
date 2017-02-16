package com.gw.ui.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.gw.ui.library.R;

import java.util.ArrayList;

/**
 * Created by GongWen on 17/2/16.
 */

public class TabIndicatorView extends View {
    private final ArrayList<OnTabSelectedListener> mSelectedListeners = new ArrayList<>();

    private final int DEFAULT_SELECTED_COLOR = 0xff089FE6;
    private final int DEFAULT_NORMAL_COLOR = 0xffffffff;
    private final int DEFAULT_CORNER_RADIUS = 0;

    private int selectedColor;//选中 背景颜色
    private int normalColor;//未选中 背景颜色

    private float cornerRadius; //圆角半径
    private float borderWidth = 4;//边框的粗细
    private float separatorWidth = 2;//分割线的粗细

    private String[] mTitles;//标题数组，通过'|'分隔开
    private float mTextSize = 15;

    private Paint mPaint;
    private TextPaint mTextPaint;
    private Paint.FontMetrics fm;

    private float itemWidth;
    private int itemCount;
    private int currentPosition = 0;

    public TabIndicatorView(Context context) {
        this(context, null, 0);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabIndicatorView);

        selectedColor = a.getColor(R.styleable.TabIndicatorView_selectedColor, DEFAULT_SELECTED_COLOR);
        normalColor = a.getColor(R.styleable.TabIndicatorView_normalColor, DEFAULT_NORMAL_COLOR);

        cornerRadius = a.getDimensionPixelSize(R.styleable.TabIndicatorView_cornerRadius, DEFAULT_CORNER_RADIUS);
        borderWidth = a.getDimension(R.styleable.TabIndicatorView_boundWidth, borderWidth);
        separatorWidth = a.getDimension(R.styleable.TabIndicatorView_separatorWidth, separatorWidth);

        mTextSize = a.getDimensionPixelSize(R.styleable.TabIndicatorView_android_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, context.getResources().getDisplayMetrics()));

        String titleArray = a.getString(R.styleable.TabIndicatorView_titles);
        if (titleArray != null) {
            mTitles = titleArray.split("\\|");
        }
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(borderWidth);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
        fm = mTextPaint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(normalColor);
        if (mTitles == null || mTitles.length == 0) {
            if (cornerRadius <= 0) {
                drawEmptyBorderRect(canvas);
            } else {
                drawEmptyBorderRoundRect(canvas);
            }
            return;
        }
        itemCount = mTitles.length;
        itemWidth = getWidth() * 1.0f / itemCount;

        if (cornerRadius <= 0) {
            drawRect(canvas);
        } else {
            drawRoundRect(canvas);
        }

        fm = mTextPaint.getFontMetrics();
        for (int i = 0; i < itemCount; i++) {
            if (i == currentPosition) {
                mTextPaint.setColor(normalColor);
            } else {
                mTextPaint.setColor(selectedColor);
            }
            canvas.drawText(mTitles[i], itemWidth * (i + 0.5f), getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2, mTextPaint);
        }
    }

    private void drawEmptyBorderRect(Canvas canvas) {
        mPaint.setColor(selectedColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        canvas.drawRect(new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2), mPaint);
    }

    private void drawRect(Canvas canvas) {
        drawEmptyBorderRect(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(currentPosition * itemWidth, 0, (currentPosition + 1) * itemWidth, getHeight(), mPaint);

        for (int i = 1; i < itemCount; i++) {
            canvas.drawRect(i * itemWidth - separatorWidth / 2, 0, i * itemWidth + separatorWidth / 2, getHeight(), mPaint);
        }
    }

    private void drawEmptyBorderRoundRect(Canvas canvas) {
        mPaint.setColor(selectedColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        canvas.drawRoundRect(new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2), cornerRadius, cornerRadius, mPaint);

    }

    private void drawRoundRect(Canvas canvas) {
        if (itemCount != 1) {
            drawEmptyBorderRoundRect(canvas);
            mPaint.setStyle(Paint.Style.FILL);

            float[] leftOuterRadii = new float[]{cornerRadius, cornerRadius, 0, 0, 0, 0, cornerRadius, cornerRadius};
            float[] rightOuterRadii = new float[]{0, 0, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0};
            if (currentPosition == 0) {
                ShapeDrawable mDrawables = new ShapeDrawable(new RoundRectShape(leftOuterRadii, null, null));
                mDrawables.getPaint().setColor(mPaint.getColor());
                mDrawables.setBounds((int) (currentPosition * itemWidth), 0, (int) ((currentPosition + 1) * itemWidth), getHeight());
                mDrawables.draw(canvas);
            } else if (currentPosition == (itemCount - 1)) {
                ShapeDrawable mDrawables = new ShapeDrawable(new RoundRectShape(rightOuterRadii, null, null));
                mDrawables.getPaint().setColor(mPaint.getColor());
                mDrawables.setBounds((int) (currentPosition * itemWidth), 0, (int) ((currentPosition + 1) * itemWidth), getHeight());
                mDrawables.draw(canvas);
            } else {
                canvas.drawRect(currentPosition * itemWidth, 0, (currentPosition + 1) * itemWidth, getHeight(), mPaint);
            }

        } else {
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), cornerRadius, cornerRadius, mPaint);
        }

        for (int i = 1; i < itemCount; i++) {
            canvas.drawRect(i * itemWidth - separatorWidth / 2, 0, i * itemWidth + separatorWidth / 2, getHeight(), mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selectPosition((int) (event.getX() / itemWidth));
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    //点击事件监听
    public void selectPosition(int selectedPosition) {

        if (currentPosition == selectedPosition) {
            dispatchTabReselected(selectedPosition);
        } else {
            dispatchTabSelected(selectedPosition);
        }
        this.currentPosition = selectedPosition;
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener listener) {
        if (!mSelectedListeners.contains(listener)) {
            mSelectedListeners.add(listener);
        }
    }

    private void dispatchTabSelected(@NonNull final int selectedPosition) {
        for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
            mSelectedListeners.get(i).onTabSelected(selectedPosition);
        }
    }

    private void dispatchTabReselected(@NonNull final int selectedPosition) {
        for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
            mSelectedListeners.get(i).onTabReselected(selectedPosition);
        }
    }

    public interface OnTabSelectedListener {

        /**
         * @param position The tab that was selected
         */
        void onTabSelected(int position);

        /**
         * @param position The position that was reselected.
         */
        void onTabReselected(int position);
    }

    //属性设置
    public void setTitles(String[] titles) {
        this.mTitles = titles;
        postInvalidate();
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        mTextPaint.setTextSize(mTextSize);
        fm = mTextPaint.getFontMetrics();
        postInvalidate();
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        postInvalidate();
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        postInvalidate();
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        postInvalidate();
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        postInvalidate();
    }

    public void setSeparatorWidth(float separatorWidth) {
        this.separatorWidth = separatorWidth;
        postInvalidate();
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        postInvalidate();
    }
}
