package com.fanwe.lib.input.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class DrawableConfig
{
    private Drawable drawable;
    private int width;
    private final int[] size = new int[2];

    public void updateBounds()
    {
        if (drawable == null)
        {
            return;
        }

        scaleDrawableIfNeed();

        int left = 0;
        int top = 0;
        int right = left + size[0];
        int bottom = top + size[1];

        drawable.setBounds(left, top, right, bottom);
    }

    private void scaleDrawableIfNeed()
    {
        size[0] = drawable.getIntrinsicWidth();
        size[1] = drawable.getIntrinsicHeight();
        if (width > 0 && width != size[0])
        {
            size[1] = getScaledHeight(width, drawable);
            size[0] = width;
        }
    }

    private static int getScaledHeight(int width, Drawable drawable)
    {
        return (int) (width * drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
    }

    public Drawable getDrawable()
    {
        return drawable;
    }

    public void setDrawable(Drawable drawable)
    {
        this.drawable = drawable;
        updateBounds();
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
        updateBounds();
    }
}
