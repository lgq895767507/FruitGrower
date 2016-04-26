package com.lgq.fruitgrower.view.act;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.model.servers.login.DataServers;
import com.lgq.fruitgrower.model.servers.login.IDataCallBack;
import com.lgq.fruitgrower.view.adapter.StatusAdapter;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.RecyclerViewAndSwipeRefreshLayout;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.UpdateListener;


public class MainFragment extends BaseFragment implements StatusAdapter.MyViewHolder.ItemClick,RecyclerViewAndSwipeRefreshLayout.SwipeRefreshLayoutRefresh{

  //  private RecyclerView recycle_view;
    private View view ;
  //  private RecyclerView.LayoutManager layoutManager;
    private StatusAdapter adapter;
    private ArrayList<Pubilsh> datas;
    //region RecyclerView组件属性
    RecyclerViewAndSwipeRefreshLayout recyclerViewAndSwipeRefreshLayout;
    //业务
    DataServers dataServers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null){
            Log.i("lgq1","return View");
            return view;
        }

        view =  View.inflate(activity,R.layout.fragment_main,null);
        initView();
        Log.i("lgq1", "MainFragment");
        return view;
    }

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
*/
    /*@Override
        public void onHiddenChanged(boolean hidden) {
            super.onHiddenChanged(hidden);
            if (!hidden) {
                Log.i("lgq1","ownerFragment");
                //设置SwipeRefreshLayout组件开始显示刷新球
                recyclerViewAndSwipeRefreshLayout.getSwipeRefreshLayout().setRefreshing(true);
                //直接调用业务
                dataServers.selectByEmail();
            }
        }
    */
    private void initView() {
  //     recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
  //     layoutManager = new LinearLayoutManager(getContext());
        adapter = new StatusAdapter(this);
        //实例化业务
        dataServers = new DataServers(getContext());
        dataServers.setSelectByIdIDataCallBack(selectIDataCallBack);
        //设置RecyclerView组件
        recyclerViewAndSwipeRefreshLayout = new RecyclerViewAndSwipeRefreshLayout(getContext(),view,adapter,this);
        //设置SwipeRefreshLayout组件开始显示刷新球
        recyclerViewAndSwipeRefreshLayout.getSwipeRefreshLayout().setRefreshing(true);
        //直接调用业务
        dataServers.selectByEmail();

        // improve performance if you know that changes in content
   /*     // do not change the size of the RecyclerView
        recycle_view.setHasFixedSize(true);*/

   /*     // use a linear layout manager
        recycle_view.setLayoutManager(layoutManager);*/

    }

    //业务实现回调
    IDataCallBack<Pubilsh> selectIDataCallBack = new IDataCallBack<Pubilsh>() {
        @Override
        public void dataOnsuccess(List<Pubilsh> objects) {
            for (Pubilsh object : objects){
                Log.i("lgq","obj:"+object.getContent()+objects.size());
            }
            adapter.getDatas().addAll(objects);
            adapter.notifyDataSetChanged();
            ToastUtils.showToast(getContext(), "加载成功...", Toast.LENGTH_SHORT);
            recyclerViewAndSwipeRefreshLayout.getSwipeRefreshLayout().setRefreshing(false);
        }

        @Override
        public void dataOnEmpty() {
            ToastUtils.showToast(getContext(),"loading empty",Toast.LENGTH_SHORT);
            recyclerViewAndSwipeRefreshLayout.getSwipeRefreshLayout().setRefreshing(false);
        }

        @Override
        public void dataOnError(int code, String msg) {
            ToastUtils.showToast(getContext(),"loading error:"+code+":"+msg,Toast.LENGTH_SHORT);
            recyclerViewAndSwipeRefreshLayout.getSwipeRefreshLayout().setRefreshing(false);
        }
    };

    @Override
    public void swipeRefreshLayoutOnRefresh() {
        adapter.getDatas().clear();
        //重新执行业务
        dataServers.selectByEmail();
    }

    @Override
    public void onBtnShareClick(int position) {
     //   intent2Activity(CommentActivity.class);
    }

    @Override
    public void onBtnCommentClick(int position) {
    /*    Bundle bundle = new Bundle();
        bundle.putString("ObjectId", adapter.getDatas().get(position).getObjectId());
        Intent intent = new Intent(getContext(),CommentActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);*/

        ((iMainFragment)getActivity()).iCommentClick(adapter.getDatas().get(position).getObjectId());
    }

    @Override
    public void onBtnLikeClick(int position) {
        Log.i("lgq", "kkk:" + adapter.getDatas().get(position).getObjectId());
        Pubilsh pubilsh = new Pubilsh();
        pubilsh.increment("liked");
        pubilsh.update(getContext(), adapter.getDatas().get(position).getObjectId(),new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("lgq","liked");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("lgq","liked onFailure"+i+"---"+s);
            }
        });
        Log.i("lgq","onBtnLikeClick++++"+position);
    }

    @Override
    public void onBtnUnLikeClick(int position) {
        Pubilsh pubilsh = new Pubilsh();
        pubilsh.increment("liked",-1);
        pubilsh.update(getContext(), adapter.getDatas().get(position).getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("lgq", "unliked");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("lgq", "unlike onFailure" + i + "---" + s);
            }
        });
        Log.i("lgq","onBtnLikeClick++++"+position);
    }

    @Override
    public void onRootViewClick(int position) {
        //显示具体的消息内容
        intent2Activity(CommentDetailsAct.class);
        Log.i("lgq","position++++"+position);
    }

    public interface iMainFragment{
        void iCommentClick(String iObjectId);
    }
}
