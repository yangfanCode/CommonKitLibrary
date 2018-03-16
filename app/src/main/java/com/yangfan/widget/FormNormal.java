package com.yangfan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangfan.commonkitlibrary.R.color;
import com.yangfan.commonkitlibrary.R.dimen;
import com.yangfan.commonkitlibrary.R.drawable;
import com.yangfan.commonkitlibrary.R.id;
import com.yangfan.commonkitlibrary.R.layout;
import com.yangfan.commonkitlibrary.R.styleable;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class FormNormal extends LinearLayout {
    private TextView tvTitle;
    private EditText etValue;
    private ImageView imvIndicator;
    private TextView tvValue;
    private ImageView imvLabel;
    private FrameLayout layContent;
    private boolean editable;
    private FormNormal.FormNormalTypeEnum mTypeEnum;

    public FormNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTypeEnum = FormNormal.FormNormalTypeEnum.TYPE_NORMAL;
        this.initUI(context, attrs);
    }

    private void initUI(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(layout.view_form_normal, this, true);
        int left = this.getResources().getDimensionPixelSize(dimen.row_inner_left_padding);
        int top = this.getResources().getDimensionPixelSize(dimen.row_inner_vertical_padding);
        int right = this.getResources().getDimensionPixelSize(dimen.row_inner_right_padding);
        int bottom = this.getResources().getDimensionPixelSize(dimen.row_inner_vertical_padding);
        this.tvTitle = (TextView)this.findViewById(id.tv_title);
        this.etValue = (EditText)this.findViewById(id.et_value);
        this.imvIndicator = (ImageView)this.findViewById(id.imv_indicator);
        this.tvValue = (TextView)this.findViewById(id.tv_value);
        this.imvLabel = (ImageView)this.findViewById(id.imv_label);
        this.layContent = (FrameLayout)this.findViewById(id.lay_content);
        TypedArray a = context.obtainStyledAttributes(attrs, styleable.FormNormal);
        if(a != null) {
            CharSequence hint = a.getText(styleable.FormNormal_fnHint);
            CharSequence title = a.getText(styleable.FormNormal_fnTitle);
            CharSequence text = a.getText(styleable.FormNormal_fnText);
            boolean indicatorVisible = a.getBoolean(styleable.FormNormal_fnIndicatorVisible, true);
            int indicatorResid = a.getResourceId(styleable.FormNormal_fnIndicatorResId, -1);
            this.editable = a.getBoolean(styleable.FormNormal_fnEditable, false);
            boolean fnGravityLeft = a.getBoolean(styleable.FormNormal_fnGravityLeft, false);
            int resid = a.getResourceId(styleable.FormNormal_fnResId, -1);
            boolean fnSetClearable = a.getBoolean(styleable.FormNormal_fnSetClearable, false);
            int titleTextSize = a.getInteger(styleable.FormNormal_fnTitleTextSize, 17);
            int textSize = a.getInteger(styleable.FormNormal_fnTextSize, -1);
            int titleTextColor = a.getResourceId(styleable.FormNormal_fnTitleTextColor, color.color_333333);
            int textColor = a.getResourceId(styleable.FormNormal_fnTextColor, -1);
            left = a.getDimensionPixelSize(styleable.FormNormal_fnLeftPadding, left);
            top = a.getDimensionPixelSize(styleable.FormNormal_fnTopPadding, top);
            right = a.getDimensionPixelSize(styleable.FormNormal_fnRightPadding, right);
            bottom = a.getDimensionPixelSize(styleable.FormNormal_fnBottomPadding, bottom);
            a.recycle();
            this.setTitleTextSize((float)titleTextSize);
            this.setTitleTextColor(titleTextColor);
            this.setTextSize((float)textSize);
            this.setTextColor(textColor);
            if(!TextUtils.isEmpty(hint)) {
                this.setHint(hint.toString());
            }

            if(!TextUtils.isEmpty(title)) {
                this.setTitle(title.toString());
            }

            if(!TextUtils.isEmpty(text)) {
                this.setText(text.toString());
            }

            this.setImvIndicatorVisible(indicatorVisible);
            this.setEtValueEditable(this.editable);
            this.setGravity(fnGravityLeft);
            this.setImvLabelImageResource(resid);
            this.setImvIndicatorImageResource(indicatorResid);
            if(fnSetClearable) {
                this.setClearable();
            }
        }

        this.setPadding2(left, top, right, bottom);
    }

    public void init(String title, String text) {
        this.setTitle(title);
        this.setHint(text);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setTitleMinWidth(int minWidth) {
        this.tvTitle.setMinWidth(minWidth);
        this.tvTitle.setMinimumWidth(minWidth);
    }

    public void setGravity(boolean isLeft) {
        this.etValue.setGravity(isLeft?3:5);
        this.tvValue.setGravity(isLeft?3:5);
    }

    public void setText(String text) {
        this.etValue.setText(text);
        this.tvValue.setText(text);
    }

    public void setText(String text, int clrResId) {
        this.setText(text);
        this.etValue.setTextColor(this.getResources().getColor(clrResId));
        this.tvValue.setTextColor(this.getResources().getColor(clrResId));
    }

    public void setText(String title, String text, int clrResId) {
        this.tvTitle.setText(title);
        this.setText(text);
        this.etValue.setTextColor(this.getResources().getColor(clrResId));
        this.tvValue.setTextColor(this.getResources().getColor(clrResId));
    }

    public void setTextDrawableRight(int drawableRightResId) {
        this.setHint("");
        Drawable drawable = this.getContext().getResources().getDrawable(drawableRightResId);
        this.tvValue.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, drawable, (Drawable)null);
    }

    public void setTitleTextColor(int clrResId) {
        this.tvTitle.setTextColor(this.getResources().getColor(clrResId));
    }

    public void setTitleTextSize(float size) {
        this.tvTitle.setTextSize(size);
    }

    public void setTextColor(int clrResId) {
        if(clrResId != -1) {
            this.etValue.setTextColor(this.getResources().getColor(clrResId));
            this.tvValue.setTextColor(this.getResources().getColor(clrResId));
        }

    }

    public void setTextSize(float size) {
        if(size != -1.0F) {
            this.tvValue.setTextSize(size);
            this.etValue.setTextSize(size);
        }

    }

    public void setImvIndicatorVisible(boolean isVisible) {
        this.imvIndicator.setVisibility(isVisible?VISIBLE:GONE);
    }

    public void setEtValueEditable(boolean editable) {
        this.etValue.setEnabled(editable);
        this.etValue.setVisibility(editable?VISIBLE:GONE);
        this.tvValue.setVisibility(editable?GONE:VISIBLE);
    }

    public void setImvLabelImageResource(int resId) {
        if(resId != -1) {
            this.imvLabel.setImageResource(resId);
            this.imvLabel.setVisibility(VISIBLE);
        } else {
            this.imvLabel.setVisibility(GONE);
        }

    }

    public void setImvIndicatorImageResource(int resId) {
        if(resId != -1) {
            this.imvIndicator.setImageResource(resId);
        }

    }

    public String getText() {
        return this.getTextView().getText().toString();
    }

    public TextView getTextView() {
        return (TextView)(this.editable?this.etValue:this.tvValue);
    }

    public EditText getEtValue() {
        return this.etValue;
    }

    public TextView getTvTitle() {
        return this.tvTitle;
    }

    public ImageView getImvIndicator() {
        return this.imvIndicator;
    }

    public FrameLayout getLayContent() {
        return this.layContent;
    }

    public ImageView getImvLabel() {
        return this.imvLabel;
    }

    public void setHint(String text) {
        this.etValue.setHint(text);
        this.tvValue.setHint(text);
    }

    public void setPadding2(int left, int top, int right, int bottom) {
        this.setPadding(left, top, right, bottom);
    }

    public void setClearable() {
        this.imvIndicator.setVisibility(INVISIBLE);
        this.setImvIndicatorImageResource(drawable.icon_clear);
        this.getTextView().addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                FormNormal.this.imvIndicator.setVisibility(s.length() > 0?VISIBLE:INVISIBLE);
            }
        });
        this.imvIndicator.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FormNormal.this.getTextView().setText("");
            }
        });
    }

    public void setMaxLength(int maxLength) {
        this.etValue.setFilters(new InputFilter[]{new LengthFilter(maxLength)});
    }

    public void addTextChangedListener(TextWatcher watcher) {
        this.etValue.addTextChangedListener(watcher);
    }

    public void chooseInputType(FormNormal.FormNormalTypeEnum typeEnum) {
        this.mTypeEnum = typeEnum;
        switch(mTypeEnum.ordinal()) {
            case 1:
                this.etValue.setInputType(1);
                break;
            case 2:
                this.etValue.setInputType(2);
                break;
            case 3:
                this.etValue.setInputType(3);
                break;
            case 4:
                this.etValue.setInputType(129);
                break;
            case 5:
                this.etValue.setInputType(145);
                break;
            case 6:
                this.etValue.setInputType(8194);
                break;
            case 7:
                this.etValue.setInputType(32);
        }

    }

    public static enum FormNormalTypeEnum {
        TYPE_NORMAL(0),
        TYPE_CLASS_PHONE(1),
        TYPE_PASSWORD(2),
        TYPE_NUMBER_OR_DECIMAL(4),
        TYPE_VISIBLE_PASSWORD(5),
        TYPE_CLASS_NUMBER(6),
        TYPE_TEXT_VARIATION_EMAIL_ADDRESS(7);

        private int mCode;

        public int getCode() {
            return this.mCode;
        }

        private FormNormalTypeEnum(int code) {
            this.mCode = code;
        }
    }
}
