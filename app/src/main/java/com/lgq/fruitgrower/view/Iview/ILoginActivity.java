package com.lgq.fruitgrower.view.Iview;

import com.lgq.fruitgrower.model.beans.Consumer;

/**
 * Created by lgq on 16-1-29.
 */
public interface ILoginActivity {
    String getPhone();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(Consumer consumer);

    void showFailedError();

}
