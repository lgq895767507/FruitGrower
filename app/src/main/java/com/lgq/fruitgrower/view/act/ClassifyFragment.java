package com.lgq.fruitgrower.view.act;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.view.adapter.GridLocAdapter;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.ArrayList;

public class ClassifyFragment extends BaseFragment {
    private View view;
    private GridView gridView;
    private GridLocAdapter gridLocAdapter;
    private ArrayList<String> locNames;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        return view;
    }

    private void loadData() {
        for (int i =0 ; i<10;i++) {
            locNames.add("a"+i);
        }
    }

    private void initView() {
        view = View.inflate(activity, R.layout.activity_classify,null);
        gridView = (GridView) view.findViewById(R.id.gridLoc);
        locNames = new ArrayList<String>();
        loadData();
        gridLocAdapter = new GridLocAdapter(getContext(),locNames);
        gridView.setAdapter(gridLocAdapter);
        gridView.setOnItemClickListener(new ItemClickListener());
    }
    class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String data = (String)adapterView.getItemAtPosition(i);
            ToastUtils.showToast(getContext(),data, Toast.LENGTH_SHORT);
        }
    }
}
