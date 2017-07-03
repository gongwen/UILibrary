package com.gw.ui.library.kotlin

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.view.View
import com.gw.ui.library.util.OnAttachStateChangeListenerAdapter
import com.gw.ui.library.util.ScreenUtil
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by GongWen on 17/7/3.
 */
object LoopControl {

    val TAG = LoopControl::class.java.toString()

    private val threadPool = Executors.newCachedThreadPool()
    private val map = WeakHashMap<View, LoopHolder>()

    fun start(mView: View, delayMillis: Long = 0) {
        map[mView]?.start(delayMillis)
    }

    fun registerFunction(mView: View, internal: Long = 6000L, isSafelyStop: Boolean = false, isWaitCompleted: Boolean = true, timeOut: Long = Long.MAX_VALUE, function: () -> Unit) {
        mView?.let {
            map.put(mView, LoopHolder(mView, internal, isSafelyStop, isWaitCompleted, timeOut, function))
            mView.addOnAttachStateChangeListener(object : OnAttachStateChangeListenerAdapter() {
                override fun onViewDetachedFromWindow(v: View?) {
                    unRegisterFunction(mView)
                }
            })

        }
    }


    private fun unRegisterFunction(mView: View) {
        val mLoopHolder = map[mView]
        mLoopHolder?.let {
            mLoopHolder.stop()
            map.remove(mView)
        }
    }

    private open class LoopHolder constructor(val mView: View, val internal: Long, val isSafelyStop: Boolean = false, val isWaitCompleted: Boolean, val timeOut: Long, val function: () -> Unit) {
        private val MSG = 0
        private val mHandler: Handler
        private val mainHandlerThread: HandlerThread = HandlerThread("mainHandlerThread@${Integer.toHexString(System.identityHashCode(mView))}")

        init {
            mainHandlerThread.start()
            mHandler = object : Handler(mainHandlerThread.looper) {
                override fun handleMessage(msg: Message?) {
                    if (mView.isShown && ScreenUtil.isScreenOn()) {
                        if (isWaitCompleted) {
                            if (timeOut == Long.MAX_VALUE) {
                                function()
                            } else {
                                val future = threadPool.submit {
                                    function()
                                }
                                try {
                                    future.get(timeOut, TimeUnit.MILLISECONDS)
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                } catch (e: ExecutionException) {
                                    e.printStackTrace()
                                } catch (e: TimeoutException) {
                                    e.printStackTrace()
                                    Log.i(TAG, "Task TimeoutException")
                                }
                            }
                        } else {
                            threadPool.execute { function() }
                        }
                    }
                    sendEmptyMessageDelayed(MSG, internal)
                }
            }
        }

        fun start(delayMillis: Long) {
            mHandler.sendEmptyMessageDelayed(MSG, delayMillis)
        }

        fun stop(): Boolean {
            if (isSafelyStop) return mainHandlerThread.quitSafely() else return mainHandlerThread.quit()
        }
    }
}