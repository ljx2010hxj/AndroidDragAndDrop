package com.nida.gridviewdraganddrop.view;

import android.graphics.Point;
import android.view.View;

/**
 * Create Time:2019/6/11 8:52
 * Author:Kerwin Li
 * Description:
 **/
public class EditorDragShadowBuilder extends View.DragShadowBuilder {
    private static final int TOUCH_POINTER_X = 70;
    private static final int TOUCH_POINTER_Y = 50;
    private static final float SCALE_RATIO = 1.5f;

    public EditorDragShadowBuilder(View view) {
        super(view);
    }

    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
        View view = getView();
        if (view == null) {
            return;
        }
        int width = (int) (view.getWidth() * SCALE_RATIO);
        int height = (int) (view.getHeight() * SCALE_RATIO);

        outShadowSize.set(width, height);
        outShadowTouchPoint.set(TOUCH_POINTER_X, TOUCH_POINTER_Y);
    }
}
