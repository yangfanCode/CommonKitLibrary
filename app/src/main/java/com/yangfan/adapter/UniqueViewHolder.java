package com.yangfan.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */
public class UniqueViewHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding dataBinding;

    public UniqueViewHolder(View itemView) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding() {
        return dataBinding;
    }
}