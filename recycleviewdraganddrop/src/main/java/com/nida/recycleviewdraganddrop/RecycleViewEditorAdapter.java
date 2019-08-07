package com.nida.recycleviewdraganddrop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nida.recycleviewdraganddrop.bean.QuickItemInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Create Time:2019/7/2 16:34
 * Author:Kerwin Li
 * Description:
 **/
public class RecycleViewEditorAdapter extends RecyclerView.Adapter<RecycleViewEditorAdapter.EditorViewHolder> {
    private static final long DRAG_LENGTH = 100;
    private static final float DRAG_SCALE = 1.2f;
    public static final long MOVE_DURATION = 150;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_EDIT = 1;

    private final Context mContext;
    private final ItemTouchHelper mItemTouchHelper;
    private final EditorItemDecoration mDecoration;

    private EditorViewHolder mCurrentDrag;
    private final Handler mHandler = new Handler();
    private int mEditIndex;

    private List<QuickItemInfo> mAllItemsList;


    public RecycleViewEditorAdapter(Context context) {
        mContext = context;
        mItemTouchHelper = new ItemTouchHelper(mCallbacks);
        mDecoration = new EditorItemDecoration(context);
    }

    @NonNull
    @Override
    public EditorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_EDIT) {
            return new EditorViewHolder(inflater.inflate(R.layout.recycle_view_editor_divider_layout, parent, false));
        }
        LinearLayout frame = (LinearLayout) inflater.inflate(R.layout.recycle_view_editor_item_layout, parent,
                false);
        return new EditorViewHolder(frame);
    }

    @Override
    public void onBindViewHolder(@NonNull EditorViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_EDIT) {
            ((TextView) holder.itemView.findViewById(R.id.divider_title)).setText(
                    mCurrentDrag != null ? R.string.drag_to_remove_tiles
                            : R.string.drag_to_add_tiles);
            return;
        }

        QuickItemInfo info = mAllItemsList.get(position);
        holder.mItemTitle.setText(info.titleResId);
        holder.mItemImg.setBackgroundResource(info.imgResId);
    }

    @Override
    public int getItemCount() {
        return mAllItemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAllItemsList.get(position) == null) {
            return TYPE_EDIT;
        }
        return TYPE_ITEM;
    }


    @Override
    public boolean onFailedToRecycleView(EditorViewHolder holder) {
        holder.clearDrag();
        return true;
    }

    public void saveSpecs() {
        List<String> newSpecs = new ArrayList<>();
        for (int i = 0; i < mAllItemsList.size() && mAllItemsList.get(i) != null; i++) {
            newSpecs.add(mAllItemsList.get(i).spec);
        }
        changeItems(newSpecs);
    }

    /**
     * set current tile list
     */
    public void setTileSpecs(List<QuickItemInfo> currentSpecs) {
        mAllItemsList = currentSpecs;
        for (int i = 0; i < mAllItemsList.size(); i++) {
            Log.d("nida", "setTileSpecs [" + i + "]=" + mAllItemsList.get(i));
        }
        updateDividerLocations();
        notifyDataSetChanged();
    }

    private final GridLayoutManager.SpanSizeLookup mSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            final int type = getItemViewType(position);
            return type == TYPE_EDIT ? Constant.COL_NUM : 1;
        }
    };

    public GridLayoutManager.SpanSizeLookup getSizeLookup() {
        return mSizeLookup;
    }

    public ItemTouchHelper getItemTouchHelper() {
        return mItemTouchHelper;
    }

    public EditorItemDecoration getItemDecoration() {
        return mDecoration;
    }

    private void updateDividerLocations() {
        // The first null is the edit items label, the second null is the item divider.
        // If there is no second null, then there are no non-system items.
        mEditIndex = -1;
        for (int i = 0; i < mAllItemsList.size(); i++) {
            if (mAllItemsList.get(i) == null) {
                if (mEditIndex == -1) {
                    mEditIndex = i;//the first null is edit text
                }
            }
        }

    }

    public class EditorViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mItemView;
        public ImageView mItemImg;
        public TextView mItemTitle;

        public EditorViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView instanceof LinearLayout) {
                mItemView = (LinearLayout) itemView;
                mItemImg = (ImageView) itemView.findViewById(R.id.editor_item_img);
                mItemTitle = (TextView) itemView.findViewById(R.id.editor_item_title);
            }
        }

        public void clearDrag() {
            //This is base
            mItemView.clearAnimation();
            mItemView.setAlpha(1);
        }

        //expand view to 1.2
        public void startDrag() {
            mItemView.animate()
                    .setDuration(DRAG_LENGTH)
                    .scaleX(DRAG_SCALE)
                    .scaleY(DRAG_SCALE);
        }

        //reset to original
        public void stopDrag() {
            mItemView.animate()
                    .setDuration(DRAG_LENGTH)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    private final ItemTouchHelper.Callback mCallbacks = new ItemTouchHelper.Callback() {

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState != ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder = null;
            }
            if (viewHolder == mCurrentDrag) return;
            if (mCurrentDrag != null) {
                mCurrentDrag.stopDrag();
                mCurrentDrag = null;
            }
            if (viewHolder != null) {
                mCurrentDrag = (EditorViewHolder) viewHolder;
                mCurrentDrag.startDrag();
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //change the Edit text
                    notifyItemChanged(mEditIndex);
                }
            });
        }

        @Override
        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current,
                                   RecyclerView.ViewHolder target) {
            return target.getAdapterPosition() <= mEditIndex + 1;

        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == TYPE_EDIT) {
                return makeMovementFlags(0, 0);
            }

            int dragFlags;
            //If mEditIndex more than Constant.MAX_ABOVE, just can move from above to below.
            if (mEditIndex >= Constant.MAX_ABOVE && viewHolder.getAdapterPosition() > mEditIndex) {
                dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                return makeMovementFlags(dragFlags, 0);
            }

            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT
                    | ItemTouchHelper.LEFT;
            return makeMovementFlags(dragFlags, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            return move(from, to, target.itemView);
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }
    };


    private <T> void move(int from, int to, List<T> list) {
        list.add(to, list.remove(from));
        notifyItemMoved(from, to);
    }

    private boolean move(int from, int to, View v) {
        if (to == from) {
            return true;
        }
        move(from, to, mAllItemsList);
        updateDividerLocations();
        saveSpecs();
        return true;
    }

    /**
     * The item decoration will be effected by the background image
     **/
    private class EditorItemDecoration extends RecyclerView.ItemDecoration {
        private final ColorDrawable mDrawable;

        private EditorItemDecoration(Context context) {
            TypedArray ta =
                    context.obtainStyledAttributes(new int[]{android.R.attr.colorSecondary});
            mDrawable = new ColorDrawable(ta.getColor(0, 0));
            ta.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            final int childCount = parent.getChildCount();
            final int width = parent.getWidth();
            final int bottom = parent.getBottom();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.ViewHolder holder = parent.getChildViewHolder(child);
                //Just for Edit Text
                if (holder.getAdapterPosition() < mEditIndex && !(child instanceof TextView)) {
                    continue;
                }

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getTop() + params.topMargin +
                        Math.round(ViewCompat.getTranslationY(child));
                // Draw full width, in case there aren't tiles all the way across.
                mDrawable.setBounds(0, top, width, bottom);
                //Draw mDrawable into c
                mDrawable.draw(c);
                break;
            }
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = (int) mContext.getResources().getDimension(R.dimen.editor_recycle_view_item_bottom);
        }
    }


    public void changeItems(List<String> newTiles) {
        SPManager.getInstance().writeStringValue(Constant.CON_ITEMS, TextUtils.join(",", newTiles));
    }
}
