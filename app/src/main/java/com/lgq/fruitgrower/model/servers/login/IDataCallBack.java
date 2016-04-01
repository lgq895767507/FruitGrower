package com.lgq.fruitgrower.model.servers.login;

import java.util.List;

/**
 * Created by lgq on 16-4-1.
 */

/**
 * 组合
 * 成功后返回list数据的接口,会返回空结果
 * @param <T>
 */
public interface IDataCallBack<T> extends IDataCallBackError,IDataCallBackEmpty {
    void dataOnsuccess(List<T> objects);
}
