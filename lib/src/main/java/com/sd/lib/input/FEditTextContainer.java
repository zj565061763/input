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
    public FEditTextContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private EditText mEditText;
    private boolean mHasInit = false;

    private List<UpdateCallback> mListCallback = new CopyOnWriteArrayList<>();

    public void addCallback(UpdateCallback callback)
    {
        if (callback == null || mListCallback.contains(callback))
            return;

        mListCallback.add(callback);
    }

    public void removeCallback(UpdateCallback callback)
    {
        mListCallback.remove(callback);
    }

    public synchronized void init()
    {
        if (mHasInit)
            return;

        final List<View> list = getAllViewsFrom(this);
        list.remove(this);

        if (mEditText == null)
            throw new RuntimeException("EditText was not found in " + this);
        else
            addOrRemoveCallback(list, true);

        mHasInit = true;
    }

    private void setEditText(EditText editText)
    {
        if (mEditText == null)
        {
            mEditText = editText;
            mViewListener.setView(editText);
            mViewListener.start(true);
        } else
            throw new RuntimeException("EditText has been specified");
    }

    private final FViewListener<EditText> mViewListener = new FViewListener<EditText>()
    {
        @Override
        protected void onUpdate(EditText view)
        {
            for (UpdateCallback item : mListCallback)
            {
                item.onUpdate(mEditText);
            }
        }
    };

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);

        if (mHasInit)
        {
            final List<View> list = getAllViewsFrom(child);
            addOrRemoveCallback(list, true);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (mHasInit)
        {
            final List<View> list = getAllViewsFrom(child);
            addOrRemoveCallback(list, false);
        }
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        init();
    }

    private void addOrRemoveCallback(List<View> list, boolean add)
    {
        for (View item : list)
        {
            if (item instanceof FEditTextContainer)
            {
                throw new RuntimeException(FEditTextContainer.class.getSimpleName() + " is found in " + this);
            } else if (item instanceof UpdateCallback)
            {
                if (add)
                    addCallback((UpdateCallback) item);
                else
                    removeCallback((UpdateCallback) item);
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
            setEditText((EditText) view);
        }
        return list;
    }

    public interface UpdateCallback
    {
        void onUpdate(EditText editText);
    }
}
