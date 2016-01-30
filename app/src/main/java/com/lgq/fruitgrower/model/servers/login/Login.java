package com.lgq.fruitgrower.model.servers.login;

import com.lgq.fruitgrower.model.beans.Consumer;

/**
 * Created by lgq on 16-1-29.
 */
public class Login implements ILogin{



    @Override
    public void login(final  String mPhone, final  String mPassword,final OnloginListener loginListener) {

        //模拟子线程耗时操作
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //模拟登录成功
                if ("zhy".equals(mPhone) && "123".equals(mPassword))
                {
                    Consumer consumer = new Consumer();
                    consumer.setPhone(mPhone);
                    consumer.setPassword(mPassword);
                    loginListener.loginSuccess(consumer);
                } else
                {
                    loginListener.loginFailed();
                }
            }
        }.start();
    }

}
