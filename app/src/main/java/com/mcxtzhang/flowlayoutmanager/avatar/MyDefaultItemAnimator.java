/*
package com.mcxtzhang.flowlayoutmanager.avatar;

import android.animation.Animator;  
import android.animation.AnimatorListenerAdapter;  
import android.animation.AnimatorSet;  
import android.animation.ArgbEvaluator;  
import android.animation.ObjectAnimator;  
import android.graphics.Color;  
import android.graphics.drawable.ColorDrawable;  
import android.support.annotation.NonNull;  
import android.support.v4.util.ArrayMap;  
import android.support.v7.widget.DefaultItemAnimator;  
import android.support.v7.widget.RecyclerView;  
import android.util.Log;  
import android.view.View;  
import android.view.animation.AccelerateInterpolator;  
import android.view.animation.DecelerateInterpolator;  
import android.widget.LinearLayout;  
import android.widget.TextView;  
  
import java.util.List;  
  
*/
/**
 * Created by axing on 16/5/25. 
 *//*

public class MyDefaultItemAnimator extends DefaultItemAnimator {  
    private static final String TAG = "MyDefaultItemAnimator";  
  
    // 定义动画执行时的加速度  
    private AccelerateInterpolator mAccelerateInterpolator = new AccelerateInterpolator();  
    private DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();  
  
    ArgbEvaluator mColorEvaluator = new ArgbEvaluator();  
  
    */
/**
     * 定义正在执行Animator的ViewHolder的Map集合 
     * 此集合会保存用户点击的ViewHolder对象，目的在于当用户不停的点击某一item时 
     * 会先判断此ViewHolder种的itemView动画是否正在执行，如果正在执行则停止 
     *//*

    private ArrayMap<RecyclerView.ViewHolder, AnimatorInfo> mAnimatorMap = new ArrayMap<>();  
  
    */
/**
     * 复写canReuseUpdatedViewHolder方法并返回true，通知RecyclerView在执行动画时可以复用ViewHolder对象 
     * @param viewHolder 
     * @return 
     *//*

    @Override  
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {  
        return true;  
    }  
  
    */
/**
     * 自定义getItemHolderInfo方法，将ViewHolder中的背景颜色和TextView的文本信息传入ColorTextInfo中 
     * @param viewHolder 
     * @param info 
     * @return 
     *//*

    @NonNull  
    private ItemHolderInfo getItemHolderInfo(MyViewHolder viewHolder, ColorTextInfo info) {  
        //获取当前正在操作的ViewHolder对象  
        final MyViewHolder myHolder = viewHolder;  
        //获取ViewHolder中itemView背景颜色  
        final int bgColor = ((ColorDrawable) myHolder.container.getBackground()).getColor();  
        //将背景颜色和TextView的文本信息赋值给ColorTextInfo对象的color和text变量  
        info.color = bgColor;  
        info.text = (String) myHolder.textView.getText();  
        return info;  
    }  
  
    */
/**
     * 通过ViewHolder对象获取动画执行之前itemView中的背景颜色和文本信息 
     * 初始化ColorTextInfo对象，并将背景颜色和文本信息进行赋值 
     * @return 
     *//*

    @NonNull  
    @Override  
    public ItemHolderInfo recordPreLayoutInformation(RecyclerView.State state,  
                                                     RecyclerView.ViewHolder viewHolder, int changeFlags, List<Object> payloads) {  
        Log.e(TAG, "recordPreLayoutInformation: " + viewHolder.toString());  
        ColorTextInfo info = (ColorTextInfo) super.recordPreLayoutInformation(state, viewHolder,  
                changeFlags, payloads);  
        return getItemHolderInfo((MyViewHolder) viewHolder, info);  
    }  
  
    */
/**
     * 通过ViewHolder对象获取动画执行之后itemView中的背景颜色和文本信息 
     * 初始化ColorTextInfo对象，并将背景颜色和文本信息进行赋值 
     * @return 
     *//*

    @NonNull  
    @Override  
    public ItemHolderInfo recordPostLayoutInformation(@NonNull RecyclerView.State state,  
                                                      @NonNull RecyclerView.ViewHolder viewHolder) {  
        Log.e(TAG, "recordPostLayoutInformation: " + viewHolder.toString());  
        ColorTextInfo info = (ColorTextInfo) super.recordPostLayoutInformation(state, viewHolder);  
        return getItemHolderInfo((MyViewHolder) viewHolder, info);  
    }  
  
    */
