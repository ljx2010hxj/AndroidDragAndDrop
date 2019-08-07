package com.nida.recycleviewdraganddrop.view.item;

import android.content.Context;

import com.nida.recycleviewdraganddrop.R;
import com.nida.recycleviewdraganddrop.view.QuickItem;

/**
 * Create Time:2019/7/2 11:35
 * Author:Kerwin Li
 * Description:
 **/
public class RotationItem extends QuickItem {
    public RotationItem(Context context) {
        super(context);
        setItemImgRes(R.drawable.ic_3d_rotation_black_24dp);
        setItemSpec("rotation");
        setItemTitle(R.string.rotation_item_title);
    }
}
