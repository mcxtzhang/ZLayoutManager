package com.mcxtzhang.layoutmanager.gallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;

import java.util.List;

/**
 * Intro:  横向 无限循环滑动 自动居中 视差效果的画廊
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/7/4.
 * History:
 */

public class BaseLoopGallery<T> extends RecyclerView {
    Context mContext;
    LinearLayoutManager mLinearLayoutManager;
    CommonAdapter mAdapter;
    SnapHelper mSnapHelper;

    public BaseLoopGallery(Context context) {
        super(context);
        init(context);
    }

    public BaseLoopGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseLoopGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        //布局
        setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        //居中
        mSnapHelper = new LinearSnapHelper();
        mSnapHelper.attachToRecyclerView(this);

        //渐变
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int middle = (int) (getX() + getWidth() / 2);
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    int childMiddle = (int) (child.getX() + child.getWidth() / 2);
                    int gap = Math.abs(middle - childMiddle);
                    float fraction = gap * 1.0f / getWidth() / 2;
                    scale(child, fraction);
                }
            }

            private void scale(View child, float fraction) {
                final float MIN_SCALE = 0.65f;
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(fraction));
                child.setScaleX(scaleFactor);
                child.setScaleY(scaleFactor);
            }
        });
    }

    public void setDatasAndLayoutId(final List<T> datas, int itemLayoutId, BindDataListener bindDataListener) {
        mBindDataListener = bindDataListener;

        setAdapter(mAdapter = new CommonAdapter<T>(mContext, datas, itemLayoutId) {
            @Override
            public void onBindViewHolder(com.mcxtzhang.commonadapter.rv.ViewHolder holder, int position) {
                position = position % mDatas.size();
                super.onBindViewHolder(holder, position);
            }

            @Override
            public void convert(com.mcxtzhang.commonadapter.rv.ViewHolder viewHolder, T t) {
                //onBindData(viewHolder, t);
                if (null != mBindDataListener) {
                    mBindDataListener.onBindData(viewHolder, t);
                }
            }

            @Override
            public T getItem(int position) {
                return mDatas.get(position % mDatas.size());
            }

            @Override
            public long getItemId(int position) {
                return (position % mDatas.size());
            }


            @Override
            public int getItemCount() {
                return Integer.MAX_VALUE;
            }
        });


        //首次进入自动居中
        post(new Runnable() {
            @Override
            public void run() {
                if (datas != null) {
                    scrollToPosition(datas.size() * 1000);
                    snapToTargetExistingView();
                }

            }

            void snapToTargetExistingView() {
                if (mLinearLayoutManager == null) {
                    return;
                }
                View snapView = mSnapHelper.findSnapView(mLinearLayoutManager);
                if (snapView == null) {
                    return;
                }
                int middle = (int) (getX() + getWidth() / 2);
                int childMiddle = (int) (snapView.getX() + snapView.getWidth() / 2);
                int gap = (middle - childMiddle);
                smoothScrollBy(gap, 0);
            }
        });
    }

    public interface BindDataListener<T> {
        void onBindData(com.mcxtzhang.commonadapter.rv.ViewHolder holder, T data);
    }

    BindDataListener mBindDataListener;

    public BindDataListener getBindDataListener() {
        return mBindDataListener;
    }

    public BaseLoopGallery setBindDataListener(BindDataListener bindDataListener) {
        mBindDataListener = bindDataListener;
        return this;
    }
}
