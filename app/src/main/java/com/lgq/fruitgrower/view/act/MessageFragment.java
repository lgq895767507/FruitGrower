package com.lgq.fruitgrower.view.act;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Chat;

import com.lgq.fruitgrower.view.adapter.MessageAdapter;

import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MessageFragment extends BaseFragment implements MessageAdapter.PositionClick{
    private View view;
    private ArrayList<Chat> chats = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.activity_message, null);
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
        BmobQuery query = new BmobQuery();
        query.findObjects(getContext(), new FindListener<Chat>() {

            @Override
            public void onSuccess(List<Chat> arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                initView(arg0);

                for (Chat chat:arg0){
                    System.out.println(chat.getOther_name());
                    System.out.println(chat.getTime().getDate());
                    System.out.println(chat.getOther_img().getFilename());//文件名称
                    System.out.println(chat.getOther_img().getFileUrl(getContext()));//文件下载地址
                }

                ToastUtils.showToast(getContext(), "查询成功:", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(getContext(), "无法连接网络:" + s, Toast.LENGTH_SHORT);
            }


        });



    }
    private void initView(List<Chat> arg0){
        chats = (ArrayList)arg0;
        messageAdapter = new MessageAdapter(getContext(),chats,this);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(messageAdapter);
    }


    @Override
    public void positionClick(int position) {
        Log.i("lgq","popopopopo::"+position);
        Intent intent = new Intent(getContext(),ChatActivity.class);
        startActivity(intent);
    }
}
