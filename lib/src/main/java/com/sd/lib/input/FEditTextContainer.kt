package com.sd.lib.input

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout

class FEditTextContainer : FrameLayout {
    private val _stateListener = FEditTextStateListener()
    private var _editText: EditText? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    /**
     * 初始化
     */
    @Synchronized
    fun init() {
        reset()
        val list = getAllViews(this)

        list.forEach {
            if (it is EditText) {
                saveEditText(it)
            } else if (it is FEditTextContainer) {
                if (it !== this) {
                    throw RuntimeException("Can not add FEditTextContainer to FEditTextContainer")
                }
            }
        }

        if (_editText == null) {
            throw RuntimeException("EditText was not found in $this")
        }

        list.forEach {
            if (it is StateView) {
                _stateListener.addStateCallback(it)
            }
        }
    }

    /**
     * 重置，重置后需要重新初始化
     */
    @Synchronized
    fun reset() {
        _stateListener.clearStateCallback()
        _stateListener.stop()
        _editText = null
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        _editText?.let {
            if (!isAttached(it)) {
                // 如果EditText被移除，则重置
                reset()
            }
        }
    }

    private fun saveEditText(editText: EditText) {
        if (_editText == null) {
            _editText = editText
            _stateListener.start(editText)
        } else {
            if (_editText !== editText) {
                throw RuntimeException("EditText has been saved")
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        _stateListener.start(_editText)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _stateListener.stop()
    }

    interface StateView : FEditTextStateListener.StateCallback

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

        private fun isAttached(view: View): Boolean {
            return if (Build.VERSION.SDK_INT >= 19) {
                view.isAttachedToWindow
            } else {
                view.windowToken != null
            }
        }
    }
}