package com.mcxtzhang.flowlayoutmanager.avatar;

import android.animation.ValueAnimator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/6/22.
 * History:
 */

public class TanTanAnimator extends DefaultItemAnimator {
    @Override
    public boolean animateMove(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {


        if (holder.getAdapterPosition() == 0) {
            ValueAnimator animator = ValueAnimator.ofFloat(0.5f, 1f);
            animator.setDuration(getMoveDuration());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    holder.itemView.setScaleX((Float) animation.getAnimatedValue());
                    holder.itemView.setScaleY((Float) animation.getAnimatedValue());
                }
            });
            animator.start();
        }

        return super.animateMove(holder, fromX, fromY, toX, toY);


    }
}
