package com.yangfan.utils;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

/**
 * Schedule a countdown until a time in the future, with
 * regular notifications on intervals along the way.
 * <p>
 * Example of showing a 30 second countdown in a text field:
 * <p>
 * <pre class="prettyprint">
 * new CountDownTimer(30000, 1000) {
 * <p>
 * public void onTick(long millisUntilFinished) {
 * mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
 * }
 * <p>
 * public void onFinish() {
 * mTextField.setText("done!");
 * }
 * }.start();
 * </pre>
 * <p>
 * The calls to {@link #onTick(long)} are synchronized to this object so that
 * one call to {@link #onTick(long)} won't ever occur before the previous
 * callback is complete.  This is only relevant when the implementation of
 * {@link #onTick(long)} takes an amount of time to execute that is significant
 * compared to the countdown interval.
 */

/**
 * Copy from API 26 's CountDownTimer.
 * API 26 CountDownTimer 的源码副本。
 * 仅增加了中文注释和 Log 打印代码。(注释为 Add 的代码)
 * <p>
 * date 2018/1/9
 */
public abstract class CountDownTimer {
    private String TAG = "CountDownTimer-26";//Add

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }

        //Add
        Log.i(TAG, "start → mMillisInFuture = " + mMillisInFuture + ", seconds = " + mMillisInFuture / 1000);

        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;

        //Add
        Log.i(TAG, "start → mStopTimeInFuture = " + mStopTimeInFuture);

        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }


    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    // handles counting down
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountDownTimer.this) {
                if (mCancelled) {
                    return;
                }

                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                //Add
                Log.i(TAG, "handleMessage → elapsedRealtime = " + SystemClock.elapsedRealtime());
                Log.i(TAG, "handleMessage → millisLeft = " + millisLeft + ", seconds = " + millisLeft / 1000);

                if (millisLeft <= 0) {
                    //Add
                    Log.i(TAG, "onFinish → millisLeft = " + millisLeft);

                    onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();

                    //Add
                    Log.i(TAG, "before onTick → lastTickStart = " + lastTickStart);
                    Log.i(TAG, "before onTick → millisLeft = " + millisLeft + ", seconds = " + millisLeft / 1000);

                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    // 考虑到用户执行 onTick 需要时间
                    long lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart;
                    long delay;

                    //Add
                    Log.i(TAG, "after onTick → lastTickDuration = " + lastTickDuration);
                    Log.i(TAG, "after onTick → elapsedRealtime = " + SystemClock.elapsedRealtime());

                    if (millisLeft < mCountdownInterval) {
                        // just delay until done
                        //直接延迟到计时结束
                        delay = millisLeft - lastTickDuration;

                        //Add
                        Log.i(TAG, "millisLeft < mCountdownInterval!");
                        Log.i(TAG, "after onTick → delay1 = " + delay);

                        // special case: user's onTick took more than interval to
                        // complete, trigger onFinish without delay
                        // 特殊情况：用户的 onTick 执行时间超过了给定的时间间隔 mCountdownInterval，则立即触发 onFinish
                        if (delay < 0) delay = 0;

                        //Add
                        Log.i(TAG, "after onTick → delay2 = " + delay);
                    } else {
                        delay = mCountdownInterval - lastTickDuration;

                        //Add
                        Log.i(TAG, "after onTick → delay1 = " + delay);

                        // special case: user's onTick took more than interval to
                        // complete, skip to next interval
                        // 特殊情况：用户的 onTick 执行时间超过了给定的时间间隔 mCountdownInterval，则直接跳到下一次间隔
                        while (delay < 0) delay += mCountdownInterval;

                        //Add
                        Log.i(TAG, "after onTick → delay2 = " + delay);
                    }

                    //Add
                    Log.i(TAG, "before send msg → elapsedRealtime = " + SystemClock.elapsedRealtime());

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };

    /**
     * 获得准确秒数
     * @param millisUntilFinished
     * @return
     */
    public synchronized final long getMillisUntilSecond(long millisUntilFinished){
        return  Math.round((double) millisUntilFinished / 1000);
    }
}
