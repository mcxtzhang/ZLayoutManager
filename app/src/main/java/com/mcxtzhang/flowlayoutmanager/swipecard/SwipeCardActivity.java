package com.mcxtzhang.flowlayoutmanager.swipecard;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.flowlayoutmanager.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.mcxtzhang.flowlayoutmanager.swipecard.CardConfig.MAX_SHOW_COUNT;
import static com.mcxtzhang.flowlayoutmanager.swipecard.CardConfig.SCALE_GAP;
import static com.mcxtzhang.flowlayoutmanager.swipecard.CardConfig.TRANS_Y_GAP;

public class SwipeCardActivity extends AppCompatActivity {
    RecyclerView mRv;
    CommonAdapter<SwipeCardBean> mAdapter;
    List<SwipeCardBean> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new OverLayCardLayoutManager());
        mRv.setAdapter(mAdapter = new CommonAdapter<SwipeCardBean>(this, mDatas = SwipeCardBean.initDatas(), R.layout.item_swipe_card) {
            public static final String TAG = "zxt/Adapter";

            @Override
            public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
                Log.d(TAG, "convert() called with: viewHolder = [" + viewHolder + "], swipeCardBean = [" + swipeCardBean + "]");
                viewHolder.setText(R.id.tvName, swipeCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, swipeCardBean.getPostition() + " /" + mDatas.size());
                Picasso.with(SwipeCardActivity.this).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv));
            }
        });
        CardConfig.initConfig(this);


        final ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //水平方向是否可以被回收掉的阈值
            public float getThreshold(RecyclerView.ViewHolder viewHolder) {
                return mRv.getWidth() * getSwipeThreshold(viewHolder);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
                //rollBack(viewHolder);
                //★实现循环的要点
                SwipeCardBean remove = mDatas.remove(viewHolder.getLayoutPosition());
                mDatas.add(0, remove);
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                Log.e("swipecard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
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
/*                } else {
                    //系统复位的过程

                }*/

            }
        };
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);

    }


}
