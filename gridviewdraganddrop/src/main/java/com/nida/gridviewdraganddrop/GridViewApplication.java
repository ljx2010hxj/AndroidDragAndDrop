package com.nida.gridviewdraganddrop;

import android.app.Application;
import android.content.Context;

/**
 * Create Time:2019/7/3 17:24
 * Author:Kerwin Li
 * Description:
 **/
public class GridViewApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
