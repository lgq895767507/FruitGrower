package com.lgq.fruitgrower.view.act;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.beans.Goods;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.view.adapter.StatusAdapter;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class MainFragment extends BaseFragment {

    private RecyclerView recycle_view;
    private View view ;
    private RecyclerView.LayoutManager layoutManager;
    private StatusAdapter adapter;
    private ArrayList<Pubilsh> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  View.inflate(activity,R.layout.fragment_main,null);
        return view;
    }

    private void initView() {
        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(getContext());



        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        recycle_view.setHasFixedSize(true);

        // use a linear layout manager
        recycle_view.setLayoutManager(layoutManager);

    }




    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            queryData();
        }
    }
    public void queryData(){

        initView();

        BmobQuery query = new BmobQuery("Pubilsh");
        query.findObjects(getContext(), new FindListener<Pubilsh>() {

            @Override
            public void onSuccess(final List<Pubilsh> arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                datas = new ArrayList<Pubilsh>();
                for (Pubilsh pubilsh : arg0){
                    datas.add(pubilsh);
                }
                //倒序
//                Comparator cmp = Collections.reverseOrder();
//                Collections.sort(datas, cmp);
                //set datas
                adapter = new StatusAdapter(getContext(),datas);
                recycle_view.setAdapter(adapter);


                ToastUtils.showToast(getContext(), "查询成功:", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(getContext(),"查询失败:" +s, Toast.LENGTH_SHORT);
            }


        });
    }
}
