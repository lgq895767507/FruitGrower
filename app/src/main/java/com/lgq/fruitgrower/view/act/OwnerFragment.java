package com.lgq.fruitgrower.view.act;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.constance.Constance;
import com.lgq.fruitgrower.model.entity.OwnerSetting;
import com.lgq.fruitgrower.view.adapter.OwnerAdapter;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;
import com.lgq.fruitgrower.view.widget.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

public class OwnerFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ArrayList<OwnerSetting> ownerSettings = new ArrayList<>();
    private OwnerAdapter ownerAdapter;
    private WrapHeightListView listView;

    //head
    private ImageView iv_avatar;
    private TextView tv_subhead;
    private TextView tv_caption;

    //ItemOnclick
    private LinearLayout ll_userinfo;

    //sharePreference auto login


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.activity_owner, null);
        initView();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            showData();
        }
    }

    //显示数据
    private void showData() {

        BmobQuery<Consumer> query = new BmobQuery<Consumer>();
        //其他条件的查询

        query.addWhereEqualTo("email", SharePreUtils.getEmailPre(getActivity()));
        query.findObjects(getContext(), new FindListener<Consumer>() {
            @Override
            public void onSuccess(List<Consumer> list) {
                for (Consumer consumer : list) {
                    setOweHead(consumer);
                    ToastUtils.showToast(getContext(), "查询成功:", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(int i, String s) {
                //若没有网络的时候，显示本地的数据
                localViewShow();
                ToastUtils.showToast(getContext(), "查询失败:" + i, Toast.LENGTH_SHORT);
            }
        });


    }

    private void setOweHead(Consumer consumer) {
        if (consumer.getImg() != null) {
            Glide.with(getContext())
                    .load(consumer.getImg().getFileUrl(getContext()))
                    .thumbnail(Constance.SizeHeadPng)
                    .into(iv_avatar);
        }
        tv_subhead.setText(consumer.getName());
        tv_caption.setText(consumer.getAddress());

    }

    private void localViewShow(){
        Glide.with(getContext())
                .load(SharePreUtils.getEmailPre(getContext(), Constance.imgHeadPath, ""))
                .into(iv_avatar);
        tv_subhead.setText(SharePreUtils.getEmailPre(getContext(), Constance.nickname, SharePreUtils.getEmailPre(getContext())));
        tv_caption.setText(SharePreUtils.getEmailPre(getContext(), Constance.address, ""));
    }

    private void initView() {

        setData();
        //head
        iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tv_subhead = (TextView) view.findViewById(R.id.tv_subhead);
        tv_caption = (TextView) view.findViewById(R.id.tv_caption);

        ownerAdapter = new OwnerAdapter(getContext(), ownerSettings);
        listView = (WrapHeightListView) view.findViewById(R.id.lv_user_items);
        listView.setAdapter(ownerAdapter);
        //onclick
        listView.setOnItemClickListener(this);

        ownerAdapter.notifyDataSetChanged();

        ll_userinfo = (LinearLayout) view.findViewById(R.id.ll_userinfo);
        ll_userinfo.setOnClickListener(llOnclick);
    }

    private void setData() {
        ownerSettings.add(new OwnerSetting(false, R.mipmap.publish, getString(R.string.ower_publish), ""));
        ownerSettings.add(new OwnerSetting(false, R.mipmap.saved, getString(R.string.ower_saved), ""));
        ownerSettings.add(new OwnerSetting(false, R.mipmap.like, getString(R.string.ower_liked), ""));
        ownerSettings.add(new OwnerSetting(false, R.mipmap.message, getString(R.string.ower_comment), ""));
        ownerSettings.add(new OwnerSetting(true, R.mipmap.compose_trendbutton_background, getString(R.string.exit_login), ""));
    }

    View.OnClickListener llOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent2Activity(OwerDetailsAct.class);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ownerPublic();
                ToastUtils.showToast(getContext(), "case 0 ", Toast.LENGTH_SHORT);
                break;
            case 1:
                ToastUtils.showToast(getContext(), "case 1 ", Toast.LENGTH_SHORT);
                break;
            case 2:
                ToastUtils.showToast(getContext(), "case 2 ", Toast.LENGTH_SHORT);
                break;
            case 3:
                ToastUtils.showToast(getContext(), "case 3 ", Toast.LENGTH_SHORT);
                break;
            case 4:
                //change login shareference
                exitApp();
                break;
        }
    }

    private void ownerPublic(){
       intent2Activity(OwnerPublicList.class);
    }

    private void exitApp() {

        AlertDialog.Builder exitDialog = new AlertDialog.Builder(getContext());
        exitDialog.setTitle("提示")
                .setMessage("您确定退出当前用户吗？")
                .setNegativeButton("取消", new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do cancle
                        return;
                    }
                }).setPositiveButton("确定", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do finish
                //clear SharePre
                SharePreUtils.clear(getContext());
                getActivity().finish();
            }
        }).show();

    }
}
