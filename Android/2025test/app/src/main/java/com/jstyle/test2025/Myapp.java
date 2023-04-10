package com.jstyle.test2025;

import android.app.Application;

import com.jstyle.test2025.Util.SharedPreferenceUtils;
import com.jstyle.test2025.ble.BleManager;
import com.jstyle.test2025.daomananger.DbManager;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DbManager.init(this);
        SharedPreferenceUtils.init(this);
        BleManager.init(this);
    }
}
