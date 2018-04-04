package com.fanwe.lib.input.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.lib.input.FStateEditText;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class EditTextFocusImageView extends ImageView implements FStateEditText.StateView
{
    public EditTextFocusImageView(Context context)
    {
        super(context);
    }

    public EditTextFocusImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public EditTextFocusImageView(Context context, AttributeSet attrs, int defStyleAttr)
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
