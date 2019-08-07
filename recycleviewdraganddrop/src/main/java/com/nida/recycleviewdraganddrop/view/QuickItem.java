package com.nida.recycleviewdraganddrop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nida.recycleviewdraganddrop.R;

/**
 * Create Time:2019/7/2 10:34
 * Author:Kerwin Li
 * Description:
 **/
public class QuickItem extends LinearLayout {
    private TextView mItemTitle;
    private String mItemSpec;
    private ImageView mItemImg;

    public QuickItem(Context context) {
        this(context, null);
    }

    public QuickItem(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QuickItem(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initView(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.item_layout, this);
        //LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this, true);
        mItemTitle = (TextView) findViewById(R.id.item_title);
        mItemImg = (ImageView) findViewById(R.id.item_img);
    }

    public void setItemTitle(String title) {
        mItemTitle.setText(title);
    }

    public void setItemTitle(int resId) {
        mItemTitle.setText(resId);
    }

    public TextView getItemTitle() {
        return mItemTitle;
    }

    public void setItemImgRes(int resId) {
        mItemImg.setImageResource(resId);
    }

    public ImageView getItemImg() {
        return mItemImg;
    }

    public void setItemSpec(String spec) {
        this.mItemSpec = spec;
    }

    public String getItemSpec() {
        return this.mItemSpec;
    }
}
