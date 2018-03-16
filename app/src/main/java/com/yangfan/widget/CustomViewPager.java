package com.yangfan.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CustomViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    public boolean onTouchEvent(MotionEvent arg0) {
        return this.isCanScroll?super.onTouchEvent(arg0):false;
    }

    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return this.isCanScroll?super.onInterceptTouchEvent(arg0):false;
    }
}
