package com.mcxtzhang.flowlayoutmanager.gallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mcxtzhang.flowlayoutmanager.R;
import com.mcxtzhang.layoutmanager.gallery.BaseLoopGallery;
import com.squareup.picasso.Picasso;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/7/4.
 * History:
 */

public class AlyLoopGallery extends BaseLoopGallery<String> {
    public AlyLoopGallery(Context context) {
        super(context);
    }

    public AlyLoopGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlyLoopGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onBindData(com.mcxtzhang.commonadapter.rv.ViewHolder holder, String data) {
        Picasso.with(holder.itemView.getContext())
                .load(data)
                .into((ImageView) holder.getView(R.id.image));
    }
}
