package com.mcxtzhang.flowlayoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.flowlayoutmanager.util.CommonAdapter;
import com.mcxtzhang.flowlayoutmanager.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private CommonAdapter<TestBean> mAdapter;
    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new FlowLayoutManager());//自己写的流式布局
        mRv.setAdapter(mAdapter = new CommonAdapter<TestBean>(this, R.layout.item_flow, mDatas) {
            @Override
            public void convert(ViewHolder holder, TestBean testBean) {
                Log.d("zxt", "convert() called with: holder = [" + holder + "], testBean = [" + testBean + "]");
                holder.setText(R.id.tv, testBean.getName() + testBean.getUrl());
                holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG1", "onClick() called with: v = [" + v + "]");
                    }
                });
            }
        });
    }

    private int i = 0;

    public List<TestBean> initDatas() {
        mDatas = new ArrayList<>();
        for (int j = 0; j < 60; j++) {
            mDatas.add(new TestBean((i++) + "  ", "张"));
            mDatas.add(new TestBean((i++) + " ", "旭童"));
            mDatas.add(new TestBean((i++) + " ", "多种type"));
            mDatas.add(new TestBean((i++) + "    ", "遍"));
            mDatas.add(new TestBean((i++) + "   ", "多种type"));
            mDatas.add(new TestBean((i++) + "  ", "多种type"));
            mDatas.add(new TestBean((i++) + "  ", "多种type"));
            mDatas.add(new TestBean((i++) + "  ", "多种type"));
        }
        return mDatas;
    }

    public void add(View vIew) {
        mDatas.add(new TestBean((i++) + "  ", "新增的一个Item"));
        mAdapter.notifyItemInserted(mDatas.size() - 1);
    }

    public void del(View vIew) {
        mDatas.remove(mDatas.size() - 1);
        mAdapter.notifyItemRemoved(mDatas.size());
    }
}
