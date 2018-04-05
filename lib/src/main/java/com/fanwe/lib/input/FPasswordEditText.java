package com.fanwe.lib.input;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class FPasswordEditText extends FDrawableEditText implements FEditText.StateView
{
    public FPasswordEditText(Context context)
    {
        super(context);
        init();
    }

    public FPasswordEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FPasswordEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 是否显示密码
     */
    private boolean mIsPasswordVisible = false;

    private Drawable mDrawablePasswordVisible;
    private Drawable mDrawablePasswordInvisible;

    private void init()
    {
        addStateView(this);

        setDrawablePasswordVisible(getResources().getDrawable(R.drawable.lib_input_ic_edit_password_visible));
        setDrawablePasswordInvisible(getResources().getDrawable(R.drawable.lib_input_ic_edit_password_invisible));

        final float scale = getResources().getDisplayMetrics().density;
        getDrawableConfigRight().setWidth((int) (18 * scale));

        updateInputType();
    }

    /**
     * 设置密码可见状态的图片
     *
     * @param drawable
     */
    public void setDrawablePasswordVisible(Drawable drawable)
    {
        mDrawablePasswordVisible = drawable;
        onStateChanged(ChangType.Refresh, this);
    }

    /**
     * 设置密码不可见状态的图片
     *
     * @param drawable
     */
    public void setDrawablePasswordInvisible(Drawable drawable)
    {
        mDrawablePasswordInvisible = drawable;
        onStateChanged(ChangType.Refresh, this);
    }

    public boolean isPasswordVisible()
    {
        return mIsPasswordVisible;
    }

    @Override
    public void onStateChanged(ChangType type, EditText editText)
    {
        Drawable drawable = null;

        if (mIsPasswordVisible)
        {
            drawable = mDrawablePasswordVisible;
        } else
        {
            drawable = mDrawablePasswordInvisible;
        }

        setDrawableRight(drawable);
    }

    @Override
    protected void onClickDrawableRight()
    {
        super.onClickDrawableRight();

        mIsPasswordVisible = !mIsPasswordVisible;

        onStateChanged(ChangType.Refresh, this);
        updateInputType();
    }

    private void updateInputType()
    {
        final int selection = getSelectionEnd();
        if (mIsPasswordVisible)
        {
            setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else
        {
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        setSelection(selection);
    }
}
