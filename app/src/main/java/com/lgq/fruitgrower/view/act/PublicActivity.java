package com.lgq.fruitgrower.view.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.view.MainActivity;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import cn.bmob.v3.listener.SaveListener;

public class PublicActivity extends AppCompatActivity {

    private TextView tv_content;
    private Pubilsh publisher;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        initView();

    }

    private void publicContent() {
        if (tv_content.getText().toString().isEmpty()){
            ToastUtils.showToast(getApplicationContext(), "没有发布任何消息", Toast.LENGTH_SHORT);
        }else {
            publisher.setContent(tv_content.getText().toString());
            publisher.save(getApplicationContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    ToastUtils.showToast(getApplicationContext(), "发布数据成功", Toast.LENGTH_SHORT);
                    intent = new Intent(getApplication(), MainActivity.class);

                }

                @Override
                public void onFailure(int i, String s) {
                    ToastUtils.showToast(getApplicationContext(), "添加数据失败" + s, Toast.LENGTH_SHORT);
                }
            });
        }

    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        publisher = new Pubilsh();
        //自动显示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(tv_content.getWindowToken(),0);
    }

    @Override
    public void onBackPressed() {
        publicContent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
}
