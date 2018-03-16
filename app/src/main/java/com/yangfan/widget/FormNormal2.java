package com.yangfan.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangfan.commonkitlibrary.R.dimen;
import com.yangfan.commonkitlibrary.R.id;
import com.yangfan.commonkitlibrary.R.layout;
import com.yangfan.commonkitlibrary.R.styleable;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class FormNormal2 extends LinearLayout {
    private TextView tvTitle;
    private TextView tvTitle2;
    private ImageView imvIndicator;
    private TextView tvValue;
    private ImageView imvLabel;

    public FormNormal2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initUI(context, attrs);
    }

    private void initUI(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(layout.view_form_normal2, this, true);
        int left = this.getResources().getDimensionPixelSize(dimen.row_inner_left_padding);
        int top = left;
        int right = left;
        int bottom = left;
        this.tvTitle = (TextView)this.findViewById(id.tv_title);
        this.tvTitle2 = (TextView)this.findViewById(id.tv_title2);
        this.imvIndicator = (ImageView)this.findViewById(id.imv_indicator);
        this.tvValue = (TextView)this.findViewById(id.tv_value);
        this.imvLabel = (ImageView)this.findViewById(id.imv_label);
        TypedArray a = context.obtainStyledAttributes(attrs, styleable.FormNormal2);
        CharSequence hint = a.getText(styleable.FormNormal2_fn2Hint);
        CharSequence title = a.getText(styleable.FormNormal2_fn2Title);
        CharSequence title2 = a.getText(styleable.FormNormal2_fn2Title2);
        CharSequence text = a.getText(styleable.FormNormal2_fn2Text);
        boolean indicatorVisible = a.getBoolean(styleable.FormNormal2_fn2IndicatorVisible, true);
        int resId = a.getResourceId(styleable.FormNormal2_fn2ResId, -1);
        left = a.getDimensionPixelSize(styleable.FormNormal2_fn2LeftPadding, left);
        top = a.getDimensionPixelSize(styleable.FormNormal2_fn2TopPadding, top);
        right = a.getDimensionPixelSize(styleable.FormNormal2_fn2RightPadding, right);
        bottom = a.getDimensionPixelSize(styleable.FormNormal2_fn2BottomPadding, bottom);
        a.recycle();
        if(!TextUtils.isEmpty(hint)) {
            this.setHint(hint.toString());
        }

        if(!TextUtils.isEmpty(title)) {
            this.setTitle(title.toString());
        }

        if(!TextUtils.isEmpty(title2)) {
            this.setTitle2(title2.toString());
        }

        if(!TextUtils.isEmpty(text)) {
            this.setText(text.toString());
        }

        this.setImvLabelImageResource(resId);
        this.setImvIndicatorVisible(indicatorVisible);
        this.setPadding2(left, top, right, bottom);
    }

    public void setPadding2(int left, int top, int right, int bottom) {
        this.setPadding(left, top, right, bottom);
    }

    public void init(String title, String text) {
        this.setTitle(title);
        this.setHint(text);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setTitle2(String title2) {
        this.tvTitle2.setText(title2);
    }

    public void setText(String text) {
        this.tvValue.setText(text);
    }

    public void setText(String text, int clrResId) {
        this.setText(text);
        this.tvValue.setTextColor(this.getResources().getColor(clrResId));
    }

    public void setTextDrawableRight(int drawableRightResId) {
        this.setHint("");
        Drawable drawable = null;

        try {
            drawable = this.getContext().getResources().getDrawable(drawableRightResId);
        } catch (Resources.NotFoundException var4) {
            drawable = null;
        }

        this.tvValue.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, drawable, (Drawable)null);
    }

    public void setText(String title, String text, int clrResId) {
        this.tvTitle.setText(title);
        this.setText(text);
        this.tvValue.setTextColor(this.getResources().getColor(clrResId));
    }

    public void setTextColor(int clrResId) {
        this.tvValue.setTextColor(this.getResources().getColor(clrResId));
    }

    public void setImvIndicatorVisible(boolean isVisible) {
        this.imvIndicator.setVisibility(isVisible?VISIBLE:GONE);
    }

    public void setImvLabelImageResource(int resId) {
        if(resId != -1) {
            this.imvLabel.setImageResource(resId);
            this.imvLabel.setVisibility(VISIBLE);
        } else {
            this.imvLabel.setVisibility(GONE);
        }

    }

    public String getText() {
        return this.getTextView().getText().toString();
    }

    public TextView getTextView() {
        return this.tvValue;
    }

    public TextView getTvTitle() {
        return this.tvTitle;
    }

    public void setHint(String text) {
        this.tvValue.setHint(text);
    }

    public TextView getTvTitle2() {
        return this.tvTitle2;
    }
}
