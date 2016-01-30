package com.lgq.fruitgrower.model.servers.login;

import com.lgq.fruitgrower.model.beans.Consumer;

/**
 * Created by lgq on 16-1-29.
 */
public interface OnloginListener {

    void loginSuccess(Consumer consumer);

    void loginFailed();
}
