package com.lgq.fruitgrower.view.act;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.view.adapter.MessageAdapter;
import com.lgq.fruitgrower.view.adapter.OwnerPublicAdapter;
import com.lgq.fruitgrower.view.adapter.StatusAdapter;
import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class OwnerPublicListAct extends AppCompatActivity implements OwnerPublicAdapter.MyViewHolder.ItemClick{

    private RecyclerView owner_public_recycle;
    private OwnerPublicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_public_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        owner_public_recycle = (RecyclerView) findViewById(R.id.owner_public_recycle);
        adapter = new OwnerPublicAdapter(this);
        owner_public_recycle.setLayoutManager(new LinearLayoutManager(this));
        //适配器添加数据
        BmobQuery<Pubilsh> query = new BmobQuery<Pubilsh>();
        //降序排列
        query.order("-createdAt");
        query.addWhereEqualTo("email", SharePreUtils.getEmailPre(this));
        query.findObjects(this, new FindListener<Pubilsh>() {
            @Override
            public void onSuccess(List<Pubilsh> list) {
                if (list.size() == 0) {
                    ToastUtils.showToast(getApplicationContext(),"您还没有发布任何消息！",Toast.LENGTH_SHORT);
                }
                adapter.getDatas().addAll(list);
                //设置适配器
                owner_public_recycle.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(getApplicationContext(),"ownerpublicERROR"+i+s, Toast.LENGTH_SHORT);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRootViewClick(int position) {

    }
}
