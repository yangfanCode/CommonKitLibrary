package com.yangfan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.yangfan.commonkitlibrary.R;
import com.yangfan.utils.DecimalDigitsWatcher;


/**
 * Created by yangfan
 * nrainyseason@163.com
 * 限制editText小数位数 避免先输入后加小数点
 */
public class DecimalDigitsEditText extends AppCompatEditText {
    private int beforeDot=-1,afterDot=-1;
    public DecimalDigitsEditText(Context context) {
        super(context);
    }

    public DecimalDigitsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DecimalDigitsEditText);
        beforeDot = (int) array.getInteger(R.styleable.DecimalDigitsEditText_beforeDot, -1);
        afterDot = (int) array.getInteger(R.styleable.DecimalDigitsEditText_afterDot, -1);
        addTextChangedListener(new DecimalDigitsWatcher(beforeDot,afterDot));
    }

    public void setBeforeDot(int beforeDot){
        this.beforeDot=beforeDot;
        addTextChangedListener(new DecimalDigitsWatcher(beforeDot,afterDot));
    }

    public void setAfterDot(int afterDot){
        this.afterDot=afterDot;
        addTextChangedListener(new DecimalDigitsWatcher(beforeDot,afterDot));
    }

}
