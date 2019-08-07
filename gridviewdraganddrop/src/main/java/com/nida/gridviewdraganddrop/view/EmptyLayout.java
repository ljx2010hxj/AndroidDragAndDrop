package com.nida.gridviewdraganddrop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Create Time:2019/6/26 8:27
 * Author:Kerwin Li
 * Description:
 **/
public class EmptyLayout extends LinearLayout {
    private boolean mAbove = false;
    private EmptyLayoutListener mEmptyLayoutListener;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setOnDragListener(onDragListener);
    }

    private OnDragListener onDragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("nida", "############MyLayout############ACTION_DRAG_STARTED");
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("nida", "############MyLayout############ACTION_DRAG_ENTERED");
                    if (mEmptyLayoutListener != null) {
                        mEmptyLayoutListener.enterEmptyLayout(mAbove);
                    }
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("nida", "############MyLayout############ACTION_DRAG_LOCATION");
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("nida", "############MyLayout############ACTION_DRAG_EXITED");
                    if (mEmptyLayoutListener != null) {
                        mEmptyLayoutListener.exitEmptyLayout(mAbove);
                    }
                    return true;
                case DragEvent.ACTION_DROP:
                    Log.d("nida", "############MyLayout############ACTION_DROP");
                    if (mEmptyLayoutListener != null) {
                        mEmptyLayoutListener.dropEmptyLayout(mAbove);
                    }
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("nida", "############MyLayout############ACTION_DRAG_ENDED");
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    public boolean isAbove() {
        return this.mAbove;
    }

    public void setAbove(boolean above) {
        this.mAbove = above;
    }

    public void setEmptyLayoutListener(EmptyLayoutListener listener) {
        this.mEmptyLayoutListener = listener;
    }

    public EmptyLayoutListener getEmptyLayoutListener() {
        return this.mEmptyLayoutListener;
    }

    public interface EmptyLayoutListener {
        void enterEmptyLayout(boolean isAbove);

        void exitEmptyLayout(boolean isAbove);

        void dropEmptyLayout(boolean isAbove);
    }
}
