package com.sd.lib.input

import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.EditText
import java.util.concurrent.ConcurrentHashMap

class FEditTextStateListener {
    private val _editText: EditText
    private val _mapStateCallback = ConcurrentHashMap<StateCallback, String>()

    private val _onPreDrawListener: OnPreDrawListener
    private var _isStarted = false

    constructor(editText: EditText) {
        _editText = editText
        _onPreDrawListener = OnPreDrawListener {
            notifyCallback()
            true
        }
    }

    /**
     * 添加状态回调
     */
    @Synchronized
    fun addStateCallback(callback: StateCallback) {
        _mapStateCallback[callback] = ""
    }

    /**
     * 移除状态回调
     */
    @Synchronized
    fun removeStateCallback(callback: StateCallback?) {
        if (callback != null) {
            _mapStateCallback.remove(callback)
        }
    }

    /**
     * 开始监听
     */
    @Synchronized
    fun start(): Boolean {
        val observer = _editText.viewTreeObserver
        if (observer.isAlive) {
            observer.removeOnPreDrawListener(_onPreDrawListener)
            observer.addOnPreDrawListener(_onPreDrawListener)
            _isStarted = true
            return true
        }
        return false
    }

    /**
     * 停止监听
     */
    @Synchronized
    fun stop() {
        val observer = _editText.viewTreeObserver
        if (observer.isAlive) {
            observer.addOnPreDrawListener(_onPreDrawListener)
        }
        _isStarted = false
    }

    @Synchronized
    private fun notifyCallback() {
        if (!_isStarted) return

        for (item in _mapStateCallback.keys) {
            item.onUpdate(_editText)
        }
    }

    interface StateCallback {
        fun onUpdate(editText: EditText)
    }
}