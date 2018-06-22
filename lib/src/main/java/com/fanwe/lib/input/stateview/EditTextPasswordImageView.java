/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.input.stateview;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.lib.input.FEditText;
import com.fanwe.lib.input.R;

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
