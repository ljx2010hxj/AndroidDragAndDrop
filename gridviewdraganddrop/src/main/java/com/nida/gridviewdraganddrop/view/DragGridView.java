package com.nida.gridviewdraganddrop.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Create Time:2019/6/10 9:35
 * Author:Kerwin Li
 * Description:
 **/
public class DragGridView extends GridView {
    private static final String ITEM_TAG = "item_tag";
    private int mTouchPos = -1;
    //A flag decide to the item whether can cover.If the DragGridView below, it can't cover.
    private boolean canCover = true;
    private int mCurrentPos = AdapterView.INVALID_POSITION;

    //A flag whether move from a view to anther.
    private boolean mCrossMove = false;

    //A flag whether added a space item.
    private boolean hasAddItem = false;

    private static final int INVALID_TOUCH_POSITION = -1;
    private static final int ABOVE_MAX_ITEM_NUM = 15;

    private DragDataChangedListener mDataChangedListener;

    public DragGridView(Context context) {
        this(context, null);
    }

    public DragGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DragGridView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        initView();
    }

    private OnItemLongClickListener onLongClickListener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //above item more than 15, prevent to move from below to above.
            if (!canCover) {
                if (mDataChangedListener != null) {
                    if (mDataChangedListener.getAboveItemsNum() >= ABOVE_MAX_ITEM_NUM) {
                        return true;
                    }
                }
            }

            ClipData.Item item = new ClipData.Item("");
            ClipData data = new ClipData(ITEM_TAG, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                    item);
            view.startDrag(data, new EditorDragShadowBuilder(view), null, 0);
            mTouchPos = mCurrentPos = position;
            ((DragGridViewAdapter) getAdapter()).setHidePos(mTouchPos);
            //缓存当前被dragged的item
            if (mDataChangedListener != null) {
                mDataChangedListener.dragDataChanged(mCurrentPos, canCover);
            }
            return true;
        }
    };

    private OnDragListener mOnDragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("nida", "drag start");
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    hasAddItem = false;
                    Log.d("nida", "drag enter mTouchPos=" + mTouchPos + ", canCover=" + canCover + ", mCurrentPos=" + mCurrentPos);
                    //move between above and below.
                    if (mTouchPos == AdapterView.INVALID_POSITION) {
                        if (mDataChangedListener != null) {
                            mDataChangedListener.updateDragGridView();
                        }
                        mCrossMove = true;
                        //from below to above.
                        if (canCover) {
                            int pos = pointToPosition((int) event.getX(), (int) event.getY());
                            if (pos != AdapterView.INVALID_POSITION) {
                                ((DragGridViewAdapter) getAdapter()).getItemsList().add(pos, mDataChangedListener.getDraggedItem());
                                ((DragGridViewAdapter) getAdapter()).setHidePos(pos);
                                mCurrentPos = pos;
                                hasAddItem = true;
                            }
                        } else {//from above to below.
                            //need add a view.
                            Log.d("nida", "ACTION_DRAG_ENTERED add in 0 pos");
                            ((DragGridViewAdapter) getAdapter()).getItemsList().add(0, mDataChangedListener.getDraggedItem());
                            ((DragGridViewAdapter) getAdapter()).setHidePos(0);
                        }
                    }
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("nida", "drag location x=" + event.getX() + ", y=" + event.getY());
                    int currentLocaPos = pointToPosition((int) event.getX(), (int) event.getY());
                    Log.d("nida", "drag location  currentLocaPos=" + currentLocaPos + ", mCurrentPos=" + mCurrentPos + ", mCrossMove=" + mCrossMove);
                    if (mCrossMove) {
                        //now, move on above.
                        if (canCover) {
                            if (currentLocaPos != AdapterView.INVALID_POSITION && mCurrentPos != currentLocaPos) {
                                if (!hasAddItem) {
                                    Log.d("nida", "this will add");
                                    ((DragGridViewAdapter) getAdapter()).getItemsList().add(currentLocaPos, mDataChangedListener.getDraggedItem());
                                    ((DragGridViewAdapter) getAdapter()).setHidePos(currentLocaPos);
                                    hasAddItem = true;
                                } else {
                                    ((DragGridViewAdapter) getAdapter()).setHidePos(currentLocaPos);
                                    ((DragGridViewAdapter) getAdapter()).swap(mCurrentPos, currentLocaPos);
                                }
                                mCurrentPos = currentLocaPos;
                            }

                            if (currentLocaPos == AdapterView.INVALID_POSITION && mCurrentPos == currentLocaPos) {
                                Log.d("nida", "Add in the last");
                                ((DragGridViewAdapter) getAdapter()).getItemsList().add(((DragGridViewAdapter) getAdapter()).getCount(), mDataChangedListener.getDraggedItem());
                                ((DragGridViewAdapter) getAdapter()).setHidePos(((DragGridViewAdapter) getAdapter()).getCount() - 1);
                                hasAddItem = true;
                                mCurrentPos = ((DragGridViewAdapter) getAdapter()).getCount() - 1;
                                Log.d("nida", "size=" + ((DragGridViewAdapter) getAdapter()).getCount());
                            }
                        }
                        return true;
                    }

                    if (!canCover) {
                        return true;
                    }
                    if (currentLocaPos != AdapterView.INVALID_POSITION && mCurrentPos != currentLocaPos) {
                        ((DragGridViewAdapter) getAdapter()).setHidePos(currentLocaPos);
                        ((DragGridViewAdapter) getAdapter()).swap(mTouchPos, currentLocaPos);
                        mCurrentPos = currentLocaPos;
                    }
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("nida", "drag exit mTouchPos=" + mTouchPos + ", mCurrentPos=" + mCurrentPos);
                    if (mCrossMove) {
                        if (!canCover) {
                            mCurrentPos = 0;
                        }
                        mTouchPos = -1;
                    }

                    if (mDataChangedListener != null) {
                        mDataChangedListener.dragDataChanged(mCurrentPos, canCover);
                    }
                    mCurrentPos = -1;
                    mTouchPos = -1;
                    return true;
                case DragEvent.ACTION_DROP:
                    Log.d("nida", "drag drop x=" + event.getX() + ", y=" + event.getY());
                    ((DragGridViewAdapter) getAdapter()).setHidePos(INVALID_TOUCH_POSITION);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("nida", "drag end");
                    mTouchPos = -1;
                    mCurrentPos = -1;
                    mCrossMove = false;
                    ((DragGridViewAdapter) getAdapter()).setHidePos(INVALID_TOUCH_POSITION);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    private void initView() {
        setOnItemLongClickListener(onLongClickListener);
        setOnDragListener(mOnDragListener);
    }

    public void setCanCover(boolean canCover) {
        this.canCover = canCover;
    }

    public boolean isCanCover() {
        return canCover;
    }

    public void setDragDataChangedListener(DragDataChangedListener listener) {
        this.mDataChangedListener = listener;
    }

    public void setCurrentPos(int pos) {
        mTouchPos = mCurrentPos = pos;
    }

    public interface DragDataChangedListener {
        void dragDataChanged(int pos, boolean canCover);

        void updateDragGridView();

        DragItem getDraggedItem();

        int getAboveItemsNum();
    }

}
