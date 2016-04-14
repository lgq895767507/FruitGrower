package com.lgq.fruitgrower.view.application;

import android.app.Application;

import com.lgq.fruitgrower.model.CustomImHanlder.MyMessageHandler;
import com.lgq.fruitgrower.model.constance.Constance;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;

/**
 * Created by lgq on 16-1-29.
 */
public class AppAct extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(), Constance.APP_KEY);
    }
}
