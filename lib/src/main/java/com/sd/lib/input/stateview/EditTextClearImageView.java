package com.sd.lib.input.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sd.lib.input.FEditTextContainer;
import com.sd.lib.input.R;

/**
 * 清除输入框内容
 */
public class EditTextClearImageView extends ImageView implements FEditTextContainer.UpdateCallback
{
    public EditTextClearImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private EditText mEditText;
    private View.OnClickListener mOnClickListener;

    private void init()
    {
        if (getDrawable() == null)
            setImageResource(R.drawable.lib_input_selector_edit_clear);

        super.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mEditText != null)
                    mEditText.setText("");

                if (mOnClickListener != null)
                    mOnClickListener.onClick(v);
            }
        });
    }

    @Override
    public void setOnClickListener(View.OnClickListener l)
    {
        mOnClickListener = l;
    }

    @Override
    public void setVisibility(int visibility)
    {
        if (getVisibility() != visibility)
            super.setVisibility(visibility);
    }

    @Override
    public void onUpdate(EditText editText)
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
