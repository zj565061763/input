package com.sd.lib.input;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class FEditTextContainer extends FrameLayout
{
    private EditText mEditText;
    private final FEditTextStateListener mStateListener = new FEditTextStateListener();

    public FEditTextContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 初始化
     */
    public void init()
    {
        reset();

        final List<View> list = getAllViews(this);
        checkAndSaveEditText(list);

        if (mEditText == null)
            throw new RuntimeException("EditText was not found in " + this);

        addStateViewIfNeed(list);
    }

    /**
     * 重置，重置后需要重新初始化
     */
    public void reset()
    {
        mStateListener.clearStateCallback();
        mStateListener.stop();
        mEditText = null;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        init();
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (mEditText != null)
        {
            if (isAttached(mEditText))
            {
                final List<View> list = getAllViews(child);
                removeStateViewIfNeed(list);
            } else
            {
                reset();
            }
        }
    }

    private void addStateViewIfNeed(List<View> list)
    {
        for (View item : list)
        {
            if (item instanceof StateView)
                addStateView((StateView) item);
        }
    }

    private void removeStateViewIfNeed(List<View> list)
    {
        for (View item : list)
        {
            if (item instanceof StateView)
                removeStateView((StateView) item);
        }
    }

    private void addStateView(StateView stateView)
    {
        mStateListener.addStateCallback(stateView);
    }

    private void removeStateView(StateView stateView)
    {
        mStateListener.removeStateCallback(stateView);
    }

    private void checkAndSaveEditText(List<View> list)
    {
        for (View item : list)
        {
            if (item instanceof EditText)
            {
                saveEditText((EditText) item);
            } else if (item instanceof FEditTextContainer)
            {
                if (item != this)
                    throw new RuntimeException("Can not add FEditTextContainer to FEditTextContainer");
            }
        }
    }

    private void saveEditText(EditText editText)
    {
        if (editText == null)
            throw new IllegalArgumentException("editText is null when saveEditText()");

        if (mEditText == null)
        {
            mEditText = editText;
            mStateListener.start(editText);
        } else
        {
            if (mEditText != editText)
                throw new RuntimeException("EditText has been saved");
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mStateListener.start(mEditText);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mStateListener.stop();
    }

    private static List<View> getAllViews(View view)
    {
        if (view == null)
            throw new IllegalArgumentException("view is null when getAllViews()");

        final List<View> list = new ArrayList<>();

        list.add(view);
        if (view instanceof ViewGroup)
        {
            final ViewGroup viewGroup = (ViewGroup) view;
            final int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++)
            {
                final View child = viewGroup.getChildAt(i);
                if (child != null)
                    list.addAll(getAllViews(child));
            }
        }
        return list;
    }

    private static boolean isAttached(View view)
    {
        if (view == null)
            return false;

        if (Build.VERSION.SDK_INT >= 19)
            return view.isAttachedToWindow();
        else
            return view.getWindowToken() != null;
    }

    public interface StateView extends FEditTextStateListener.StateCallback
    {
    }
}
