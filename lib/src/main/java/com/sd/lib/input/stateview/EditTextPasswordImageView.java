package com.sd.lib.input.stateview;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sd.lib.input.FEditText;
import com.sd.lib.input.R;

/**
 * 密码隐藏和明文切换
 */
public class EditTextPasswordImageView extends ImageView implements FEditText.StateView
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

    private EditText mEditText;
    private View.OnClickListener mOnClickListener;

    private void init()
    {
        if (getDrawable() == null)
            setImageResource(R.drawable.lib_input_selector_edit_password);

        super.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setSelected(!isSelected());
                updateInputType();

                if (mOnClickListener != null)
                    mOnClickListener.onClick(v);
            }
        });
    }

    private void updateInputType()
    {
        if (mEditText != null)
        {
            final int selection = mEditText.getSelectionEnd();

            if (isSelected())
                mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            else
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            mEditText.setSelection(selection);
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener l)
    {
        mOnClickListener = l;
    }

    @Override
    public void onStateChanged(FEditText.ChangType type, EditText editText)
    {
        if (mEditText != editText)
        {
            mEditText = editText;
            updateInputType();
        }

        if (type == FEditText.ChangType.Visibility)
            setVisibility(editText.getVisibility());
    }
}
