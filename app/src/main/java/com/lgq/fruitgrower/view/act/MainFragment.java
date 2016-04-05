package com.lgq.fruitgrower.view.act;


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

        view =  View.inflate(activity,R.layout.fragment_main,null);
        initView();
        return view;
    }

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



  /*  @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            queryData();
        }
    }
    public void queryData(){



        BmobQuery query = new BmobQuery("Pubilsh");
        query.setLimit(5);
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

                recycle_view.setAdapter(adapter);


                ToastUtils.showToast(getContext(), "查询成功:", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(getContext(),"查询失败:" +s, Toast.LENGTH_SHORT);
            }


        });
    }*/

    @Override
    public void swipeRefreshLayoutOnRefresh() {
        adapter.getDatas().clear();
        //重新执行业务
        dataServers.selectByEmail();
    }

    @Override
    public void onBtnShareClick(int position) {
        Log.i("lgq","onBtnShareClick++++"+position);
    }

    @Override
    public void onBtnCommentClick(int position) {
        Log.i("lgq","onBtnCommentClick++++"+position);
    }

    @Override
    public void onBtnLikeClick(int position) {
        Log.i("lgq","onBtnLikeClick++++"+position);
    }

    @Override
    public void onRootViewClick(int position) {
        Log.i("lgq","position++++"+position);
    }
}
