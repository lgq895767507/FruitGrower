package com.lgq.fruitgrower.model.servers.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.constance.Constance;
import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by lgq on 16-3-25.
 */
public class FindByEmailServer {
    //查找对应的objectId,注意：修改数据只能通过objectId来修改，目前不提供查询条件方式的修改方法。

    private static String objectId;

    public FindByEmailServer(){

    }

    public String findIdByEmail(Context context) {
        //查找对应的objectId,注意：修改数据只能通过objectId来修改，目前不提供查询条件方式的修改方法。
        BmobQuery<Consumer> query = new BmobQuery<Consumer>();
        query.addWhereEqualTo("email", SharePreUtils.getEmailPre(context));

        query.findObjects(context, new FindListener<Consumer>() {
            @Override
            public void onSuccess(List<Consumer> list) {
                Consumer consumer = list.get(0);
                objectId = consumer.getObjectId();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        return objectId;
    }
}