/**
     * 复写obtainHolderInfo，返回自定义的ItemHolderInfo对象 
     * @return 
     *//*

    @Override  
    public ItemHolderInfo obtainHolderInfo() {  
        Log.e(TAG, "obtainHolderInfo: ");  
        return new ColorTextInfo();  
    }  
  
    */
/**
     * 自定义ItemHolderInfo对象，持有两个变量，依次来表示每一个Item的背景颜色和文本信息 
     *//*

    private class ColorTextInfo extends ItemHolderInfo {  
        int color;  
        String text;  
    }  
  
    */
/**
     * 创建执行animateChange的动画Info对象，内部封装了所需要执行一个动画类的相关信息 
     * 起始alpha属性动画，和起始旋转属性动画 
     *//*

    private class AnimatorInfo {  
        Animator overallAnim;  
        ObjectAnimator fadeToBlackAnim, fadeFromBlackAnim, oldTextRotator, newTextRotator;  
  
        public AnimatorInfo(Animator overallAnim,  
                            ObjectAnimator fadeToBlackAnim, ObjectAnimator fadeFromBlackAnim,  
                            ObjectAnimator oldTextRotator, ObjectAnimator newTextRotator) {  
            this.overallAnim = overallAnim;  
            this.fadeToBlackAnim = fadeToBlackAnim;  
            this.fadeFromBlackAnim = fadeFromBlackAnim;  
            this.oldTextRotator = oldTextRotator;  
            this.newTextRotator = newTextRotator;  
        }  
    }  
  
  
    */
