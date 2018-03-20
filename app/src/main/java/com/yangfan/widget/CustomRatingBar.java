package com.yangfan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.yangfan.commonkitlibrary.R;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CustomRatingBar extends RatingBar {
    private final float mDp = getResources().getDisplayMetrics().density;
    private float mStarSize;
    private Bitmap mNormalBmp = null;
    private Bitmap mSelectBmp = null;
    private int margin = 0;
    private int mDrawableNormal = R.drawable.greystar;
    private int mDrawableSelect = R.drawable.yellowstar;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();


    public CustomRatingBar(Context context) {
        this(context, null);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        getXmlAttrs(context, attrs);
        init();
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getXmlAttrs(context, attrs);
        init();
    }

    private void init() {

        mNormalBmp = ((BitmapDrawable) getResources().getDrawable(mDrawableNormal)).getBitmap();
        mSelectBmp = ((BitmapDrawable) getResources().getDrawable(mDrawableSelect)).getBitmap();

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) (50 * mDp * getNumStars());
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        int numStars = getNumStars();

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(heightSize, width / numStars);
        } else {
            //Be whatever you want
            height = width / numStars;
        }

        //If starSize matches getHeight, the tips of the star can get cut off due to strokeWidth
        // being added to the polygon size.
        //Make it a bit smaller to avoid this. Also decrease star size and spread them out rather
        // than cutting them off if the
        //height is insufficient for the width.
        mStarSize = Math.min(height, width / numStars);
        margin = (width - (int) (mStarSize * numStars)) / (numStars - 1);

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {


        int numStars = getNumStars();
        float rating = getRating();
        float floorValue = (float) Math.floor(rating);
        float mod = rating - floorValue;

        int left;
        int right;

//        // 方法一：绘制两层
//
//        for (int i = 0; i < getNumStars(); ++i) {
//            left = i * ((int) mStarSize + margin);
//            right = left + (int) mStarSize;
//            dstRect.set(left, 0, right, (int) mStarSize);
//            canvas.drawBitmap(mNormalBmp, null, dstRect, null);
//        }
//
//        for (int i = 0; i < floorValue; ++i) {
//            left = i * ((int) mStarSize + margin);
//            right = left + (int) mStarSize;
//            dstRect.set(left, 0, right, (int) mStarSize);
//            canvas.drawBitmap(mSelectBmp, null, dstRect, null);
//        }
//
//        if (mod > 0) {
//            int modWidth = (int) (mod * mStarSize);
//            left = (int) (floorValue * ((int) mStarSize + margin));
//            right = left + modWidth;
//            srcRect.set(0, 0, (int) (mod * mSelectBmp.getWidth()), mSelectBmp.getHeight());
//            dstRect.set(left, 0, right, (int) mStarSize);
//            canvas.drawBitmap(mSelectBmp, srcRect, dstRect, null);
//
//        }


        // 方法一：绘制一层

        // 绘制完整 选中星星

        for (int i = 0; i < floorValue; ++i) {
            left = i * ((int) mStarSize + margin);
            right = left + (int) mStarSize;
            srcRect.set(0, 0, mSelectBmp.getWidth(), mSelectBmp.getHeight());
            dstRect.set(left, 0, right, (int) mStarSize);
            canvas.drawBitmap(mSelectBmp, srcRect, dstRect, null);
        }
        // 绘制不完整
        if (mod > 0) {

            // 绘制未选中的 完整 星星
            left = (int) (floorValue * ((int) mStarSize + margin));
            right = left + (int) mStarSize;
            dstRect.set(left, 0, right, (int) mStarSize);
            canvas.drawBitmap(mNormalBmp, null, dstRect, null);


            // 绘制选中的 不完整 星星
            int modWidth = (int) (mod * mStarSize);
            left = (int) (floorValue * (mStarSize + margin));
            right = left + modWidth;
            srcRect.set(0, 0, (int) (mod * mSelectBmp.getWidth()), mSelectBmp.getHeight());
            dstRect.set(left, 0, right, (int) mStarSize);
            canvas.drawBitmap(mSelectBmp, srcRect, dstRect, null);

//            // 绘制未选中的 不完整 星星
//            int temp = left;
//            left = right;
//            right = temp + (int) mStarSize;
//            srcRect.set((int) (mod * mNormalBmp.getWidth()), 0, mNormalBmp.getWidth(), mNormalBmp.getHeight());
//            dstRect.set(left, 0, right, (int) mStarSize);
//            canvas.drawBitmap(mNormalBmp, srcRect, dstRect, null);

        }

        // 绘制未选中
        for (int i = (int) Math.ceil(rating); i < numStars; ++i) {
            left = i * ((int) mStarSize + margin);
            right = left + (int) mStarSize;
            srcRect.set(0, 0, mNormalBmp.getWidth(), mNormalBmp.getHeight());
            dstRect.set(left, 0, right, (int) mStarSize);
            canvas.drawBitmap(mNormalBmp, srcRect, dstRect, null);
        }

    }

    //Set any XML attributes that may have been specified
    private void getXmlAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .CustomRatingBar, 0, 0);
        try {
            mDrawableNormal = a.getResourceId(R.styleable.CustomRatingBar_normalDrawable, R.drawable.greystar);
            mDrawableSelect = a.getResourceId(R.styleable.CustomRatingBar_selectDrawable, R.drawable.yellowstar);

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
