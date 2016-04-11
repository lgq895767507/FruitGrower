package com.lgq.fruitgrower.view.act;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;


import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.model.constance.Constance;
import com.lgq.fruitgrower.view.MainActivity;

import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;


import java.io.BufferedOutputStream;


import java.io.File;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;


import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PublicActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView tv_content;
    private Pubilsh publisher;
    private Intent intent;

    private RadioGroup rg_pub;
    private ImageView Im_photo;
    private ImageView Im_close;

    //radiobutton
    private RadioButton rb_photo;
    private RadioButton rb_about;
    private RadioButton rb_emoji;
    private RadioButton rb_add;

    private final static int SELECT_PICTURE = 0;
    private final static int SELECT_CAMER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        initView();

    }

    private void publicContent() {
        if (tv_content.getText().toString().isEmpty()) {
            ToastUtils.showToast(getApplicationContext(), "没有发布任何消息", Toast.LENGTH_SHORT);
        } else {
            publisher.setContent(tv_content.getText().toString());
            if (getIntent() == null) {
                ToastUtils.showToast(getApplicationContext(), "getIntent()为空", Toast.LENGTH_SHORT);
                return;
            }
            Bundle bundle = getIntent().getExtras();
            publisher.setIsFarmer(bundle.getBoolean("farmer"));
            publisher.setEmail(SharePreUtils.getEmailPre(this));
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
        Im_photo = (ImageView) findViewById(R.id.Im_photo);
        rg_pub = (RadioGroup) findViewById(R.id.rg_pub);
        Im_close = (ImageView) findViewById(R.id.Im_close);
        //radiobutton
        rb_photo = (RadioButton) findViewById(R.id.rb_photo);
        rb_about = (RadioButton) findViewById(R.id.rb_about);
        rb_emoji = (RadioButton) findViewById(R.id.rb_emoji);
        rb_add = (RadioButton) findViewById(R.id.rb_add);

        rg_pub.setOnCheckedChangeListener(this);
        Im_close.setOnClickListener(listener);
        publisher = new Pubilsh();
        //自动显示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(tv_content.getWindowToken(), 0);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Im_photo.setVisibility(View.GONE);
            Im_close.setVisibility(View.GONE);

        }
    };

    @Override
    public void onBackPressed() {
        publicContent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_photo:
                selectPhoto();
                ToastUtils.showToast(getApplicationContext(), "rb_photo", Toast.LENGTH_SHORT);
                rb_photo.setChecked(false);
                break;
            case R.id.rb_about:
                ToastUtils.showToast(getApplicationContext(), "rb_about", Toast.LENGTH_SHORT);
                rb_about.setChecked(false);
                break;
            case R.id.rb_emoji:
                ToastUtils.showToast(getApplicationContext(), "rb_emoji", Toast.LENGTH_SHORT);
                rb_emoji.setChecked(false);
                break;
            case R.id.rb_add:
                ToastUtils.showToast(getApplicationContext(), "rb_add", Toast.LENGTH_SHORT);
                rb_add.setChecked(false);
                break;
            default:
                break;
        }
    }

    private void selectPhoto() {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == SELECT_PICTURE) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");//相片类型
                            startActivityForResult(intent, SELECT_PICTURE);
                        } else {
                            String state = Environment.getExternalStorageState();
                            if (state.equals(Environment.MEDIA_MOUNTED)) {
                                Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                                startActivityForResult(getImageByCamera, SELECT_CAMER);
                            } else {
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
                String[] proj = {MediaStore.Images.Media.DATA};
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
                Im_photo.setImageBitmap(bmp);
                Im_photo.setVisibility(View.VISIBLE);
                Im_close.setVisibility(View.VISIBLE);

                //保存图片，上传图片
                uploadImg(path);


            }
        } else if (requestCode == SELECT_CAMER) {
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
                        String spath = "/sdcard/DCIM/Camera/" + strDate + ".jpg";


                        saveImage(photo, spath);
                        Im_photo.setImageBitmap(photo);
                        Im_photo.setVisibility(View.VISIBLE);
                        Im_close.setVisibility(View.VISIBLE);

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

    private void saveImage(Bitmap photo, String spath) {
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

    private void uploadImg(String path) {
        BmobFile bmobFile = new BmobFile(new File(path));
        publisher.setPhoto(bmobFile);
        bmobFile.uploadblock(getApplicationContext(), new UploadFileListener() {
            @Override
            public void onSuccess() {

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
    }


}
