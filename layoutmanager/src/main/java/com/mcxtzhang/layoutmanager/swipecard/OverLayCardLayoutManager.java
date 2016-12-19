package com.mcxtzhang.layoutmanager.swipecard;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍：参考人人影视 最多排列四个
 * 重叠卡片布局
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/15.
 */

public class OverLayCardLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "swipecard";

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.e(TAG, "onLayoutChildren() called with: recycler = [" + recycler + "], state = [" + state + "]");
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }
        //top-3View的position
        int bottomPosition;
        //边界处理
        if (itemCount < CardConfig.MAX_SHOW_COUNT) {
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }

        //从可见的最底层View开始layout，依次层叠上去
        for (int position = bottomPosition; position < itemCount; position++) {
            View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            //我们在布局时，将childView居中处理，这里也可以改为只水平居中
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
            /**
             * TopView的Scale 为1，translationY 0
             * 每一级Scale相差0.05f，translationY相差7dp左右
             *
             * 观察人人影视的UI，拖动时，topView被拖动，Scale不变，一直为1.
             * top-1View 的Scale慢慢变化至1，translation也慢慢恢复0
             * top-2View的Scale慢慢变化至 top-1View的Scale，translation 也慢慢变化只top-1View的translation
             * top-3View的Scale要变化，translation岿然不动
             */

            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = itemCount - position - 1;
            //除了顶层不需要缩小和位移
            if (level > 0 /*&& level < mShowCount - 1*/) {
                //每一层都需要X方向的缩小
                view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                //前N层，依次向下位移和Y方向的缩小
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                    view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                } else {//第N层在 向下位移和Y方向的缩小的成都与 N-1层保持一致
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }
            }
        }
    }

}
