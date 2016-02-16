package com.lgq.fruitgrower.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.view.base.BaseFragment;

import java.util.zip.Inflater;

public class OwnerFragment extends BaseFragment {

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity,R.layout.activity_owner,null);
        return view;
    }
}
