package com.fanwe.lib.input.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class DrawableConfig
{
    private Drawable mDrawable;
    private int mWidth;
    private final int[] mSize = new int[2];

    public void updateBounds()
    {
        if (mDrawable == null)
        {
            return;
        }

        scaleDrawableIfNeed();

        int left = 0;
        int top = 0;
        int right = left + mSize[0];
        int bottom = top + mSize[1];

        mDrawable.setBounds(left, top, right, bottom);
    }

    private void scaleDrawableIfNeed()
    {
        mSize[0] = mDrawable.getIntrinsicWidth();
        mSize[1] = mDrawable.getIntrinsicHeight();
        if (mWidth > 0 && mWidth != mSize[0])
        {
            mSize[1] = getScaledHeight(mWidth, mDrawable);
            mSize[0] = mWidth;
        }
    }

    private static int getScaledHeight(int width, Drawable drawable)
    {
        return (int) (width * drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
    }

    public void setDrawable(Drawable drawable)
    {
        this.mDrawable = drawable;
        updateBounds();
    }

    public void setWidth(int width)
    {
        this.mWidth = width;
        updateBounds();
    }
}
