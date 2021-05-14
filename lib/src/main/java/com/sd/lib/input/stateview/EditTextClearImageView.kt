package com.sd.lib.input.stateview

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import com.sd.lib.input.FEditTextContainer
import com.sd.lib.input.R

/**
 * 清除输入框内容
 */
class EditTextClearImageView : ImageView, FEditTextContainer.StateView {
    private var _editText: EditText? = null
    private var _onClickListener: OnClickListener? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (drawable == null) {
            setImageResource(R.drawable.lib_input_selector_edit_clear)
        }

        super.setOnClickListener { v ->
            _editText?.setText("")
            _onClickListener?.onClick(v)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        _onClickListener = l
    }

    override fun setVisibility(visibility: Int) {
        if (this.visibility != visibility) {
            super.setVisibility(visibility)
        }
    }

    override fun onUpdate(editText: EditText) {
        _editText = editText
        if (editText.visibility == VISIBLE &&
            editText.isFocused &&
            editText.isEnabled &&
            editText.text.isNotEmpty()
        ) {
            this.visibility = VISIBLE
        } else {
            this.visibility = GONE
        }
    }
}