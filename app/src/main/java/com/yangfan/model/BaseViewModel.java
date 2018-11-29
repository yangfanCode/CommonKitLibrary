package com.yangfan.model;

import com.yangfan.adapter.ItemModel;

import java.io.Serializable;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */
public abstract class BaseViewModel implements ItemModel, Serializable {
    private static final long serialVersionUID = 20160903L;

    public abstract long getId();

    public abstract long getType();
}
