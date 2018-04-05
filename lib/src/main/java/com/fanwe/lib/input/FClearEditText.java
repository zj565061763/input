package com.fanwe.lib.input;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class FClearEditText extends FDrawableEditText implements FEditText.StateView
{
    public FClearEditText(Context context)
    {
        super(context);
        init();
    }

    public FClearEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FClearEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Drawable mDrawableClear;

    private void init()
    {
        addStateView(this);
        setDrawableClear(getResources().getDrawable(R.drawable.lib_input_selector_edit_clear));

        final float scale = getResources().getDisplayMetrics().density;
        getDrawableConfigRight().setWidth((int) (16 * scale));
    }

    /**
     * 设置清空内容图片
     *
     * @param drawableClear
     */
    public void setDrawableClear(Drawable drawableClear)
    {
        mDrawableClear = drawableClear;
        onStateChanged(ChangType.Refresh, this);
    }

    @Override
    public void onStateChanged(ChangType type, EditText editText)
    {
        Drawable drawable = null;

        if (isFocused() && isEnabled() && getText().length() > 0)
        {
            drawable = mDrawableClear;
        }

        setDrawableRight(drawable);
    }

    @Override
    protected void onClickDrawableRight()
    {
        super.onClickDrawableRight();
        setText("");
    }
}
