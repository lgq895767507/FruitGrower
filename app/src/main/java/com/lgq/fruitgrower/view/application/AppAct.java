package com.lgq.fruitgrower.view.application;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by lgq on 16-1-29.
 */
public class AppAct extends Application {

    private final static String APP_KEY = "1e2c0a6ba014146ecf54552f56ee0c64";

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),"1e2c0a6ba014146ecf54552f56ee0c64");
    }
}
