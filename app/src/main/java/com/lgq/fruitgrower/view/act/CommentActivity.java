package com.lgq.fruitgrower.view.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.view.MainActivity;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import cn.bmob.v3.listener.UpdateListener;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_comment;

    private ImageButton Ib_photo;
    private ImageButton Ib_contact;
    private ImageButton Ib_emojy;
    private ImageButton Ib_send;

    private Bundle bundle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.comtent));
        setSupportActionBar(toolbar);

        bundle = new Bundle();

        et_comment = (EditText) findViewById(R.id.et_comment);
        Ib_photo = (ImageButton) findViewById(R.id.Ib_photo);
        Ib_contact = (ImageButton) findViewById(R.id.Ib_contact);
        Ib_emojy = (ImageButton) findViewById(R.id.Ib_emojy);
        Ib_send = (ImageButton) findViewById(R.id.Ib_send);

        Ib_photo.setOnClickListener(this);
        Ib_contact.setOnClickListener(this);
        Ib_emojy.setOnClickListener(this);
        Ib_send.setOnClickListener(this);

        //自动显示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(et_comment.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Ib_photo:
                ToastUtils.showToast(this,"Ib_photo", Toast.LENGTH_SHORT);
                break;
            case R.id.Ib_contact:
                ToastUtils.showToast(this,"Ib_contact", Toast.LENGTH_SHORT);
                break;
            case R.id.Ib_emojy:
                ToastUtils.showToast(this,"Ib_emojy", Toast.LENGTH_SHORT);
                break;
            case R.id.Ib_send:

                if (et_comment.getText().toString().isEmpty()){
                    break;
                }

                bundle = this.getIntent().getExtras();
                if (bundle == null){
                    break;
                }
                Thread sendThread = new Thread(myRunnable);
                sendThread.start();

                finish();
                break;
        }
    }

    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            Pubilsh pubilsh = new Pubilsh();
            pubilsh.setComent(et_comment.getText().toString());
            pubilsh.update(getApplicationContext(), bundle.getString("ObjectId"), new UpdateListener() {
                @Override
                public void onSuccess() {
                    ToastUtils.showToast(getApplicationContext(), "已评论", Toast.LENGTH_SHORT);
                }

                @Override
                public void onFailure(int i, String s) {
                    ToastUtils.showToast(getApplicationContext(),"出错啦！"+i+s, Toast.LENGTH_SHORT);
                }
            });
        }
    };

}
