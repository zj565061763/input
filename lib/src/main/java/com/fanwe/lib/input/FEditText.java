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
public class FEditText extends EditText
{
    public FEditText(Context context)
    {
        super(context);
        init();
    }

    public FEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private final List<StateView> mStateViewHolder = new ArrayList<>(1);

    private void init()
    {
        addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                notifyStateChanged(ChangType.Text);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    public final void addStateView(StateView stateView)
    {
        if (stateView == null || mStateViewHolder.contains(stateView))
            return;

        mStateViewHolder.add(stateView);
        stateView.onStateChanged(ChangType.Refresh, this);
    }

    public final void removeStateView(StateView stateView)
    {
        mStateViewHolder.remove(stateView);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect)
    {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        notifyStateChanged(ChangType.Focus);
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        notifyStateChanged(ChangType.Enable);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);

        if (changedView == this)
            notifyStateChanged(ChangType.Visibility);
    }

    private void notifyStateChanged(ChangType type)
    {
        if (mStateViewHolder == null || mStateViewHolder.isEmpty())
            return;

        for (StateView item : mStateViewHolder)
        {
            item.onStateChanged(type, this);
        }
    }

    public interface StateView
    {
        void onStateChanged(ChangType type, EditText editText);
    }

    public enum ChangType
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
