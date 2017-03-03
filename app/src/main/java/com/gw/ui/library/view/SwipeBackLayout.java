package com.gw.ui.library.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SwipeBackLayout extends FrameLayout {
    private Scroller mScroller;
    private int mTouchSlop;

    private int downX;
    private int downY;
    private int lastX;
    private int lastY;

    private int viewWidth;
    private boolean isSliding;
    private boolean isFinish;
    private Activity mActivity;

    public SwipeBackLayout(Context context) {
        this(context, null, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);

        setWillNotDraw(false);
    }


    public void attachToActivity(Activity activity) {
        mActivity = activity;
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundColor(0x00000000);
        decor.removeView(decorChild);
        addView(decorChild);
        decor.addView(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = lastX = (int) ev.getRawX();
                downY = lastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                lastX = (int) ev.getRawX();
                lastY = (int) ev.getRawY();
                if (lastX - downX > mTouchSlop
                        && Math.abs(lastY - downY) < mTouchSlop) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                if (lastX - downX > mTouchSlop
                        && Math.abs(lastY - downY) < mTouchSlop) {
                    isSliding = true;
                }

                if (lastX - downX >= 0 && isSliding) {
                    scrollTo(downX - lastX, 0);
                } else {
                    scrollTo(0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isSliding = false;
                if (getScrollX() <= -viewWidth / 2) {
                    isFinish = true;
                    scrollRight();
                } else {
                    isFinish = false;
                    scrollLeft();
                }
                break;
        }

        return true;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            viewWidth = this.getWidth();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int alpha = (int) (200 * (1 - 1.0f * Math.abs(getScrollX()) / viewWidth));
        canvas.drawARGB(alpha, 0, 0, 0);
    }

    private void scrollRight() {
        final int delta = (viewWidth + getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    private void scrollLeft() {
        int delta = getScrollX();
        mScroller.startScroll(getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }


}