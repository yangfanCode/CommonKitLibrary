package com.yangfan.commonkitlibrary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.github.captain_miao.uniqueadapter.library.OnClickPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yangfan.adapter.BaseRecyclerAdapter;
import com.yangfan.model.TestModel;
import com.yangfan.utils.ToastTools;
import com.yangfan.widget.RecycleViewDivider;
import com.yangfan.widget.RecyclerEmptyView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickPresenter<TestModel>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincustom);
        RecyclerEmptyView recyclerView= (RecyclerEmptyView) findViewById(R.id.recycler);
        SmartRefreshLayout refreshLayout= (SmartRefreshLayout) findViewById(R.id.refrensh);
        final BaseRecyclerAdapter<TestModel> adapter=new BaseRecyclerAdapter<TestModel>(){};
        adapter.setOnClickPresenter(this);
        List<TestModel>list=new ArrayList<>();
        for(int i=0;i<20;i++){
            TestModel testModel=new TestModel();
            testModel.name=i+"";
            list.add(testModel);
        }
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.color_9b9b9b)));
        View v= LayoutInflater.from(this).inflate(R.layout.pop_normal,null);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(v);

        adapter.addAll(list,true);
//        for(int i=0;i<list.size();i++){
//            adapter.add(list.get(i),false);
//        }
//        adapter.notifyDataSetChanged();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ToastTools.showToast(MainActivity.this,"onLoadMore");

                refreshLayout.finishLoadMore(500);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ToastTools.showToast(MainActivity.this,"onRefresh");
                adapter.clear(true);
                refreshLayout.finishRefresh(500);
            }
        });
    }

    @Override
    public void onClick(View view, TestModel testModel) {
        switch (view.getId()){
            case R.id.tv_name:
                ToastTools.showToast(MainActivity.this,"tv:"+testModel.name);
                break;
            case R.id.layout_test:
                ToastTools.showToast(MainActivity.this,"layout:"+testModel.name);
                break;
            case R.id.iv_icon:
                ToastTools.showToast(MainActivity.this,"iv:"+testModel.name);
                break;
        }
    }
}
