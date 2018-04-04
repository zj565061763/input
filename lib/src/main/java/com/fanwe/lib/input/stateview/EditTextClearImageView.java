package com.fanwe.lib.input.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.lib.input.FStateEditText;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class EditTextClearImageView extends ImageView implements FStateEditText.StateView
{
    public EditTextClearImageView(Context context)
    {
        super(context);
        init();
    }

    public EditTextClearImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public EditTextClearImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private EditText mEditText;
    private View.OnClickListener mOnClickListener;

    private void init()
    {
        super.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mEditText != null)
                {
                    mEditText.setText("");
                }
                if (mOnClickListener != null)
                {
                    mOnClickListener.onClick(v);
                }
            }
        });
    }

    @Override
    public void setOnClickListener(View.OnClickListener l)
    {
        mOnClickListener = l;
    }

    @Override
    public void onStateChanged(FStateEditText.ChangType type, EditText editText)
    {
        mEditText = editText;

        if (editText.getVisibility() == View.VISIBLE
                && editText.isFocused()
                && editText.isEnabled()
                && editText.getText().length() > 0)
        {
            setVisibility(VISIBLE);
        } else
        {
            setVisibility(GONE);
        }
    }
}
