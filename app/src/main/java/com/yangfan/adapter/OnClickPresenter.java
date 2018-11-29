package com.yangfan.adapter;

import android.view.View;

import java.io.Serializable;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */
public interface OnClickPresenter<T extends ItemModel> extends Serializable {
    void onClick(View view, T t);
}
