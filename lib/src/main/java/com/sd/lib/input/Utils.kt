package com.sd.lib.input

import android.os.Build
import android.view.View
import android.view.ViewGroup

internal object Utils {
    fun getAllViews(view: View): List<View> {
        val list = mutableListOf(view)
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

    fun isAttached(view: View): Boolean {
        return if (Build.VERSION.SDK_INT >= 19) {
            view.isAttachedToWindow
        } else {
            view.windowToken != null
        }
    }
}