package com.yangfan.model;

import com.yangfan.commonkitlibrary.R;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */
public class TestModel extends BaseViewModel{
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
        if(Integer.valueOf(name)%3==0){
            return R.layout.item_test2;
        }else{
            return R.layout.item_test;
        }
    }
}
