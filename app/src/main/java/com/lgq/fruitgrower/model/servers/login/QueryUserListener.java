package com.lgq.fruitgrower.model.servers.login;

import com.lgq.fruitgrower.model.beans.Chat;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;

/**
 * Created by lgq on 16-4-12.
 */
public abstract class QueryUserListener extends BmobListener<Chat>{
    public abstract void done(Chat s, BmobException e);

    @Override
    protected void postDone(Chat chat, BmobException e) {
        done(chat, e);
    }
}
