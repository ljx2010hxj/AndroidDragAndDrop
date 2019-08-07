package com.nida.gridviewdraganddrop.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.nida.gridviewdraganddrop.EditorData;
import com.nida.gridviewdraganddrop.R;
import com.nida.gridviewdraganddrop.SPManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Time:2019/6/11 10:07
 * Author:Kerwin Li
 * Description:
 **/
public class GridViewEditor extends LinearLayout implements DragGridView.DragDataChangedListener, EmptyLayout.EmptyLayoutListener {
    private DragGridView mAboveDragGridView;
    private DragGridView mBelowDragGridView;
    private DragGridViewAdapter mAboveAdapter;
    private DragGridViewAdapter mBelowAdapter;
    private static final int INVALID_POSITION = -1;
    private int mCurrentPos = INVALID_POSITION;
    private boolean isCanCover = false;
    private DragItem mCurrentItem;
    private EmptyLayout mAboveEmptyLayout;
    private EmptyLayout mBelowEmptyLayout;
    private boolean isShow = false;

    public GridViewEditor(Context context) {
        this(context, null);
    }

    public GridViewEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
        setOrientation(VERTICAL);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.grid_view_editor_layout, this);

        mAboveDragGridView = (DragGridView) findViewById(R.id.above_grid_view);
        mAboveDragGridView.setCanCover(true);
        mAboveDragGridView.setDragDataChangedListener(this);

        mAboveEmptyLayout = (EmptyLayout) findViewById(R.id.above_empty_layout);
        mAboveEmptyLayout.setAbove(true);
        mAboveEmptyLayout.setEmptyLayoutListener(this);
        mAboveDragGridView.setEmptyView(mAboveEmptyLayout);

        mAboveAdapter = new DragGridViewAdapter(getContext());
        mAboveDragGridView.setAdapter(mAboveAdapter);

        mBelowDragGridView = (DragGridView) findViewById(R.id.below_grid_view);
        mBelowDragGridView.setCanCover(false);
        mBelowDragGridView.setDragDataChangedListener(this);

        mBelowEmptyLayout = (EmptyLayout) findViewById(R.id.below_empty_layout);
        mBelowEmptyLayout.setAbove(false);
        mBelowEmptyLayout.setEmptyLayoutListener(this);
        mBelowDragGridView.setEmptyView(mBelowEmptyLayout);

        mBelowAdapter = new DragGridViewAdapter(getContext());
        mBelowDragGridView.setAdapter(mBelowAdapter);
    }

    public void updateRes() {
        mAboveAdapter.notifyDataSetChanged();
        mBelowAdapter.notifyDataSetChanged();
    }

    public void show() {
        if (!isShow) {
            isShow = true;
            setVisibility(VISIBLE);
        }
    }

    public void hide() {
        if (isShow) {
            isShow = false;
            saveSpecs();
            setVisibility(GONE);
        }
    }

    @Override
    public void dragDataChanged(int pos, boolean canCover) {
        Log.d("nida", "dragDataChanged pos=" + pos + ", canCover=" + canCover);
        mCurrentPos = pos;
        isCanCover = canCover;
        if (canCover) {
            mCurrentItem = (DragItem) mAboveAdapter.getItem(pos);
        } else {
            mCurrentItem = (DragItem) mBelowAdapter.getItem(pos);
        }
    }

    @Override
    public void updateDragGridView() {
        Log.d("nida", "updateDragGridView isCanCover=" + isCanCover + ", mCurrentPos=" + mCurrentPos);
        if (isCanCover) {
            mAboveAdapter.getItemsList().remove(mCurrentPos);
            mAboveAdapter.setHidePos(INVALID_POSITION);
            mAboveAdapter.notifyDataSetChanged();
        } else {
            mBelowAdapter.getItemsList().remove(mCurrentPos);
            mBelowAdapter.setHidePos(INVALID_POSITION);
            mBelowAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public DragItem getDraggedItem() {
        return mCurrentItem;
    }

    @Override
    public int getAboveItemsNum() {
        return mAboveAdapter.getCount();
    }

    public void setAboveDragGridViewList(List<DragItem> list) {
        mAboveAdapter.setItemsList(list);
        mAboveAdapter.notifyDataSetChanged();
    }

    public void setBelowDragGridView(List<DragItem> list) {
        mBelowAdapter.setItemsList(list);
        mBelowAdapter.notifyDataSetChanged();
    }

    @Override
    public void enterEmptyLayout(boolean isAbove) {
        if (isAbove) {
            if (mAboveAdapter.getItemsList().size() > 0) {
                return;
            }
            mAboveAdapter.getItemsList().add(0, mCurrentItem);
            mAboveAdapter.setHidePos(0);
            mAboveAdapter.notifyDataSetChanged();
            //need to remove mCurrentItem from mBelowAdapter
            mBelowAdapter.getItemsList().remove(mCurrentPos);
            mBelowAdapter.notifyDataSetChanged();
        } else {
            if (mBelowAdapter.getItemsList().size() > 0) {
                return;
            }
            mBelowAdapter.getItemsList().add(0, mCurrentItem);
            mBelowAdapter.setHidePos(0);
            mBelowAdapter.notifyDataSetChanged();
            //need to remove mCurrentItem from mAboveAdapter
            mAboveAdapter.getItemsList().remove(mCurrentPos);
            mBelowAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void exitEmptyLayout(boolean isAbove) {
        if (isAbove) {
            mAboveDragGridView.setCurrentPos(0);
        } else {
            mBelowDragGridView.setCurrentPos(0);
        }
    }

    @Override
    public void dropEmptyLayout(boolean isAbove) {
        if (isAbove) {
            mAboveAdapter.setHidePos(INVALID_POSITION);
            mAboveAdapter.notifyDataSetChanged();
        } else {
            mBelowAdapter.setHidePos(INVALID_POSITION);
            mBelowAdapter.notifyDataSetChanged();
        }
    }

    public void saveSpecs() {
        List<String> newSpecs = new ArrayList<>();
        for (int i = 0; i < mAboveAdapter.getCount() && mAboveAdapter.getItem(i) != null; i++) {
            newSpecs.add(((DragItem) mAboveAdapter.getItem(i)).spec);
        }
        changeItems(newSpecs);
    }

    public void changeItems(List<String> newTiles) {
        Log.d("nida", "changeItems=" + TextUtils.join(",", newTiles));
        SPManager.getInstance().writeStringValue(EditorData.STORED_ITEM_STR, TextUtils.join(",", newTiles));
    }

    public DragGridView getAboveDragGridView() {
        return this.mAboveDragGridView;
    }
}
