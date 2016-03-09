package com.lgq.fruitgrower.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.entity.OwnerSetting;
import com.lgq.fruitgrower.view.adapter.OwnerAdapter;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.ToastUtils;
import com.lgq.fruitgrower.view.widget.WrapHeightListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

public class OwnerFragment extends BaseFragment {

    private View view;
    private ArrayList<OwnerSetting> ownerSettings = new ArrayList<>();
    private OwnerAdapter ownerAdapter;
    private WrapHeightListView listView;

    //head
    private ImageView iv_avatar;
    private TextView tv_subhead;
    private TextView tv_caption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity,R.layout.activity_owner,null);
        initView();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            showData();
        }
    }
//显示数据
    private void showData() {
        BmobQuery<Consumer> query = new BmobQuery<Consumer>();
        query.getObject(getContext(), "K28rFFFy",new GetListener<Consumer>() {

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(getContext(), "查询失败:" + s, Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(Consumer consumer) {
                setOweHead(consumer);

                ToastUtils.showToast(getContext(), "查询成功:", Toast.LENGTH_SHORT);
            }


        });
    }

    private void setOweHead(Consumer consumer){

            Glide.with(getContext())
                    .load(consumer.getImg().getFileUrl(getContext()))
                    .into(iv_avatar);
        tv_subhead.setText(consumer.getName());
        tv_caption.setText(consumer.getAddress());
    }

    private void initView() {

        setData();
        //head
        iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tv_subhead = (TextView) view.findViewById(R.id.tv_subhead);
        tv_caption = (TextView) view.findViewById(R.id.tv_caption);

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
