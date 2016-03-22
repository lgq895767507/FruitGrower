package com.lgq.fruitgrower.view.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.view.MainActivity;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class OwerDetailsAct extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_head;
    private ImageView img_subhead;

    private RelativeLayout rl_nickname;
    private TextView tv_nickname;

    private RelativeLayout rl_signature;
    private TextView tv_signature;

    private RelativeLayout rl_phone;
    private TextView tv_phone;

    private RelativeLayout rl_address;
    private TextView tv_address;

    private Consumer consumer;

    private final static int SELECT_PICTURE = 0;
    private final static int SELECT_CAMER = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ower_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init view
        initView();

    }

    private void initView(){
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        rl_nickname = (RelativeLayout) findViewById(R.id.rl_nickname);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);

        img_subhead = (ImageView) findViewById(R.id.img_subhead);

        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);

        consumer = new Consumer();

        //setonclick
        rl_head.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_address.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_head:
                selectPhoto();
                break;
            case R.id.rl_nickname:
                break;
            case R.id.rl_signature:
                break;
            case R.id.rl_phone:
                break;
            case R.id.rl_address:
                break;
        }
    }

    private void selectPhoto() {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if( which == SELECT_PICTURE ){
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");//相片类型
                            startActivityForResult(intent, SELECT_PICTURE);
                        }else{
                            String state = Environment.getExternalStorageState();
                            if (state.equals(Environment.MEDIA_MOUNTED)) {
                                Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                                startActivityForResult(getImageByCamera, SELECT_CAMER);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                Uri mImageCaptureUri = data.getData();
                Bitmap bmp;
                String [] proj={MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(mImageCaptureUri,
                        proj,                 // Which columns to return
                        null,       // WHERE clause; which rows to return (all rows)
                        null,       // WHERE clause selection arguments (none)
                        null);                 // Order-by clause (ascending by name)

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                String path = cursor.getString(column_index);
                cursor.close();
                bmp = BitmapFactory.decodeFile(path);
                img_subhead.setImageBitmap(bmp);



                //保存图片，上传图片
                uploadImg(path);



            }
        }else if (requestCode == SELECT_CAMER) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap

                        //取得当前日期时间
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String strDate = formatter.format(curDate);
                        //spath :生成图片取个名字和路径包含类型
                        String spath = "/sdcard/DCIM/Camera/"+strDate+".jpg";


                        saveImage(photo, spath);
                        img_subhead.setImageBitmap(photo);


                        //保存图片，上传图片
                        uploadImg(spath);

                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    //to do find the path of pic by uri
                }
            }
        }
    }
    private  void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadImg(String path){
        BmobFile bmobFile = new BmobFile(new File(path));
        consumer.setImg(bmobFile);
        bmobFile.uploadblock(getApplicationContext(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showToast(getApplicationContext(), "上传数据成功" , Toast.LENGTH_SHORT);
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(getApplicationContext(), "上传数据失败" + s, Toast.LENGTH_SHORT);
            }
        });
        consumer.save(getApplicationContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showToast(getApplicationContext(), "发布数据成功", Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(getApplicationContext(), "添加数据失败" + s, Toast.LENGTH_SHORT);
            }
        });
    }
}
