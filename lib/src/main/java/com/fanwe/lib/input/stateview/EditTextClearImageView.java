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
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.lib.input.FEditText;
import com.fanwe.lib.input.R;

/**
 * 清除输入框内容
 */
public class EditTextClearImageView extends ImageView implements FEditText.StateView
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
    public void onStateChanged(FEditText.ChangType type, EditText editText)
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
