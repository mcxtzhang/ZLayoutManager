package com.mcxtzhang.flowlayoutmanager.tantan;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.flowlayoutmanager.R;
import com.mcxtzhang.flowlayoutmanager.swipecard.CardConfig;
import com.mcxtzhang.flowlayoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.flowlayoutmanager.swipecard.SwipeCardBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.mcxtzhang.flowlayoutmanager.swipecard.CardConfig.MAX_SHOW_COUNT;
import static com.mcxtzhang.flowlayoutmanager.swipecard.CardConfig.SCALE_GAP;
import static com.mcxtzhang.flowlayoutmanager.swipecard.CardConfig.TRANS_Y_GAP;


public class TanTanActivity extends AppCompatActivity {
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
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
                super.onBindViewHolder(holder, position);
            }

            @Override
            public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
                Log.d(TAG, "convert() called with: viewHolder = [" + viewHolder + "], swipeCardBean = [" + swipeCardBean + "]");
                viewHolder.setText(R.id.tvName, swipeCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, swipeCardBean.getPostition() + " /" + mDatas.size());
                Picasso.with(TanTanActivity.this).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv));
            }
        });
        CardConfig.initConfig(this);

        //探探上下滑是不能删除的，所以只传入左右即可
        final ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0,
               /* ItemTouchHelper.DOWN | ItemTouchHelper.UP |*/ ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            private static final int MAX_ROTATION = 15;


            //是否可以被回收掉的阈值
            public float getThreshold(RecyclerView.ViewHolder viewHolder) {
                return mRv.getWidth() * getSwipeThreshold(viewHolder);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //Drag&Drop 效果会用到
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //ItemTouchHelper帮我们执行了滑动删除操作后会用到
                Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
                mAdapter.notifyDataSetChanged();
                SwipeCardBean remove = mDatas.remove(viewHolder.getLayoutPosition());
                mDatas.add(0, remove);


                //探探只是第一层加了rotate & alpha的操作
                //对rotate进行复位
                viewHolder.itemView.setRotation(0);
                //自己感受一下吧 Alpha
                if (viewHolder instanceof ViewHolder) {
                    ViewHolder holder = (ViewHolder) viewHolder;
                    holder.setAlpha(R.id.iv_love, 0);
                    holder.setAlpha(R.id.iv_del, 0);
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                Log.e("swipecard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
                //探探的效果
                double swipValue = Math.sqrt(dX * dX + dY * dY);
                double fraction = swipValue / getThreshold(viewHolder);
                //边界修正 最大为1
                if (fraction > 1) {
                    fraction = 1;
                }
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
                    } else {
                        //探探只是第一层加了rotate & alpha的操作
                        //不过他区分左右
                        float xFraction = dX / getThreshold(viewHolder);
                        //边界修正 最大为1
                        if (xFraction > 1) {
                            xFraction = 1;
                        } else if (xFraction < -1) {
                            xFraction = -1;
                        }
                        //rotate
                        child.setRotation(xFraction * MAX_ROTATION);

                        //自己感受一下吧 Alpha
                        if (viewHolder instanceof ViewHolder) {
                            ViewHolder holder = (ViewHolder) viewHolder;
                            if (dX > 0) {
                                //露出左边，比心
                                holder.setAlpha(R.id.iv_love, xFraction);
                            } else {
                                //露出右边，滚犊子
                                holder.setAlpha(R.id.iv_del, -xFraction);
                            }


                        }
                    }
                }
            }
        };
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);

    }


}

