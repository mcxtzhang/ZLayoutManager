package com.mcxtzhang.flowlayoutmanager.zuimei;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.flowlayoutmanager.R;
import com.mcxtzhang.flowlayoutmanager.swipecard.SwipeCardBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.mcxtzhang.flowlayoutmanager.swipecard.SwipeCardBean.initDatas;

public class ScaleCardActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private CommonAdapter<SwipeCardBean> mAdapter;
    private List<SwipeCardBean> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_card);

        mRv = (RecyclerView) findViewById(R.id.rv);

        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            mDatas.addAll(initDatas());
        }

        mRv.setAdapter(mAdapter = new CommonAdapter<SwipeCardBean>(this, mDatas, R.layout.item_zuimei) {
            public static final String TAG = "zxt/Adapter";

            @Override
            public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
                Log.d(TAG, "convert() called with: viewHolder = [" + viewHolder + "], swipeCardBean = [" + swipeCardBean + "]");
                viewHolder.setText(R.id.tvName, swipeCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, swipeCardBean.getPostition() + " /" + mDatas.size());
                Picasso.with(ScaleCardActivity.this).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv));
            }
        });

        mRv.setLayoutManager(new ScaleCardLayoutManager());



/*        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int childCount = recyclerView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = recyclerView.getChildAt(i);
                    float distance = child.getY() - recyclerView.getPaddingBottom();
                    int horizontalSpace = recyclerView.getHeight() - recyclerView.getPaddingTop() - recyclerView.getPaddingBottom();
                    //0.8f-1.0f
                    child.setScaleX(0.8f + distance / horizontalSpace * 0.2f);
                    Log.d("zxt", "onScrolled() called with: distance = [" + distance + "], horizontalSpace = [" + horizontalSpace + "], i = [" + i + "]");
                    Log.d("zxt", "onScrolled() called with: getY = [" + child.getY() + "], getTop [" + child.getTop() + "], getTranslationY = [" + child.getTranslationY() + "]");
                }
            }
        });*/
/*        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRv);*/
    }
}
