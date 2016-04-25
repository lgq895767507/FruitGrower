package com.lgq.fruitgrower.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.CustomImHanlder.MyMessageHandler;
import com.lgq.fruitgrower.model.beans.Chat;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.view.MainActivity;
import com.lgq.fruitgrower.view.utils.IMMLeaks;
import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindStatisticsListener;

public class ChatActivity extends AppCompatActivity implements ObseverListener {

    private static ChatActivity chatActivity ;
    public static ChatActivity getInstance(){
        return chatActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

   /*     //NewIM初始化
        BmobIM.init(this);
        //注册消息接收器
        BmobIM.registerDefaultMessageHandler(new MyMessageHandler(this));*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        //连接服务器
        BmobIM.connect(SharePreUtils.getEmailPre(this), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Log.i("lgq", "connect success");
                } else {
                    Log.i("lgq", e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });

        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
               ToastUtils.showToast(getApplicationContext(), "" + status.getMsg(), Toast.LENGTH_SHORT);
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());*/
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        //添加观察者-用于是否显示通知消息
        BmobNotificationManager.getInstance(this).addObserver(this);
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
  /*      //移除观察者
        BmobNotificationManager.getInstance(this).removeObserver(this);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
  /*      //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
        //完全退出应用时需调用clearObserver来清除观察者
        BmobNotificationManager.getInstance(this).clearObserver();*/
    }




  //  private FragmentController controller;
    @Override
    public void onBackPressed() {
//        controller = FragmentController.getInstance(this,R.id.fl_content);
//        controller.showFragment(3);
        super.onBackPressed();
    }
}
