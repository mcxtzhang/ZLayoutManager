package com.mcxtzhang.flowlayoutmanager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.flowlayoutmanager.other.ImgAdapter;
import com.mcxtzhang.flowlayoutmanager.swipecard.OverlayCardLayoutManager;
import com.mcxtzhang.flowlayoutmanager.util.CommonAdapter;
import com.mcxtzhang.flowlayoutmanager.util.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
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



/*        mRv.setAdapter(mAdapter = new CommonAdapter<TestBean>(this, R.layout.item_flow, mDatas) {
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
        });*/

        //图片的Adapter
        mRv.setAdapter(new ImgAdapter(this));


        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.DOWN |ItemTouchHelper.UP|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
                )  {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                c.drawColor(Color.GREEN);
                View childAt = recyclerView.getChildAt(viewHolder.getLayoutPosition() - 1);
                childAt.setScaleX(1.5f);
                childAt.setScaleY(1.5f);
                Log.e("swipecard", "onChildDraw() called with: c = [" + c + "], recyclerView = [" + recyclerView + "], viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
            }
        };
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);



        findViewById(R.id.btnFlow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mRv.setLayoutManager(new FlowLayoutManager());//自己写的流式布局
                itemTouchHelper.attachToRecyclerView(null);
            }
        });

        findViewById(R.id.btnCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRv.setLayoutManager(new OverlayCardLayoutManager());
                itemTouchHelper.attachToRecyclerView(mRv);
            }
        });
    }

    private int i = 0;

    public List<TestBean> initDatas() {
        mDatas = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
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
/*        mDatas.remove(mDatas.size() - 1);
        mAdapter.notifyItemRemoved(mDatas.size());*/
        Collections.shuffle(mDatas);
        mRv.setAdapter(mAdapter = new CommonAdapter<TestBean>(this, R.layout.item_rv_1, mDatas) {
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
}
