package com.lgq.fruitgrower.model.servers.login;

import android.content.Context;
import android.util.Log;

import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.view.utils.SharePreUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lgq on 16-4-1.
 */
public class DataServers {

    Context context;

    public DataServers(Context context) {
        this.context = context;
    }

    IDataCallBack selectByEmailIDataCallBack;

    public void setSelectByIdIDataCallBack(IDataCallBack selectByEmailIDataCallBack) {
        this.selectByEmailIDataCallBack = selectByEmailIDataCallBack;
    }

    public void selectByEmail() {
    //查找对应的objectId,注意：修改数据只能通过objectId来修改，目前不提供查询条件方式的修改方法。
        BmobQuery<Pubilsh> query = new BmobQuery<Pubilsh>();
        Log.i("lgq","now email::::"+SharePreUtils.getEmailPre(context));
        query.addWhereEqualTo("email", SharePreUtils.getEmailPre(context));

        query.findObjects(context, new FindListener<Pubilsh>() {
            @Override
            public void onSuccess(List<Pubilsh> list) {
                if (list.size() == 0){
                    selectByEmailIDataCallBack.dataOnEmpty();
                    return;
                }
                selectByEmailIDataCallBack.dataOnsuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                selectByEmailIDataCallBack.dataOnError(i,s);
            }
        });
    }
    
}
