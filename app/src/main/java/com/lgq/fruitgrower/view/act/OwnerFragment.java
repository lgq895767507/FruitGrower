package com.lgq.fruitgrower.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.beans.OwnerSetting;
import com.lgq.fruitgrower.view.adapter.OwnerAdapter;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.ToastUtils;
import com.lgq.fruitgrower.view.widget.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class OwnerFragment extends BaseFragment {

    private View view;
    private ArrayList<OwnerSetting> ownerSettings = new ArrayList<>();
    private OwnerAdapter ownerAdapter;
    private WrapHeightListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity,R.layout.activity_owner,null);
        initView();
        return view;
    }

//    private void initView(List<Consumer> arg0) {
//        consumers = (ArrayList)arg0;
//        ownerAdapter = new OwnerAdapter(getContext(),consumers);
//        listView = (WrapHeightListView) view.findViewById(R.id.lv_user_items);
//        listView.setAdapter(ownerAdapter);
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            showData();
        }
    }
//显示数据
    private void showData() {
//        BmobQuery query = new BmobQuery("Consumer");
//        query.findObjects(getContext(), new FindListener<Consumer>() {
//
//            @Override
//            public void onSuccess(List<Consumer> arg0) {
//                //注意：查询的结果是JSONArray,需要自行解析
//                initView(arg0);
//
//                ToastUtils.showToast(getContext(), "查询成功:" , Toast.LENGTH_SHORT);
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                ToastUtils.showToast(getContext(),"查询成功:" +s, Toast.LENGTH_SHORT);
//            }
//
//
//        });





    }

    private void initView() {

        setData();

        ownerAdapter = new OwnerAdapter(getContext(),ownerSettings);
        listView = (WrapHeightListView) view.findViewById(R.id.lv_user_items);
        listView.setAdapter(ownerAdapter);
        ownerAdapter.notifyDataSetChanged();
    }

    private void setData() {
        ownerSettings.add(new OwnerSetting(false,R.mipmap.ic_launcher, "我发布的", ""));
        ownerSettings.add(new OwnerSetting(false,R.mipmap.ic_launcher, "我的收藏", ""));
        ownerSettings.add(new OwnerSetting(false,R.mipmap.ic_launcher, "我收到的赞", ""));
        ownerSettings.add(new OwnerSetting(false,R.mipmap.ic_launcher, "我的评论", ""));
        ownerSettings.add(new OwnerSetting(true,R.mipmap.ic_launcher, "退出账号", ""));
    }
}
