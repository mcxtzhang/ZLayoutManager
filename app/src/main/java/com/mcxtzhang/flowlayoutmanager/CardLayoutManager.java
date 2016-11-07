package com.mcxtzhang.flowlayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍：奇妙的卡片布局
 * 这里就假设每个Item Card 大小是一样的
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/11/05.
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {

    private int mFullShowPosition;//完全展示的那个Item的positon
    private int gap = 150;//完全展示Item距离下个Item的TopGap
    private int mItemHeight;
    private int mItemWidth;


    private int mFullShowViewBaseTop;//完全展示的Item的 基准Top
    private int mTopViewBaseTop;//第一个只展示一个底边的View的基准Top


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {//没有Item，界面空着吧
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {//state.isPreLayout()是支持动画的
            return;
        }


        //初始化肯定是第一个全显示
        mFullShowPosition = 0;

        if (mItemHeight == 0) {
            View child = recycler.getViewForPosition(0);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            mItemHeight = getDecoratedMeasuredHeight(child);
            mItemWidth = getDecoratedMeasuredWidth(child);
            mTopViewBaseTop = (int) (getPaddingTop() - (mItemHeight * 0.9f));
            mFullShowViewBaseTop = mTopViewBaseTop + mItemHeight;

        }

        //暂时detach所有View
        detachAndScrapAttachedViews(recycler);

        if (getChildCount() > 1) {//取childCount-2 那个是fullShow的
            View firstView = getChildAt(getChildCount() - 2);
            if (firstView != null) {
                mFullShowPosition = getPosition(firstView);
            }
        }


        int left = getPaddingLeft();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();

        //后add的View会覆盖前面的View， 本身是从小->大Layout、
        // 所以我们还是从 postion大->小 add吧。
        for (int i = mFullShowPosition + 1; i >= 0; i--) {

            //不可见了 不添加了
            if (top > getParentBottomLocation()) {
                break;
            }

            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            if (i == mFullShowPosition + 1) {
                layoutDecoratedWithMargins(child, left, mTopViewBaseTop,
                        left + mItemWidth, mTopViewBaseTop + mItemHeight);
                top = mFullShowViewBaseTop;
            } else if (i == mFullShowPosition) {
                layoutDecoratedWithMargins(child, left, top,
                        left + mItemWidth, top + mItemHeight);
                top = top + mItemHeight + gap;
            } else {
                layoutDecoratedWithMargins(child, left, top,
                        left + mItemWidth, top + mItemHeight);
                top = (int) (top + (mItemHeight * 0.3f));
            }
        }

        Log.d("TAG", "count= [" + getChildCount() + "]" + ",[recycler.getScrapList().size():" + recycler.getScrapList().size());


    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //位移0、没有子View 当然不移动
        if (dy == 0 || getChildCount() == 0) {
            return 0;
        }
        int realOffset = dy;//实际滑动的距离， 可能会在边界处被修复

        //边界修复代码
        if (realOffset < 0) {//上边界
            //if ()

        } else if (realOffset > 0) {//下边界 ,最后一个View positoin ==0，
            if (mFullShowPosition == 0) {
                View fullshowView = getChildAt(getChildCount() - 1);
                int fullshowViewTop = getDecoratedTopWithMargin(fullshowView);
                if (fullshowViewTop - realOffset < mFullShowViewBaseTop) {
                    realOffset = fullshowViewTop - mFullShowViewBaseTop;
                }
            }
        }

        offsetChildrenVertical(-realOffset);//滑动

        return realOffset;
    }

    /**
     * 得到某个View的Top（包含marigin）
     *
     * @param view
     * @return
     */
    public int getDecoratedTopWithMargin(View view) {
        return getDecoratedTop(view) - ((RecyclerView.LayoutParams)
                view.getLayoutParams()).topMargin;
    }

    //模仿LLM Horizontal 源码

    /**
     * 获取某个childView在水平方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }

    /**
     * 父底边的坐标
     *
     * @return
     */
    public int getParentBottomLocation() {
        return getHeight() - getPaddingBottom();
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
