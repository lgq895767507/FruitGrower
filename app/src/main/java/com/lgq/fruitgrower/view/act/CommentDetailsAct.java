package com.lgq.fruitgrower.view.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.model.constance.Constance;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CommentDetailsAct extends AppCompatActivity {

    private String TAG = "lgq1";
    private String bundleBObject;

    private TextView tv_content;
    private ImageView iv_image;
    private TextView tv_share_num;
    private TextView tv_comment_number;
    private TextView tv_like_num;
    private FrameLayout include_status_image;

    private ImageView iv_avatar;
    private TextView tv_subhead;
    private TextView tv_caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("详情界面");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bundleBObject = intent.getStringExtra("ONOBJECTID");
        Log.i(TAG, "intent" + intent.getStringExtra("ONOBJECTID"));

        //init view
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_share_num = (TextView) findViewById(R.id.tv_share_num);
        tv_comment_number = (TextView) findViewById(R.id.tv_comment_number);
        tv_like_num = (TextView) findViewById(R.id.tv_like_num);
        include_status_image = (FrameLayout) findViewById(R.id.include_status_image);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_subhead = (TextView) findViewById(R.id.tv_subhead);
        tv_caption = (TextView) findViewById(R.id.tv_caption);

        showView();
    }

    private void showView() {
        BmobQuery<Pubilsh> query = new BmobQuery<Pubilsh>();
        query.addWhereEqualTo("objectId", bundleBObject);
        query.findObjects(this, new FindListener<Pubilsh>() {
            @Override
            public void onSuccess(List<Pubilsh> list) {

                //设置头像
                if (list.get(0).getImg() != null) {
                    Glide.with(getApplication())
                            .load(list.get(0).getPhoto().getFileUrl(getApplicationContext()))
                            .into(iv_avatar);
                } else {
                    Bitmap defaultImg = BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.logo);
                    iv_avatar.setImageBitmap(defaultImg);
                }
                //设置名字
                if (list.get(0).getName() != null){
                    tv_subhead.setText(list.get(0).getName());
                }else{
                    tv_subhead.setText(list.get(0).getEmail());
                }
                //设置发布时间
                tv_caption.setText(list.get(0).getCreatedAt());

                if (list.get(0).getContent() != null) {
                    tv_content.setText(list.get(0).getContent());
                }
                //设置图片内容,并缓存图片和缩略图片，增强应用的性能增强。
                if (list.get(0).getPhoto() != null) {
                    Glide.with(getApplicationContext())
                            .load(list.get(0).getPhoto().getFileUrl(getApplicationContext()))
                            .thumbnail(Constance.SizeHalf)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iv_image);
                    include_status_image.setVisibility(View.VISIBLE);
                    iv_image.setVisibility(View.VISIBLE);
                } else {
                    include_status_image.setVisibility(View.GONE);
                    iv_image.setVisibility(View.GONE);
                }
                if (list.get(0).getLiked() != 0){
                    Log.i(TAG,"likedNumber:"+list.get(0).getLiked());
                    tv_like_num.setText(""+list.get(0).getLiked());
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "CommentDeatilsAct onError" + i + s);
            }
        });
    }


}
