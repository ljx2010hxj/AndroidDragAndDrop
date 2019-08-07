package com.nida.recycleviewdraganddrop;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Create Time:2019/1/10 13:29
 * Author:Kerwin Li
 * Description:
 **/
public class SPManager {
    private static final String TAG = SPManager.class.getSimpleName();
    private static SPManager INSTANCE = new SPManager();
    private SharedPreferences sharedPreferences;
    private static final String SP_NAME = "con_tile";

    private SPManager() {
        sharedPreferences = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SPManager getInstance() {
        return INSTANCE;
    }

    public void writeStringValue(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public void writeIntValue(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, "default");
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, 0);
    }
}
