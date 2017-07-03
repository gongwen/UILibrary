package com.gw.ui.library.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoopControl {
    private static final String TAG = "LoopControl";
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final WeakHashMap<View, LoopHolder> map = new WeakHashMap<>();
    private static final LoopControl instance = new LoopControl();

    private LoopControl() {
    }

    public static LoopControl getInstance() {
        return instance;
    }

    public void start(View mView, long delayMillis) {
        LoopHolder mLoopHolder = map.get(mView);
        if (mLoopHolder != null) {
            mLoopHolder.start(delayMillis);
        }
    }

    public void registerFunction(View mView, long internal, boolean isSafelyStop, boolean isWaitCompleted, long timeOut, Runnable mRunnable) {
        if (mView != null) {
            map.put(mView, new LoopHolder(mView, internal, isSafelyStop, isWaitCompleted, timeOut, mRunnable));
            mView.addOnAttachStateChangeListener(new OnAttachStateChangeListenerAdapter() {
                @Override
                public void onViewDetachedFromWindow(View v) {
                    unRegisterFunction(v);
                }
            });
        }
    }

    private void unRegisterFunction(View mView) {
        LoopHolder mLoopHolder = map.get(mView);
        if (mLoopHolder != null) {
            mLoopHolder.stop();
            map.remove(mView);
        }
    }

    private static class LoopHolder {
        private final int MSG = 0;
        private Handler mHandler;
        private HandlerThread mainHandlerThread;

        public View mView;
        public long internal;
        public boolean isSafelyStop;
        public boolean isWaitCompleted;
        public long timeOut;
        public Runnable mRunnable;

        public LoopHolder(View mView, long internal, boolean isSafelyStop, boolean isWaitCompleted, long timeOut, Runnable mRunnable) {
            this.mView = mView;
            this.internal = internal;
            this.isSafelyStop = isSafelyStop;
            this.isWaitCompleted = isWaitCompleted;
            this.timeOut = timeOut;
            this.mRunnable = mRunnable;
            init();
        }

        private void init() {
            mainHandlerThread = new HandlerThread("mainHandlerThread" + Integer.toHexString(System.identityHashCode(mView)));
            mainHandlerThread.start();
            mHandler = new Handler(mainHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (mView.isShown() && ScreenUtil.isScreenOn()) {
                        if (isWaitCompleted) {
                            if (timeOut == Long.MAX_VALUE) {
                                mRunnable.run();
                            } else {
                                Future future = threadPool.submit(new Callable() {
                                    @Override
                                    public Object call() throws Exception {
                                        mRunnable.run();
                                        return null;
                                    }
                                });
                                try {
                                    future.get(timeOut, TimeUnit.MILLISECONDS);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "Task TimeoutException");
                                }
                            }
                        } else {
                            threadPool.execute(mRunnable);
                        }
                    }
                    sendEmptyMessageDelayed(MSG, internal);
                }
            };
        }

        public void start(long delayMillis) {
            mHandler.sendEmptyMessageDelayed(MSG, delayMillis);
        }

        public boolean stop() {
            if (isSafelyStop) return mainHandlerThread.quitSafely();
            else return mainHandlerThread.quit();
        }
    }
}