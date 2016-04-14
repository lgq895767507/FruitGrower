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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bumptech.glide.Glide;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.model.constance.Constance;
import com.lgq.fruitgrower.model.servers.login.ConsumerServers;
import com.lgq.fruitgrower.model.servers.login.IDataCallBack;
import com.lgq.fruitgrower.view.utils.SharePreUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
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

    private Intent intent;

    //对应email的id
    private static String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ower_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i("lgq", "onCreate");
        //init view
        initView();

    }

    private void initView() {
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

        //第一次加载的网络的时候onCreate显示网络数据
        if (SharePreUtils.getEmailPre(this, Constance.ONFLAG, true)) {
            //直接先执行业务
            consumerServer();
            showView();
            //保存在sharePre中,先加个加载标识flag
            SharePreUtils.setSharePre(this, Constance.ONFLAG, false);
        }

        //setonclick
        rl_head.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_address.setOnClickListener(this);

        //开启子线程来上传数据
        Thread myThread = new Thread(myRunable);
        myThread.start();
    }

    //load sharePre
    private void showLocView() {
        Glide.with(getApplicationContext())
                .load(SharePreUtils.getEmailPre(this, Constance.imgHeadPath, ""))
                .into(img_subhead);
        tv_nickname.setText(SharePreUtils.getEmailPre(this, Constance.nickname, SharePreUtils.getEmailPre(this)));

        tv_signature.setText(SharePreUtils.getEmailPre(this,Constance.signature, ""));
        tv_phone.setText(SharePreUtils.getEmailPre(this, Constance.phone, ""));
        tv_address.setText(SharePreUtils.getEmailPre(this,Constance.address, ""));
    }

    //update network
    @Override
    protected void onPause() {
        //update data to network
        //      updateData();
        Log.i("lgq", "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        //if not intent connect,show local view
        showLocView();
        Log.i("lgq", "onResume");
        //开启子线程来上传数据
        Thread myThread = new Thread(myRunable);
        myThread.start();

        super.onResume();
    }
    private void showView() {
        if (consumer.getImg() != null) {
            Glide.with(getApplicationContext())
                    .load(consumer.getImg().getFileUrl(getApplicationContext()))
                    .into(img_subhead);
        } else {
            Bitmap defaultImg = BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.logo);
            img_subhead.setImageBitmap(defaultImg);
        }
        tv_nickname.setText(consumer.getName());
        tv_signature.setText(consumer.getSignature());
        tv_phone.setText(consumer.getPhone());
        tv_address.setText(consumer.getAddress());

        savaSharePre(consumer);
    }

    private void savaSharePre(Consumer consumer) {
        //图片这里可能还存在问题，因为这个url是网上的，所以保存下来肯定也是加载不出来的，想想怎么将图片缓存下来保存在本地。2016.3.16
        if (consumer.getImg() != null){
            SharePreUtils.setSharePre(this, Constance.imgHeadPath, consumer.getImg().getFileUrl(getApplication()));
        }else{
            SharePreUtils.setSharePre(this, Constance.imgHeadPath, "");
        }
        SharePreUtils.setSharePre(this, Constance.nickname, consumer.getName());
        SharePreUtils.setSharePre(this, Constance.signature, consumer.getSignature());
        SharePreUtils.setSharePre(this, Constance.phone, consumer.getPhone());
        SharePreUtils.setSharePre(this, Constance.address, consumer.getAddress());
    }

    @Override
    public void onClick(View v) {
        Bundle tag = new Bundle();
        intent = new Intent(this, EditAct.class);
        switch (v.getId()) {
            case R.id.rl_head:
                selectPhoto();
                break;
            case R.id.rl_nickname:
                tag.putString(Constance.nickname, Constance.nickname);
                intent.putExtras(tag);
                startActivity(intent);
                break;
            case R.id.rl_signature:
                tag.putString(Constance.signature, Constance.signature);
                intent.putExtras(tag);
                startActivity(intent);
                break;
            case R.id.rl_phone:
                tag.putString(Constance.phone,Constance.phone);
                intent.putExtras(tag);
                startActivity(intent);
                break;
            case R.id.rl_address:
                tag.putString(Constance.address, Constance.address);
                intent.putExtras(tag);
                startActivity(intent);
                break;
        }
    }

    private void selectPhoto() {
        CharSequence[] items = {getString(R.string.photo), getString(R.string.camera)};
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
        Log.i("lgq","onActivityResult");
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
                img_subhead.setImageBitmap(bmp);


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
        BmobProFile bmobFile = BmobProFile.getInstance(this);
        //保存图片路径到SharePre
        SharePreUtils.setSharePre(this,Constance.imgHeadPath, path);
        Log.i("lgq", "uploadImg");

        bmobFile.upload(path, new UploadListener() {
            @Override
            public void onSuccess(String s, String s1, BmobFile bmobFile) {
                //保存图片
                consumer.setImg(bmobFile);

                ToastUtils.showToast(getApplication(), "上传成功", Toast.LENGTH_SHORT);
            }

            @Override
            public void onProgress(int i) {

            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(getApplication(), "上传失败"+i+":"+s, Toast.LENGTH_SHORT);
            }
        });

    }

    //处理业务
    private void consumerServer() {
        //初始化业务
        ConsumerServers consumerServers = new ConsumerServers(this);
        consumerServers.setSelectByIdIDataCallBack(selectIDataCallBack);
        //实现业务
        consumerServers.selectByEmail();
    }

    //业务实现回调
    IDataCallBack<Consumer> selectIDataCallBack = new IDataCallBack<Consumer>() {
        @Override
        public void dataOnsuccess(List<Consumer> objects) {
            for (Consumer object : objects) {
                Log.i("lgq", "obj:" + object.getName() + objects.size());
            }
            objectId = objects.get(0).getObjectId();
            consumer = objects.get(0);
            ToastUtils.showToast(getApplication(), "加载成功...", Toast.LENGTH_SHORT);
        }

        @Override
        public void dataOnEmpty() {
            ToastUtils.showToast(getApplication(), "loading empty", Toast.LENGTH_SHORT);
        }

        @Override
        public void dataOnError(int code, String msg) {
            ToastUtils.showToast(getApplication(), "loading error:" + code + ":" + msg, Toast.LENGTH_SHORT);
        }
    };

    Runnable myRunable = new Runnable() {
        @Override
        public void run() {
            Log.i("lgq","run()");
            consumerServer();
            updateData();
        }
    };

    //更新数据
    private void updateData() {
        Log.i("lgq", "getID:::" + objectId+SharePreUtils.getEmailPre(this, Constance.imgHeadPath, ""));
        /* BmobFile bmobFile = new BmobFile(new File(SharePreUtils.getEmailPre(this, Constance.imgHeadPath, "")));
        consumer.setImg(bmobFile);*/
        //文件上传的顺序是，先
        consumer.setName(SharePreUtils.getEmailPre(this, Constance.nickname, ""));
        consumer.setSignature(SharePreUtils.getEmailPre(this, Constance.signature, ""));
        consumer.setPhone(SharePreUtils.getEmailPre(this, Constance.phone, ""));
        Log.i("lgq","address:"+SharePreUtils.getEmailPre(this, Constance.address, ""));
        consumer.setAddress(SharePreUtils.getEmailPre(this, Constance.address, ""));

        consumer.update(getApplicationContext(), objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showToast(getApplicationContext(), "更改数据成功", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(getApplicationContext(), "更改数据失败..." + i+s, Toast.LENGTH_SHORT);
            }
        });
    }

}
