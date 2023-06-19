package com.huynhngoctai.ttcm_app_altp;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import com.huynhngoctai.ttcm_app_altp.database.AppDatabase;
import com.huynhngoctai.ttcm_app_altp.view.dialog.SettingDialog;

public class App extends Application {
    private static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "Database")
                .createFromAsset("db/Database.db").build();
        boolean stateOfDarkMode = CommonUtils.getInstance().getPrefDefaultFalse(SettingDialog.STATE_DARK_MODE);
        if (!stateOfDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}