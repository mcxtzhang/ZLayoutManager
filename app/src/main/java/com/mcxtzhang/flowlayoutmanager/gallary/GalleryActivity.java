package com.mcxtzhang.flowlayoutmanager.gallary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.flowlayoutmanager.R;
import com.mcxtzhang.flowlayoutmanager.TestBean;
import com.mcxtzhang.flowlayoutmanager.other.ImgAdapter;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private CommonAdapter<TestBean> mAdapter;
    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initDatas();
        mRv = (RecyclerView) findViewById(R.id.rv);

        mAdapter = new CommonAdapter<TestBean>(this, mDatas, R.layout.item_flow) {
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
        };

        //图片的Adapter
        final ImgAdapter imgAdapter = new ImgAdapter(this);
        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new GalleryLayoutManager());
    }

    private int i = 0;

    public List<TestBean> initDatas() {
        mDatas = new ArrayList<>();
        for (int j = 0; j < 1; j++) {
            mDatas.add(new TestBean((i++) + "  ", "张旭童"));
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
}
