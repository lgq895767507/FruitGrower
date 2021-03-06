package com.lgq.fruitgrower.view.act;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.UserLogin;
import com.lgq.fruitgrower.model.constance.Constance;
import com.lgq.fruitgrower.model.servers.login.Login;
import com.lgq.fruitgrower.view.MainActivity;
import com.lgq.fruitgrower.view.base.BaseAct;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseAct implements View.OnClickListener{

    private TextView btn_sign_email;
    private TextView btn_sign_phone;
    private TextView email;
    private TextView password;
    private Button email_sign_in_button;

  //  private TextView test_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //init view
        initView();

        //设置监听事件
        btn_sign_email.setOnClickListener(this);
        email_sign_in_button.setOnClickListener(this);
        btn_sign_phone.setOnClickListener(this);
    }

    private void initView() {
        btn_sign_email = (TextView) findViewById(R.id.btn_sign_email);
        btn_sign_phone = (TextView) findViewById(R.id.btn_sign_phone);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);

      //  test_web = (TextView) findViewById(R.id.test_web);

     //   String webLinkText = "<a href=http://www.baidu.com>百度一下</a>" ;
     //   test_web.setText(Html.fromHtml(webLinkText));
     //   test_web.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_email:
                //设置字体颜色的改变
                btn_sign_email.setTextColor(Color.GRAY);
                intent2Activity(SignActivity.class);

                break;
            case R.id.btn_sign_phone:
                btn_sign_phone.setTextColor(Color.GREEN);
                ToastUtils.showToast(this, "短信注册需要一定的费用，如果你有兴趣不妨小额的支持我，敬请期待。", Toast.LENGTH_LONG);
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
                //登陆成功的email，全局使用,还是必须写到sharePreference中

                ToastUtils.showToast(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT);

                //change aoto login values
                setSharePre();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(getApplicationContext(), "登陆失败:用户名或者密码不对"+s+i, Toast.LENGTH_SHORT);
            }
        });
    }

    private void setSharePre(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constance.password, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.putBoolean(Constance.LOGINVERIFIED, true );
        editor.putString(Constance.LOGINEMAIL, email.getText().toString());
        editor.commit();
    }
}
