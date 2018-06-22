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
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.lib.input.FEditText;

/**
 * 焦点变化，切换选中状态
 */
public class EditTextFocusImageView extends ImageView implements FEditText.StateView
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
    public void onStateChanged(FEditText.ChangType type, EditText editText)
    {
        switch (type)
        {
            case Visibility:
                setVisibility(editText.getVisibility());
                break;
            case Focus:
                setSelected(editText.isFocused());
                break;
            default:
                break;
        }
    }
}
