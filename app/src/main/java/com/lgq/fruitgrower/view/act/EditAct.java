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
    Consumer consumer = new Consumer();
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
            Log.i("lgq","bundle:"+bundle);
            return;
        }
        if ("nickname".equals(bundle.getString("nickname"))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, "nickname", ""));
        } else if ("signature".equals(bundle.getString("signature"))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, "signature", ""));
        } else if ("phone".equals(bundle.getString("phone"))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, "phone", ""));
        } else if ("address".equals(bundle.getString("address"))) {
            tv_content.setText(SharePreUtils.getEmailPre(this, "address", ""));
        }
    }

    private void savaData() {
        if (bundle == null) {
            return;
        }
        String getContent = tv_content.getText().toString();
        if (getContent == null) {
            return;
        }
        if ("nickname".equals(bundle.getString("nickname"))) {
            SharePreUtils.setSharePre(this, "nickname", getContent);
        } else if ("signature".equals(bundle.getString("signature"))) {
            SharePreUtils.setSharePre(this, "signature", getContent);
        } else if ("phone".equals(bundle.getString("phone"))) {
            SharePreUtils.setSharePre(this, "phone", getContent);
        } else if ("address".equals(bundle.getString("address"))) {
            SharePreUtils.setSharePre(this, "address", getContent);
        }
    }

    @Override
    public void onBackPressed() {
        savaData();
        super.onBackPressed();
        finish();
    }

//    private void saveData(final int resultCode) {
//        //查找对应的objectId,注意：修改数据只能通过objectId来修改，目前不提供查询条件方式的修改方法。
//
//        BmobQuery<Consumer> query = new BmobQuery<Consumer>();
//        query.addWhereEqualTo("email", getSharePre());
//        query.findObjects(getApplicationContext(), new FindListener<Consumer>() {
//            @Override
//            public void onSuccess(List<Consumer> list) {
//                String ObjId = "";
//                for (Consumer consumer : list) {
//                    ObjId = consumer.getObjectId();
//                }
//                if (ObjId != "" || ObjId != null) {
//                    updateData(resultCode, ObjId);
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
//
//        setResult(resultCode, intent);
//
//    }
//
//    private void updateData(int resultCode, final String ObjId) {
//        if (resultCode == 1) {
//            consumer.setName(tv_content.getText().toString());
//        } else if (resultCode == 2) {
//            consumer.setSignature(tv_content.getText().toString());
//        } else if (resultCode == 3) {
//            consumer.setPhone(tv_content.getText().toString());
//        } else if (resultCode == 4) {
//            consumer.setAddress(tv_content.getText().toString());
//        }
//        consumer.update(getApplicationContext(), ObjId, new UpdateListener() {
//            @Override
//            public void onSuccess() {
//                ToastUtils.showToast(getApplicationContext(), "更改数据成功", Toast.LENGTH_SHORT);
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Log.i("lgq", "aaa+" + s);
//                ToastUtils.showToast(getApplicationContext(), "更改数据失败" + i, Toast.LENGTH_SHORT);
//            }
//        });
//    }


}
