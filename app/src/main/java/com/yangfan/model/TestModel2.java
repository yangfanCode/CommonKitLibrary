package com.yangfan.model;

import com.yangfan.commonkitlibrary.R;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */
public class TestModel2 extends BaseViewModel{
    public int img;
    public String name;

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public long getType() {
        return 0;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_test2;
    }
}
