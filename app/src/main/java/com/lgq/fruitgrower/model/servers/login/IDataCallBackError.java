package com.lgq.fruitgrower.model.servers.login;

/**
 * Created by lgq on 16-4-1.
 */
public interface IDataCallBackError {
    /**
     *
     * @param code  错误编码
     * @param msg   错误描述
     */
    void dataOnError(int code,String msg);
}
