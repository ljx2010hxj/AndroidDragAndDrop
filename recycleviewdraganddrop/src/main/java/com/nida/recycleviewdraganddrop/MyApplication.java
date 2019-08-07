package com.nida.recycleviewdraganddrop;

import android.app.Application;
import android.content.Context;

/**
 * Create Time:2019/7/3 8:48
 * Author:Kerwin Li
 * Description:
 **/
public class MyApplication extends Application {
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
