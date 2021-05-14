package com.sd.lib.input

import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.EditText
import java.util.concurrent.ConcurrentHashMap

class FEditTextStateListener {
    private val _mapStateCallback = ConcurrentHashMap<StateCallback, String>()
    private var _isStarted = false
    private var _editText: EditText? = null

    /**
     * 添加状态回调
     */
    fun addStateCallback(callback: StateCallback) {
        _mapStateCallback.put(callback, "")
    }

    /**
     * 移除状态回调
     */
    fun removeStateCallback(callback: StateCallback?) {
        if (callback != null) {
            _mapStateCallback.remove(callback)
        }
    }

    /**
     * 清空状态回调
     */
    fun clearStateCallback() {
        _mapStateCallback.clear()
    }

    /**
     * 开始监听
     */
    @Synchronized
    fun start(editText: EditText?): Boolean {
        if (_editText === editText) {
            return false
        }

        if (editText == null) {
            stop()
            return false
        }

        val observer = editText.viewTreeObserver
        if (!observer.isAlive) {
            return false
        }

        _editText = editText
        _isStarted = true

        observer.addOnPreDrawListener(_onPreDrawListener)
        return true
    }

    /**
     * 停止监听
     */
    @Synchronized
    fun stop() {
        _editText?.let {
            val observer = it.viewTreeObserver
            if (observer.isAlive) {
                observer.removeOnPreDrawListener(_onPreDrawListener)
            }
        }

        _editText = null
        _isStarted = false
    }

    private val _onPreDrawListener = OnPreDrawListener {
        notifyCallback()
        true
    }

    @Synchronized
    private fun notifyCallback() {
        if (!_isStarted) return
        val editText = _editText ?: return

        for (item in _mapStateCallback.keys) {
            item.onUpdate(editText)
        }
    }

    interface StateCallback {
        fun onUpdate(editText: EditText)
    }
}