package com.nida.recycleviewdraganddrop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.nida.recycleviewdraganddrop.R;

/**
 * Create Time:2019/7/2 10:26
 * Author:Kerwin Li
 * Description:
 **/
public class QuickGroup extends ViewGroup {
    private int mItemWidth = 0;
    private int mItemHeight = 0;
    private int mColNum = 0;
    private int mVerticalSpace = 0;
    private int mHorizontalSpace = 0;
    private static final int DEF_HEIGHT = 118;
    private static final int DEF_COL_NUM = 5;
    private static final int DEF_VERTICAL_SPACE = 15;
    private static final int DEF_HORIZONTAL_SPACE = 20;
    private int mMaxWidth = 0;

    public QuickGroup(Context context) {
        this(context, null);
    }

    public QuickGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QuickGroup(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.QuickGroup, 0, 0);
        try {
            mItemWidth = (int) ta.getDimension(R.styleable.QuickGroup_itemWidth, 0);
            mItemHeight = (int) ta.getDimension(R.styleable.QuickGroup_itemHeight, 0);
            mVerticalSpace = (int) ta.getDimension(R.styleable.QuickGroup_verticalSpace, 0);
            mHorizontalSpace = (int) ta.getDimension(R.styleable.QuickGroup_horizontalSpace, 0);
            mColNum = ta.getInt(R.styleable.QuickGroup_colNum, 0);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int height = 0;
        if (childCount == 0) {
            int heightSpec = MeasureSpec.makeMeasureSpec(DEF_HEIGHT, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightSpec);
            return;
        }
        if (mColNum == 0) {
            mColNum = DEF_COL_NUM;
        }
        if (mVerticalSpace == 0) {
            mVerticalSpace = DEF_VERTICAL_SPACE;
        }

        if (mItemHeight == 0) {
            mItemHeight = DEF_HEIGHT;
        }
        int rowNum = childCount / mColNum;
        int reminder = childCount % mColNum;
        int padding = getPaddingTop() + getPaddingBottom();
        if (reminder == 0) {
            height = rowNum * mItemHeight + (rowNum - 1) * mVerticalSpace + padding;
        } else {
            height = (rowNum + 1) * mItemHeight + rowNum * mVerticalSpace + padding;
        }
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        measureChildren(widthMeasureSpec, heightSpec);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        if (mColNum == 0) {
            mColNum = DEF_COL_NUM;
        }

        if (mItemWidth == 0) {
            mItemWidth = DEF_HEIGHT;
        }

        if (mItemHeight == 0) {
            mItemHeight = DEF_HEIGHT;
        }

        if (mVerticalSpace == 0) {
            mVerticalSpace = DEF_VERTICAL_SPACE;
        }

        if (mHorizontalSpace == 0) {
            mHorizontalSpace = DEF_HORIZONTAL_SPACE;
        }
        int padding = getPaddingLeft() + getPaddingRight();
        int totalItemsWidth = mColNum * mItemWidth + padding;
        int totalWidth = mColNum * mItemWidth + (mColNum - 1) * mHorizontalSpace + padding;
        if (totalItemsWidth > mMaxWidth) {
            mHorizontalSpace = DEF_HORIZONTAL_SPACE;
            mItemWidth = (mMaxWidth - mHorizontalSpace * (mColNum - 1) - padding) / mColNum;
        } else if (totalWidth > mMaxWidth) {
            mHorizontalSpace = (mMaxWidth - totalItemsWidth) / (mColNum - 1);
        }
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            int row = i / mColNum;
            int col = i % mColNum;
            int left = col * (mItemWidth + mHorizontalSpace);
            int top = (mItemHeight + mVerticalSpace) * row;
            if (row == 0) {
                top += getPaddingTop();
            }
            if (col == 0) {
                left += getPaddingLeft();
            }
            child.layout(left, top,
                    left + mItemWidth, top + mItemHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
