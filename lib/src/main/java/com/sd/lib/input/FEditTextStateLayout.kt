package com.sd.lib.input

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout

class FEditTextStateLayout : LinearLayout {
    private val _stateListener = FEditTextStateListener()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    /**
     * 初始化
     */
    fun init(editText: EditText?) {
        _stateListener.clearStateCallback()

        if (editText != null) {
            val list = getAllViews(this)
            list.forEach {
                if (it is FEditTextContainer.StateView) {
                    _stateListener.addStateCallback(it)
                }
            }
        }

        _stateListener.start(editText)
    }

    companion object {
        private fun getAllViews(view: View): List<View> {
            val list = mutableListOf<View>()
            list.add(view)

            if (view is ViewGroup) {
                val count = view.childCount
                for (i in 0 until count) {
                    val child = view.getChildAt(i)
                    if (child != null) {
                        list.addAll(getAllViews(child))
                    }
                }
            }
            return list
        }
    }
}