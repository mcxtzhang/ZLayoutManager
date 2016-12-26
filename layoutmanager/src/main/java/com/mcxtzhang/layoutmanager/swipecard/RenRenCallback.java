package com.mcxtzhang.layoutmanager.swipecard;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import static com.mcxtzhang.layoutmanager.swipecard.CardConfig.MAX_SHOW_COUNT;
import static com.mcxtzhang.layoutmanager.swipecard.CardConfig.SCALE_GAP;
import static com.mcxtzhang.layoutmanager.swipecard.CardConfig.TRANS_Y_GAP;

/**
 * 介绍：人人影视效果的Callback
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/18.
 */

public class RenRenCallback extends ItemTouchHelper.SimpleCallback {

    protected RecyclerView mRv;
    protected List mDatas;
    protected RecyclerView.Adapter mAdapter;

    public RenRenCallback(RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        this(0,
                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                rv, adapter, datas);
    }

    public RenRenCallback(int dragDirs, int swipeDirs
            , RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        super(dragDirs, swipeDirs);
        mRv = rv;
        mAdapter = adapter;
        mDatas = datas;
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
        return mRv.getWidth() * /*getSwipeThreshold(viewHolder)*/ 0.5f;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
        //rollBack(viewHolder);
        //★实现循环的要点
        Object remove = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //Log.e("swipecard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
        //人人影视的效果
        //if (isCurrentlyActive) {
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if (level > 0) {
                child.setScaleX((float) (1 - SCALE_GAP * level + fraction * SCALE_GAP));

                if (level < MAX_SHOW_COUNT - 1) {
                    child.setScaleY((float) (1 - SCALE_GAP * level + fraction * SCALE_GAP));
                    child.setTranslationY((float) (TRANS_Y_GAP * level - fraction * TRANS_Y_GAP));
                } else {
                    //child.setTranslationY((float) (mTranslationYGap * (level - 1) - fraction * mTranslationYGap));
                }
            }
        }
    }
}
