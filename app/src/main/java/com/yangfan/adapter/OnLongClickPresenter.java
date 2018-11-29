package com.yangfan.adapter;

import android.view.View;

import java.io.Serializable;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */
public interface OnLongClickPresenter<T extends ItemModel> extends Serializable {
    boolean onLongClick(View view, T t);
}
