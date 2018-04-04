package com.fanwe.lib.input.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.fanwe.lib.input.FStateEditText;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class FocusStateView extends View implements FStateEditText.StateView
{
    public FocusStateView(Context context)
    {
        super(context);
    }

    public FocusStateView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FocusStateView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onStateChanged(FStateEditText.ChangType type, EditText editText)
    {
        if (type == FStateEditText.ChangType.Focus)
        {
            setSelected(editText.isFocused());
        }
    }
}
