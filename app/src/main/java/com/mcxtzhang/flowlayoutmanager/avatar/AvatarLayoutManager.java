package com.mcxtzhang.flowlayoutmanager.avatar;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/6/21.
 * History:
 */

public class AvatarLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount()>0){
            detachAndScrapAttachedViews(recycler);
        }
        int itemCount = getItemCount();
        if (itemCount < 6)
            return;

        int width = getWidth();
        int gap = width / 3;

        int left = 0, top = 0;
        for (int i = 0; i < 6; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            if (i == 0) {
                layoutDecoratedWithMargins(view, left, top, left + gap * 2, top + gap * 2);
                left += gap * 2;

            } else {
                layoutDecoratedWithMargins(view, left, top, left + gap, top + gap);
                if (i > 0 && i < 3) {
                    top += gap;
                } else {
                    left -= gap;
                }
            }
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }
}
