package com.sd.lib.input.stateview

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import com.sd.lib.input.FEditTextContainer
import com.sd.lib.input.R

/**
 * 密码隐藏和明文切换
 */
class EditTextPasswordImageView : ImageView, FEditTextContainer.StateView {
    private var _editText: EditText? = null
    private var _onClickListener: OnClickListener? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (drawable == null) {
            setImageResource(R.drawable.lib_input_selector_edit_password)
        }

        super.setOnClickListener { v ->
            isSelected = !isSelected
            updateInputType()
            _onClickListener?.onClick(v)
        }
    }

    override fun setOnClickListener(l: OnClickListener) {
        _onClickListener = l
    }

    override fun setVisibility(visibility: Int) {
        if (getVisibility() != visibility) {
            super.setVisibility(visibility)
        }
    }

    override fun onUpdate(editText: EditText) {
        if (_editText !== editText) {
            _editText = editText
            updateInputType()
        }
        visibility = editText.visibility
    }

    private fun updateInputType() {
        val editText = _editText ?: return

        val selection = editText.selectionEnd
        if (isSelected) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        editText.setSelection(selection)
    }
}