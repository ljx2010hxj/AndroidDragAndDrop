package com.nida.gridviewdraganddrop.view;

/**
 * Create Time:2019/7/3 17:15
 * Author:Kerwin Li
 * Description:
 **/
public class DragItem {
    public int titleResId;
    public int imgResId;
    public String spec;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof DragItem) {
            return this.imgResId == ((DragItem) obj).imgResId
                    && this.spec.equals(((DragItem) obj).spec)
                    && this.titleResId == ((DragItem) obj).titleResId;

        }
        return false;
    }
}
