package com.lgq.fruitgrower.view.act;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.model.beans.UserLogin;
import com.lgq.fruitgrower.view.base.BaseAct;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.listener.SaveListener;

public class SignActivity extends BaseAct {


    private TextView email;
    private TextView password;
    private Button btn_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        //init view
        initView();

    }

    private void initView() {
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        btn_sign = (Button) findViewById(R.id.btn_sign);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignOnclick();
            }
        });
    }

    public void btnSignOnclick() {

        UserLogin userLogin = new UserLogin();
        //设置邮箱
        if (email.getText().toString().isEmpty()) {
            ToastUtils.showToast(this, "请输入邮箱", Toast.LENGTH_SHORT);
            return;
        }
        if (!isEmail(email.getText().toString())) {
            ToastUtils.showToast(this, "邮箱地址错误", Toast.LENGTH_SHORT);
            return;
        }
        userLogin.setUsername(email.getText().toString());
        //设置邮箱
        userLogin.setEmail(email.getText().toString());
        //设置密码
        if (password.getText().length() < 6) {
            ToastUtils.showToast(this, "请入密码，至少6个字符", Toast.LENGTH_SHORT);
            return;
        } else if (password.getText().length() > 12) {
            ToastUtils.showToast(this, "密码不能多于12个字符", Toast.LENGTH_SHORT);
            return;
        }
        userLogin.setPassword(password.getText().toString());
        //注册

        userLogin.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                //save email to consumer form if  login success
                saveEtoC(email.getText().toString());
                intent2Activity(LoginActivity.class);
                ToastUtils.showToast(getApplicationContext(), "注册成功，请到邮箱验证！", Toast.LENGTH_LONG);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(getApplicationContext(), "注册失败" + i+s, Toast.LENGTH_SHORT);
                Log.i("lgq", "sss+++" + s);
            }
        });

    }

    private void saveEtoC(String emails){
        Consumer consumer = new Consumer();
        consumer.setEmail(emails);
        consumer.setName(emails);
        consumer.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("lgq","save consumer email success");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("lgq","save email false"+i);
            }
        });

        //add email to pubilsh
        Pubilsh pubilsh = new Pubilsh();
        pubilsh.setEmail(emails);
        pubilsh.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("lgq","save pubilsh email success");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("lgq","save email false"+i);
            }
        });
    }

    // 判断格式是否为email
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onBackPressed() {
        intent2Activity(LoginActivity.class);
        super.onBackPressed();
        finish();
    }
}
