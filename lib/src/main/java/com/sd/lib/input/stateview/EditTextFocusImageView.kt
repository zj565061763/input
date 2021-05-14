package com.sd.lib.input.stateview

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import com.sd.lib.input.FEditTextContainer

/**
 * 焦点变化，切换选中状态
 */
class EditTextFocusImageView : ImageView, FEditTextContainer.StateView {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun setVisibility(visibility: Int) {
        if (this.visibility != visibility) {
            super.setVisibility(visibility)
        }
    }

    override fun setSelected(selected: Boolean) {
        if (this.isSelected != selected) {
            super.setSelected(selected)
        }
    }

    override fun onUpdate(editText: EditText) {
        this.isSelected = editText.isFocused
        this.visibility = editText.visibility
    }
}