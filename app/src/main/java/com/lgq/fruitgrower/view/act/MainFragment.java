package com.lgq.fruitgrower.view.act;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class MainFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return View.inflate(activity,R.layout.fragment_main,null);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            queryData();
        }
    }
    public void queryData(){
        BmobQuery query = new BmobQuery("Consumer");
        query.findObjects(getContext(), new FindListener<Consumer>() {

            @Override
            public void onSuccess(List<Consumer> arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                for (Consumer consumer : arg0){
                    System.out.println(consumer.getObjectId());
                    System.out.println(consumer.getCreatedAt());
                    System.out.println(consumer.getName());
                }

                ToastUtils.showToast(getContext(), "查询成功:" + arg0.get(0).getName(), Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(getContext(),"查询失败:" +s, Toast.LENGTH_SHORT);
            }


        });
    }
}
