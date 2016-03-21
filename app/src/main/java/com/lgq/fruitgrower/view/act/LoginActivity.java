package com.lgq.fruitgrower.view.act;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.UserLogin;
import com.lgq.fruitgrower.model.servers.login.Login;
import com.lgq.fruitgrower.view.MainActivity;
import com.lgq.fruitgrower.view.base.BaseAct;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseAct implements View.OnClickListener{

    private Button btn_sign;
    private TextView email;
    private TextView password;
    private Button email_sign_in_button;

    //sharePreference auto login
    private static  boolean LOGINVERIFIED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //init view
        initView();

        //设置监听事件
        btn_sign.setOnClickListener(this);
        email_sign_in_button.setOnClickListener(this);

    }

    private void initView() {
        btn_sign = (Button) findViewById(R.id.btn_sign);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign:
                intent2Activity(SignActivity.class);
                finish();
                break;
            case R.id.email_sign_in_button:
                login();
                break;
        }

    }



    private void login() {
        UserLogin userLogin = new UserLogin();
        if (email.getText().toString().isEmpty()){
            ToastUtils.showToast(getApplicationContext(), "没有输入邮箱账号", Toast.LENGTH_SHORT);
            return;
        }
        if (password.getText().toString().isEmpty()){
            ToastUtils.showToast(getApplicationContext(),"没有输入密码", Toast.LENGTH_SHORT);
            return;
        }

        userLogin.setUsername(email.getText().toString());
        userLogin.setPassword(password.getText().toString());




        userLogin.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                intent2Activity(MainActivity.class);
                ToastUtils.showToast(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT);

                //change aoto login values
                setSharePre();

                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(getApplicationContext(), "登陆失败:"+i, Toast.LENGTH_SHORT);
            }
        });
    }

    private void setSharePre(){
        LOGINVERIFIED = true;
        SharedPreferences sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.putBoolean("LOGINVERIFIED",LOGINVERIFIED);
        editor.commit();
    }
}
