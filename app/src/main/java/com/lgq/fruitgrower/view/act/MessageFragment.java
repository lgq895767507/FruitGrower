package com.lgq.fruitgrower.view.act;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.view.base.BaseFragment;

public class MessageFragment extends BaseFragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.activity_message, null);
        return view;
    }
}
