package com.lgq.fruitgrower.view.utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.lgq.fruitgrower.R;


/**
 * Created by lgq on 16-3-31.
 */
public class RecyclerViewAndSwipeRefreshLayout  implements RecyclerView.OnTouchListener, SwipeRefreshLayout.OnRefreshListener {

    //region RecyclerView组件
    RecyclerView recyclerView;
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    //endregion

    //region SwipeRefreshLayout组件
    SwipeRefreshLayout swipeRefreshLayout;
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
    //region 适配器
    RecyclerView.Adapter adapter;
    //endregion

    //region 上下文
    Context context;
    //endregion

    //region 当前加载RecyclerView组件的View
    View view;
    //endregion

    //region 构造
    /**
     * 构造
     *
     * @param context                   上下文
     * @param view 当前加载RecyclerView组件的View,在活动或者碎片上面可以使用getWindow().getDecorView()获取对象
     * @param adapter                   RecyclerView适配器
     * @param swipeRefreshLayoutRefresh 内部接口，处理SwipeRefreshLayout组件的刷新回调
     */
    public RecyclerViewAndSwipeRefreshLayout(Context context, View view, RecyclerView.Adapter adapter, SwipeRefreshLayoutRefresh swipeRefreshLayoutRefresh) {
        this.context = context;
        this.view = view;
        this.adapter = adapter;
        this.swipeRefreshLayoutRefresh = swipeRefreshLayoutRefresh;
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        //添加分割线
    //    recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        //设置recyclerView的OnTouchListener
        recyclerView.setOnTouchListener(this);

    }

    public RecyclerViewAndSwipeRefreshLayout(Context context, View view, RecyclerView.Adapter adapter, SwipeRefreshLayoutRefresh swipeRefreshLayoutRefresh,boolean isItemDecoration) {
        this.context = context;
        this.view = view;
        this.adapter = adapter;
        this.swipeRefreshLayoutRefresh = swipeRefreshLayoutRefresh;
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        //添加分割线
        if(isItemDecoration)
        {
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        }
        //设置recyclerView的OnTouchListener
        recyclerView.setOnTouchListener(this);
    }

    //region SwipeRefreshLayout的刷新回调接口
    //开出SwipeRefreshLayout的刷新接口
    SwipeRefreshLayoutRefresh swipeRefreshLayoutRefresh;
    public interface SwipeRefreshLayoutRefresh {
        void swipeRefreshLayoutOnRefresh();
    }

    @Override
    public void onRefresh() {
        //由外界具体执行swipeRefreshLayout的刷新方案
        swipeRefreshLayoutRefresh.swipeRefreshLayoutOnRefresh();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //当更新的时候，recyclerView不可滚动，可以有效的阻止bug
        return swipeRefreshLayout.isRefreshing();
    }
}
