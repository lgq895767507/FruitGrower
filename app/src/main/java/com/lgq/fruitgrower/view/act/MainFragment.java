package com.lgq.fruitgrower.view.act;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.view.base.BaseFragment;


public class MainFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(activity,R.layout.fragment_main,null);
    }
}
