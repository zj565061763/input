package com.sd.lib.input.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;

import com.sd.lib.input.FEditTextContainer;

/**
 * 焦点变化，切换选中状态
 */
public class EditTextFocusImageView extends ImageView implements FEditTextContainer.UpdateCallback
{
    public EditTextFocusImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void setVisibility(int visibility)
    {
        if (getVisibility() != visibility)
            super.setVisibility(visibility);
    }

    @Override
    public void setSelected(boolean selected)
    {
        if (isSelected() != selected)
            super.setSelected(selected);
    }

    @Override
    public void onUpdate(EditText editText)
    {
        setSelected(editText.isFocused());
        setVisibility(editText.getVisibility());
    }
}
