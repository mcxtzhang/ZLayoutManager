package com.mcxtzhang.flowlayoutmanager.avatar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.flowlayoutmanager.R;
import com.mcxtzhang.flowlayoutmanager.swipecard.SwipeCardBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TanTanAvatarActivity extends AppCompatActivity {
    RecyclerView mRv;
    CommonAdapter<SwipeCardBean> mAdapter;
    List<SwipeCardBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_tan_avatar);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = mRv.getLayoutParams();
                layoutParams.height = getResources().getDisplayMetrics().widthPixels;
                mRv.setLayoutParams(layoutParams);
            }
        });
        mRv.setLayoutManager(new AvatarLayoutManager());
        mRv.setAdapter(mAdapter = new CommonAdapter<SwipeCardBean>(this, mDatas = SwipeCardBean.initDatas(), R.layout.item_avatar) {
            @Override
            public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
                Picasso.with(TanTanAvatarActivity.this).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv));
            }
        });
    }
}
