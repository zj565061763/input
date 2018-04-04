package com.fanwe.lib.input;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FStateEditTextContainer extends LinearLayout
{
    public FStateEditTextContainer(Context context)
    {
        super(context);
    }

    public FStateEditTextContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FStateEditTextContainer(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private FStateEditText mEditText;
    private boolean mHasFinishInflate;

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);

        if (mHasFinishInflate)
        {
            final List<View> list = getAllViewsFrom(child);
            addOrRemoveStateView(list, true);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        final List<View> list = getAllViewsFrom(child);
        addOrRemoveStateView(list, false);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        final List<View> list = getAllViewsFrom(this);
        list.remove(this);
        for (View item : list)
        {
            if (item instanceof FStateEditText)
            {
                mEditText = (FStateEditText) item;
                break;
            }
        }

        if (mEditText == null)
        {
            throw new RuntimeException(FStateEditText.class.getName() + " not found in " + this);
        } else
        {
            addOrRemoveStateView(list, true);
        }

        mHasFinishInflate = true;
    }

    private void addOrRemoveStateView(List<View> list, boolean add)
    {
        for (View item : list)
        {
            if (item instanceof FStateEditTextContainer)
            {
                throw new RuntimeException(getClass().getSimpleName() + " is found in " + this);
            } else if (item instanceof FStateEditText.StateView)
            {
                final FStateEditText.StateView stateView = (FStateEditText.StateView) item;
                if (add)
                {
                    mEditText.addStateView(stateView);
                } else
                {
                    mEditText.removeStateView(stateView);
                }
            }
        }
    }

    private static List<View> getAllViewsFrom(View view)
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
        }
        return list;
    }
}