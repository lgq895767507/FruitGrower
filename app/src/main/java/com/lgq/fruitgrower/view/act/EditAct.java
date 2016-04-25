package com.lgq.fruitgrower.view.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.constance.Constance;
import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditAct extends AppCompatActivity {

    private EditText tv_content;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tv_content = (EditText) findViewById(R.id.tv_content);


        bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        showContent();
        intent = new Intent(this, OwerDetailsAct.class);

    }

    private void showContent() {
        if (bundle == null) {
            Log.i("lgq", "bundle:" + bundle);
            return;
        }
        if (Constance.nickname.equals(bundle.getString(Constance.nickname))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, Constance.nickname, ""));
        }
        if (Constance.signature.equals(bundle.getString(Constance.signature))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, Constance.signature, ""));
        }
        if (Constance.phone.equals(bundle.getString(Constance.phone))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, Constance.phone, ""));
        }
        if (Constance.address.equals(bundle.getString(Constance.address))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, Constance.address, ""));
        }
    }

    private void savaData() {
        if (bundle == null) {
            Log.i("lgq", "savaData bundle:" + bundle);
            return;
        }
        Log.i("lgq", "savaData bundle:" + bundle.getString(Constance.nickname));
        String getContent = tv_content.getText().toString();
        if (getContent == null) {
            return;
        }
        if (Constance.nickname.equals(bundle.getString(Constance.nickname))) {
            SharePreUtils.setSharePre(this, Constance.nickname, getContent);
            Log.i("lgq","ffff"+getContent);
        }
        if (Constance.signature.equals(bundle.getString(Constance.signature))) {
            SharePreUtils.setSharePre(this, Constance.signature, getContent);
        }
        if (Constance.phone.equals(bundle.getString(Constance.phone))) {
            SharePreUtils.setSharePre(this, Constance.phone, getContent);
        }
        if (Constance.address.equals(bundle.getString(Constance.address))) {
            SharePreUtils.setSharePre(this, Constance.address, getContent);
        }
    }

    @Override
    public void onBackPressed() {
        savaData();
        super.onBackPressed();
    }
}
