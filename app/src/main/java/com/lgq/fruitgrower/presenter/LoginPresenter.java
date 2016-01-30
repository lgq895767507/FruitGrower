package com.lgq.fruitgrower.presenter;

import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.servers.login.ILogin;
import com.lgq.fruitgrower.model.servers.login.Login;
import com.lgq.fruitgrower.model.servers.login.OnloginListener;
import com.lgq.fruitgrower.view.Iview.ILoginActivity;

/**
 * Created by lgq on 16-1-29.
 */
public class LoginPresenter {
    ILoginActivity loginActivity;
    Login login;


    public LoginPresenter( ILoginActivity loginActivity){
        this.loginActivity = loginActivity;
        login = new Login();
    }

    public void login(){
        login.login(loginActivity.getPhone(), loginActivity.getPassword(), new OnloginListener() {
            @Override
            public void loginSuccess(Consumer consumer) {

            }

            @Override
            public void loginFailed() {

            }
        });
    }

}
