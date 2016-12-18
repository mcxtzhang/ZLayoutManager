package com.mcxtzhang.flowlayoutmanager.other;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mcxtzhang.flowlayoutmanager.R;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/28.
 */

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgViewHolder> {
    @DrawableRes
    public static final int[] IMAGES = new int[]{
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic8,
            R.drawable.pic9,
            R.drawable.pic10,
    };
    private final Context mContext;

    public class ImgViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public ImgViewHolder(ImageView imageView) {
            super(imageView);
            mImageView = imageView;
        }
    }

    public ImgAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                400,
                400
        );
        lp.leftMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        lp.rightMargin = 5;
        imageView.setLayoutParams(lp);
        return new ImgViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ImgViewHolder holder, int position) {
        holder.mImageView.setImageResource(IMAGES[position % IMAGES.length]);
    }

    @Override
    public int getItemCount() {
        return IMAGES.length * 10;
    }

}
