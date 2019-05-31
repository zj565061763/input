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
    private final List<StateView> mListStateView = new CopyOnWriteArrayList<>();

    public FEditTextContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 添加{@link StateView}
     *
     * @param stateView
     */
    public void addStateView(StateView stateView)
    {
        if (stateView == null || mListStateView.contains(stateView))
            return;

        mListStateView.add(stateView);
    }

    /**
     * 移除{@link StateView}
     *
     * @param stateView
     */
    public void removeStateView(StateView stateView)
    {
        mListStateView.remove(stateView);
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

        addOrRemoveStateView(list, true);
    }

    /**
     * 重置，重置后需要重新初始化
     */
    public void reset()
    {
        mListStateView.clear();
        mViewListener.setView(null);
        mEditText = null;
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

        if (mEditText == null)
        {
            init();
        } else
        {
            final List<View> list = getAllViews(child);
            checkAndSaveEditText(list);
            addOrRemoveStateView(list, true);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (mEditText != null)
        {
            final List<View> list = getAllViews(child);
            if (resetIfNeed(list))
            {
                if (mEditText != null)
                    throw new RuntimeException("EditText is not null after reset()");

                // 已经被重置，不做任何处理
            } else
            {
                addOrRemoveStateView(list, false);
            }
        }
    }

    private void addOrRemoveStateView(List<View> list, boolean add)
    {
        for (View item : list)
        {
            if (item instanceof StateView)
            {
                if (add)
                    addStateView((StateView) item);
                else
                    removeStateView((StateView) item);
            }
        }
    }

    private boolean resetIfNeed(List<View> list)
    {
        for (View item : list)
        {
            if (mEditText != null && mEditText == item)
            {
                reset();
                return true;
            }
        }
        return false;
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
            mViewListener.setView(editText);
            mViewListener.start();
        } else
        {
            if (mEditText != editText)
                throw new RuntimeException("EditText has been saved");
        }
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
                list.addAll(getAllViews(child));
            }
        }
        return list;
    }

    public interface StateView
    {
        void onUpdate(EditText editText);
    }
}
