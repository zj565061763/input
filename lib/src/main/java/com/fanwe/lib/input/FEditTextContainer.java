package com.fanwe.lib.input;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class FEditTextContainer extends FrameLayout
{
    public FEditTextContainer(Context context)
    {
        super(context);
    }

    public FEditTextContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FEditTextContainer(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private FEditText mEditText;

    private void setEditText(FEditText editText)
    {
        if (mEditText == null)
            mEditText = editText;
        else
            throw new IllegalArgumentException("multi FEditText");
    }

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);

        if (mEditText != null)
        {
            final List<View> list = getAllViewsFrom(child);
            addOrRemoveStateView(list, true);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (mEditText != null)
        {
            final List<View> list = getAllViewsFrom(child);
            addOrRemoveStateView(list, false);
        }
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        final List<View> list = getAllViewsFrom(this);
        list.remove(this);

        if (mEditText == null)
            throw new RuntimeException(FEditText.class.getSimpleName() + " not found in " + this);
        else
            addOrRemoveStateView(list, true);
    }

    private void addOrRemoveStateView(List<View> list, boolean add)
    {
        for (View item : list)
        {
            if (item instanceof FEditTextContainer)
            {
                throw new RuntimeException(getClass().getSimpleName() + " is found in " + this);
            } else if (item instanceof FEditText.StateView)
            {
                final FEditText.StateView stateView = (FEditText.StateView) item;
                if (add)
                    mEditText.addStateView(stateView);
                else
                    mEditText.removeStateView(stateView);
            }
        }
    }

    private List<View> getAllViewsFrom(View view)
    {
        final List<View> list = new ArrayList<>();

        list.add(view);
        if (view instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) view;
            final int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++)
            {
                final View child = viewGroup.getChildAt(i);
                list.addAll(getAllViewsFrom(child));
            }
        } else if (view instanceof FEditText)
        {
            setEditText((FEditText) view);
        }
        return list;
    }
}
