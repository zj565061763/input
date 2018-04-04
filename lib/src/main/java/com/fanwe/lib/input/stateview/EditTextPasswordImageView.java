package com.fanwe.lib.input.stateview;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.lib.input.FStateEditText;
import com.fanwe.lib.input.R;

/**
 * Created by zhengjun on 2018/4/4.
 */
public class EditTextPasswordImageView extends ImageView implements FStateEditText.StateView
{
    public EditTextPasswordImageView(Context context)
    {
        super(context);
        init();
    }

    public EditTextPasswordImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public EditTextPasswordImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private EditText mEditText;
    private View.OnClickListener mOnClickListener;

    private void init()
    {
        if (getDrawable() == null)
        {
            setImageResource(R.drawable.lib_input_selector_edit_password);
        }

        super.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setSelected(!isSelected());
                updateInputType();

                if (mOnClickListener != null)
                {
                    mOnClickListener.onClick(v);
                }
            }
        });
    }

    private void updateInputType()
    {
        if (mEditText != null)
        {
            final int selection = mEditText.getSelectionEnd();
            if (isSelected())
            {
                mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else
            {
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            mEditText.setSelection(selection);
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener l)
    {
        mOnClickListener = l;
    }

    @Override
    public void onStateChanged(FStateEditText.ChangType type, EditText editText)
    {
        if (mEditText == null)
        {
            mEditText = editText;
            updateInputType();
        }

        if (type == FStateEditText.ChangType.Visibility)
        {
            if (editText.getVisibility() == View.VISIBLE)
            {
                setVisibility(VISIBLE);
            } else
            {
                setVisibility(GONE);
            }
        }
    }
}
