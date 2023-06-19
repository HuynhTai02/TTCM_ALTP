package com.huynhngoctai.ttcm_app_altp;

import android.content.Context;
import android.content.SharedPreferences;
public class CommonUtils {
    private static final String PREF_FILE = "pref_saving";
    private static CommonUtils instance;

    private CommonUtils() {
        //for singleton
    }

    public static CommonUtils getInstance() {
        if (instance == null) {
            instance = new CommonUtils();
        }
        return instance;
    }

    public void savePref(String key, boolean value) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().putBoolean(key, value).apply();
    }

    public Boolean getPrefDefaultFalse(String key) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    //Default music is on
    public Boolean getPrefDefaultTrue(String key) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return pref.getBoolean(key, true);
    }

    public void clearPref(String key) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().remove(key).apply();
    }
}