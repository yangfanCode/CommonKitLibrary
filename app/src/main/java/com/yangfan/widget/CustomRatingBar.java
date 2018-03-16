package com.yangfan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.yangfan.commonkitlibrary.R.drawable;
import com.yangfan.commonkitlibrary.R.styleable;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CustomRatingBar extends RatingBar {
    private final float mDp;
    private float mStarSize;
    private Bitmap mNormalBmp;
    private Bitmap mSelectBmp;
    private int margin;
    private int mDrawableNormal;
    private int mDrawableSelect;
    private Rect srcRect;
    private Rect dstRect;

    public CustomRatingBar(Context context) {
        this(context, (AttributeSet)null);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDp = this.getResources().getDisplayMetrics().density;
        this.mNormalBmp = null;
        this.mSelectBmp = null;
        this.margin = 0;
        this.mDrawableNormal = drawable.greystar;
        this.mDrawableSelect = drawable.yellowstar;
        this.srcRect = new Rect();
        this.dstRect = new Rect();
        this.getXmlAttrs(context, attrs);
        this.init();
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDp = this.getResources().getDisplayMetrics().density;
        this.mNormalBmp = null;
        this.mSelectBmp = null;
        this.margin = 0;
        this.mDrawableNormal = drawable.greystar;
        this.mDrawableSelect = drawable.yellowstar;
        this.srcRect = new Rect();
        this.dstRect = new Rect();
        this.getXmlAttrs(context, attrs);
        this.init();
    }

    private void init() {
        this.mNormalBmp = ((BitmapDrawable)this.getResources().getDrawable(this.mDrawableNormal)).getBitmap();
        this.mSelectBmp = ((BitmapDrawable)this.getResources().getDrawable(this.mDrawableSelect)).getBitmap();
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int)(50.0F * this.mDp * (float)this.getNumStars());
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int numStars = this.getNumStars();
        int width;
        if(widthMode == 1073741824) {
            width = widthSize;
        } else if(widthMode == -2147483648) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        int height;
        if(heightMode == 1073741824) {
            height = heightSize;
        } else if(heightMode == -2147483648) {
            height = Math.min(heightSize, width / numStars);
        } else {
            height = width / numStars;
        }

        this.mStarSize = (float)Math.min(height, width / numStars);
        this.margin = (width - (int)(this.mStarSize * (float)numStars)) / (numStars - 1);
        this.setMeasuredDimension(width, height);
    }

    protected void onDraw(@NonNull Canvas canvas) {
        int numStars = this.getNumStars();
        float rating = this.getRating();
        float floorValue = (float)Math.floor((double)rating);
        float mod = rating - floorValue;

        int left;
        int right;
        int i;
        for(i = 0; (float)i < floorValue; ++i) {
            left = i * ((int)this.mStarSize + this.margin);
            right = left + (int)this.mStarSize;
            this.srcRect.set(0, 0, this.mSelectBmp.getWidth(), this.mSelectBmp.getHeight());
            this.dstRect.set(left, 0, right, (int)this.mStarSize);
            canvas.drawBitmap(this.mSelectBmp, this.srcRect, this.dstRect, (Paint)null);
        }

        if(mod > 0.0F) {
            left = (int)(floorValue * (float)((int)this.mStarSize + this.margin));
            right = left + (int)this.mStarSize;
            this.dstRect.set(left, 0, right, (int)this.mStarSize);
            canvas.drawBitmap(this.mNormalBmp, (Rect)null, this.dstRect, (Paint)null);
            i = (int)(mod * this.mStarSize);
            left = (int)(floorValue * (this.mStarSize + (float)this.margin));
            right = left + i;
            this.srcRect.set(0, 0, (int)(mod * (float)this.mSelectBmp.getWidth()), this.mSelectBmp.getHeight());
            this.dstRect.set(left, 0, right, (int)this.mStarSize);
            canvas.drawBitmap(this.mSelectBmp, this.srcRect, this.dstRect, (Paint)null);
        }

        for(i = (int)Math.ceil((double)rating); i < numStars; ++i) {
            left = i * ((int)this.mStarSize + this.margin);
            right = left + (int)this.mStarSize;
            this.srcRect.set(0, 0, this.mNormalBmp.getWidth(), this.mNormalBmp.getHeight());
            this.dstRect.set(left, 0, right, (int)this.mStarSize);
            canvas.drawBitmap(this.mNormalBmp, this.srcRect, this.dstRect, (Paint)null);
        }

    }

    private void getXmlAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, styleable.CustomRatingBar, 0, 0);

        try {
            this.mDrawableNormal = a.getResourceId(styleable.CustomRatingBar_normalDrawable, drawable.greystar);
            this.mDrawableSelect = a.getResourceId(styleable.CustomRatingBar_selectDrawable, drawable.yellowstar);
        } finally {
            a.recycle();
        }

    }

    public void setDrawableNormal(int mDrawableNormal) {
        this.mDrawableNormal = mDrawableNormal;
    }

    public void setDrawableSelect(int mDrawableSelect) {
        this.mDrawableSelect = mDrawableSelect;
    }
}
