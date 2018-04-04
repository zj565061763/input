package com.fanwe.lib.input;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjun on 2018/4/2.
 */
public class FStateEditText extends EditText implements TextWatcher
{
    public FStateEditText(Context context)
    {
        super(context);
        init();
    }

    public FStateEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FStateEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private final List<StateChangeCallback> mCallbackHolder = new ArrayList<>();

    private void init()
    {
        addTextChangedListener(this);
    }

    public final void addStateChangeCallback(StateChangeCallback callback)
    {
        if (callback == null || mCallbackHolder.contains(callback))
        {
            return;
        }
        mCallbackHolder.add(callback);
        callback.onStateChanged(ChangedType.Refresh, this);
    }

    public final void removeStateChangeCallback(StateChangeCallback callback)
    {
        mCallbackHolder.remove(callback);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        notifyStateChanged(ChangedType.Text);
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect)
    {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        notifyStateChanged(ChangedType.Focus);
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        notifyStateChanged(ChangedType.Enable);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this)
        {
            notifyStateChanged(ChangedType.Visibility);
        }
    }

    private void notifyStateChanged(ChangedType type)
    {
        if (mCallbackHolder == null || mCallbackHolder.isEmpty())
        {
            return;
        }

        for (StateChangeCallback item : mCallbackHolder)
        {
            item.onStateChanged(type, this);
        }
    }

    public interface StateChangeCallback
    {
        void onStateChanged(ChangedType type, EditText editText);
    }

    public enum ChangedType
    {
        /**
         * 用于通知刷新
         */
        Refresh,
        /**
         * 可见状态变化
         */
        Visibility,
        /**
         * 焦点状态变化
         */
        Focus,
        /**
         * 可用状态变化
         */
        Enable,
        /**
         * 文字内容变化
         */
        Text
    }
}
