package com.lgq.fruitgrower.model.servers.login;

/**
 * Created by lgq on 16-1-29.
 */
public interface ILogin {
    public void login(String mPhone,String mPassword,OnloginListener loginListener);
}
