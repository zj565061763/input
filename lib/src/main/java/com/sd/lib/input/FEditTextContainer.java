package com.sd.lib.input;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FEditTextContainer extends FrameLayout
{
    private EditText mEditText;
    private boolean mHasInit = false;

    private final List<StateView> mListStateView = new CopyOnWriteArrayList<>();

    public FEditTextContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void addStateView(StateView stateView)
    {
        if (stateView == null || mListStateView.contains(stateView))
            return;

        mListStateView.add(stateView);
    }

    public void removeStateView(StateView stateView)
    {
        mListStateView.remove(stateView);
    }

    public void init()
    {
        if (mHasInit)
            return;

        final List<View> list = getAllViewsFrom(this);
        list.remove(this);

        if (mEditText == null)
            throw new RuntimeException("EditText was not found in " + this);
        else
            addOrRemoveStateView(list, true);

        mHasInit = true;
    }

    public void reset()
    {
        mListStateView.clear();
        mEditText = null;
        mViewListener.setView(null);
        mHasInit = false;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        init();
    }

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);

        if (mHasInit)
        {
            final List<View> list = getAllViewsFrom(child);
            addOrRemoveStateView(list, true);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (mHasInit)
        {
            final List<View> list = getAllViewsFrom(child);
            addOrRemoveStateView(list, false);
        }
    }

    private void addOrRemoveStateView(List<View> list, boolean add)
    {
        for (View item : list)
        {
            if (item instanceof FEditTextContainer)
            {
                throw new RuntimeException(FEditTextContainer.class.getSimpleName() + " is found in " + this);
            } else if (item instanceof StateView)
            {
                if (add)
                    addStateView((StateView) item);
                else
                    removeStateView((StateView) item);
            }
        }
    }

    private List<View> getAllViewsFrom(View view)
    {
        final List<View> list = new ArrayList<>();

        list.add(view);
        if (view instanceof ViewGroup)
        {
            final ViewGroup viewGroup = (ViewGroup) view;
            final int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++)
            {
                final View child = viewGroup.getChildAt(i);
                list.addAll(getAllViewsFrom(child));
            }
        } else if (view instanceof EditText)
        {
            saveEditText((EditText) view);
        }
        return list;
    }

    private void saveEditText(EditText editText)
    {
        if (mEditText == null)
        {
            mEditText = editText;
            mViewListener.setView(editText);
            mViewListener.start();
        } else
            throw new RuntimeException("EditText has been saved");
    }

    private final FViewListener<EditText> mViewListener = new FViewListener<EditText>()
    {
        @Override
        protected void onUpdate(EditText view)
        {
            for (StateView item : mListStateView)
            {
                item.onUpdate(mEditText);
            }
        }
    };

    public interface StateView
    {
        void onUpdate(EditText editText);
    }
}