/**
     * Custom change animation. Fade to black on the container background, then back 
     * up to the new bg coolor. Meanwhile, the text rotates, switching along the way. 
     * If a new change animation occurs on an item that is currently animating 
     * a change, we stop the previous change and start the new one where the old 
     * one left off. 
     * 真正的执行change动画的方法： 
     * 通过传入的preInfo和postInfo，分别将动画前后的背景色和文本信息设置到alpha属性动画和旋转属性动画中 
     *//*

    @Override  
    public boolean animateChange(@NonNull final RecyclerView.ViewHolder oldHolder,  
                                 @NonNull final RecyclerView.ViewHolder newHolder,  
                                 @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {  
        Log.e(TAG, "animateChange: ");  
  
        if (oldHolder != newHolder) {  
            //第一次显示所有的RecyclerView时，新旧ViewHolder是不相等的  
            return super.animateChange(oldHolder, newHolder, preInfo, postInfo);  
        }  
  
        final MyViewHolder viewHolder = (MyViewHolder) newHolder;  
  
        // 获取动画前后的背景色和文本信息  
        ColorTextInfo oldInfo = (ColorTextInfo) preInfo;  
        ColorTextInfo newInfo = (ColorTextInfo) postInfo;  
        int oldColor = oldInfo.color;  
        int newColor = newInfo.color;  
        final String oldText = oldInfo.text;  
        final String newText = newInfo.text;  
  
        // 获取需要被执行动画的View视图对象  
        LinearLayout newContainer = viewHolder.container;  
        final TextView newTextView = viewHolder.textView;  
  
        // 从mAnimatorMap缓存中查找当前newHolder对应的itemView动画是否在执行中，如果是则终止动画  
        AnimatorInfo runningInfo = mAnimatorMap.get(newHolder);  
        long prevAnimPlayTime = 0;  
        boolean firstHalf = false;  
        if (runningInfo != null) {  
            firstHalf = runningInfo.oldTextRotator != null &&  
                    runningInfo.oldTextRotator.isRunning();  
            prevAnimPlayTime = firstHalf ?  
                    runningInfo.oldTextRotator.getCurrentPlayTime() :  
                    runningInfo.newTextRotator.getCurrentPlayTime();  
            runningInfo.overallAnim.cancel();  
        }  
  
        // 初始化背景颜色渐变的属性动画  
        ObjectAnimator fadeToBlack = null, fadeFromBlack;  
        if (runningInfo == null || firstHalf) {  
            int startColor = oldColor;  
            if (runningInfo != null) {  
                startColor = (Integer) runningInfo.fadeToBlackAnim.getAnimatedValue();  
            }  
            fadeToBlack = ObjectAnimator.ofInt(newContainer, "backgroundColor",  
                    startColor, Color.BLACK);  
            fadeToBlack.setEvaluator(mColorEvaluator);  
            if (runningInfo != null) {  
                fadeToBlack.setCurrentPlayTime(prevAnimPlayTime);  
            }  
        }  
  
        fadeFromBlack = ObjectAnimator.ofInt(newContainer, "backgroundColor",  
                Color.BLACK, newColor);  
        fadeFromBlack.setEvaluator(mColorEvaluator);  
        if (runningInfo != null && !firstHalf) {  
            fadeFromBlack.setCurrentPlayTime(prevAnimPlayTime);  
        }  
  
        AnimatorSet bgAnim = new AnimatorSet();  
        if (fadeToBlack != null) {  
            bgAnim.playSequentially(fadeToBlack, fadeFromBlack);  
        } else {  
            bgAnim.play(fadeFromBlack);  
        }  
  
        // 初始化旋转的属性动画  
        ObjectAnimator oldTextRotate = null, newTextRotate;  
        if (runningInfo == null || firstHalf) {  
            oldTextRotate = ObjectAnimator.ofFloat(newTextView, View.ROTATION_X, 0, 90);  
            oldTextRotate.setInterpolator(mAccelerateInterpolator);  
            if (runningInfo != null) {  
                oldTextRotate.setCurrentPlayTime(prevAnimPlayTime);  
            }  
            oldTextRotate.addListener(new AnimatorListenerAdapter() {  
                boolean mCanceled = false;  
                @Override  
                public void onAnimationStart(Animator animation) {  
                    newTextView.setText(oldText);  
                }  
  
                @Override  
                public void onAnimationCancel(Animator animation) {  
                    mCanceled = true;  
                }  
  
                @Override  
                public void onAnimationEnd(Animator animation) {  
                    if (!mCanceled) {  
                        //old动画执行之后，需要重新设置文本信息  
                        newTextView.setText(newText);  
                    }  
                }  
            });  
        }  
  
        newTextRotate = ObjectAnimator.ofFloat(newTextView, View.ROTATION_X, -90, 0);  
        newTextRotate.setInterpolator(mDecelerateInterpolator);  
        if (runningInfo != null && !firstHalf) {  
            newTextRotate.setCurrentPlayTime(prevAnimPlayTime);  
        }  
  
        AnimatorSet textAnim = new AnimatorSet();  
        if (oldTextRotate != null) {  
            textAnim.playSequentially(oldTextRotate, newTextRotate);  
        } else {  
            textAnim.play(newTextRotate);  
        }  
  
        AnimatorSet changeAnim = new AnimatorSet();  
        changeAnim.playTogether(bgAnim, textAnim);  
        changeAnim.addListener(new AnimatorListenerAdapter() {  
            @Override  
            public void onAnimationEnd(Animator animation) {  
                dispatchAnimationFinished(newHolder);  
                mAnimatorMap.remove(newHolder);  
            }  
        });  
        changeAnim.start();  
  
        AnimatorInfo runningAnimInfo = new AnimatorInfo(changeAnim, fadeToBlack, fadeFromBlack,  
                oldTextRotate, newTextRotate);  
        mAnimatorMap.put(newHolder, runningAnimInfo);  
  
        return true;  
    }  
  
    @Override  
    public void endAnimation(RecyclerView.ViewHolder item) {  
        super.endAnimation(item);  
        if (!mAnimatorMap.isEmpty()) {  
            final int numRunning = mAnimatorMap.size();  
            for (int i = numRunning; i >= 0; i--) {  
                if (item == mAnimatorMap.keyAt(i)) {  
                    mAnimatorMap.valueAt(i).overallAnim.cancel();  
                }  
            }  
        }  
    }  
  
    @Override  
    public boolean isRunning() {  
        return super.isRunning() || !mAnimatorMap.isEmpty();  
    }  
  
    @Override  
    public void endAnimations() {  
        super.endAnimations();  
        if (!mAnimatorMap.isEmpty()) {  
            final int numRunning = mAnimatorMap.size();  
            for (int i = numRunning; i >= 0; i--) {  
                mAnimatorMap.valueAt(i).overallAnim.cancel();  
            }  
        }  
    }  
} */
