package com.mcxtzhang.flowlayoutmanager.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的RecyclerView 的Adapter
 * Created by zhangxutong .
 * Date: 16/03/11
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected ViewGroup mRv;//add by zhangxutong 2016 08 05 ,for 点击事件为了兼容HeaderView FooterView 的Adapter

    private OnItemClickListener mOnItemClickListener;

    public CommonAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.d("zxt", "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        //add by zhangxutong 2016 08 05 begin ,for 点击事件为了兼容HeaderView FooterView 的Adapter
        if (null == mRv) {
            mRv = parent;
        }
        //setListener(parent, viewHolder, viewType);
        //add by zhangxutong 2016 08 05 end ,for 点击事件为了兼容HeaderView FooterView 的Adapter
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    /**
     * 在onCreateHolder里调用的，但是在增加了HeaderFooter后，postion位置，会不正确。
     * 所以如果使用了{@link HeaderAndFooterWrapperAdapter},建议使用 {@link #setListener(int, ViewHolder)} 这个方法，返回的位置是正确的。
     *
     * @param parent
     * @param viewHolder
     * @param viewType
     */
    @Deprecated
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Log.d("zxt", "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
        holder.updatePosition(position);
        //add by zhangxutong 2016 08 05 begin 点击事件为了兼容HeaderView FooterView 的Adapter，所以在OnBindViewHolder里，其实性能没有onCreate好
        setListener(position, holder);
        //add by zhangxutong 2016 08 05 end
        convert(holder, mDatas.get(position));
    }

    //add by zhangxutong 2016 08 05 begin 点击事件为了兼容HeaderView FooterView 的Adapter，所以在OnBindViewHolder里，其实性能没有onCreate好
    protected void setListener(final int position, final ViewHolder viewHolder) {
        if (!isEnabled(getItemViewType(position))) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mRv, v, mDatas.get(position), position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(mRv, v, mDatas.get(position), position);
                }
                return false;
            }
        });
    }

    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }


    /**
     * 刷新数据，初始化数据
     *
     * @param list
     */
    public void setDatas(List<T> list) {
        if (this.mDatas != null) {
            if (null != list) {
                List<T> temp = new ArrayList<>();
                temp.addAll(list);
                this.mDatas.clear();
                this.mDatas.addAll(temp);
            } else {
                this.mDatas.clear();
            }
        } else {
            this.mDatas = list;
        }
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     *
     * @param i
     */
    public void remove(int i) {
        if (null != mDatas && mDatas.size() > i && i > -1) {
            mDatas.remove(i);
            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多数据
     *
     * @param list
     */
    public void addDatas(List<T> list) {
        if (null != list) {
            List<T> temp = new ArrayList<>();
            temp.addAll(list);
            if (this.mDatas != null) {
                this.mDatas.addAll(temp);
            } else {
                this.mDatas = temp;
            }
            notifyDataSetChanged();
        }

    }


    public List<T> getDatas() {
        return mDatas;
    }


    public T getItem(int position) {
        if (position > -1 && null != mDatas && mDatas.size() > position) {
            return mDatas.get(position);
        }
        return null;
    }
}
