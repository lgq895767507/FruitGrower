package com.lgq.fruitgrower.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Consumer;
import com.lgq.fruitgrower.view.act.FragmentController;
import com.lgq.fruitgrower.view.act.PublicActivity;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import org.json.JSONArray;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener
        ,NavigationView.OnNavigationItemSelectedListener {

    private RadioGroup rg_tab;
    private ImageView iv_add;
    private FragmentController controller;
    private Toolbar toolbar;

    private RelativeLayout rela_layout;

    private Button btn_farmer;
    private Button btn_business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // view init



        openDrawerToggle("首页");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设为竖屏
        controller = FragmentController.getInstance(this, R.id.fl_content);
        controller.showFragment(0);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        initView();





    }

    private void openDrawerToggle(String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initView() {
        rela_layout = (RelativeLayout) findViewById(R.id.rela_layout);

        rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        btn_farmer = (Button) findViewById(R.id.btn_farmer);
        btn_business = (Button) findViewById(R.id.btn_business);

        rg_tab.setOnCheckedChangeListener(this);
        iv_add.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (rela_layout != null && rela_layout.getVisibility() == View.VISIBLE){
            rela_layout.setVisibility(View.GONE);
            Log.i("lgq", "aaaaa");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                controller.showFragment(0);
                openDrawerToggle("首页");
                break;
            case R.id.rb_meassage:
                controller.showFragment(1);
                openDrawerToggle("分类");
                break;
            case R.id.rb_search:
                controller.showFragment(2);
                openDrawerToggle("消息");
                break;
            case R.id.rb_user:
                controller.showFragment(3);
                openDrawerToggle("我的");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
//                Intent intent = new Intent(this, PublicActivity.class);
//                startActivityForResult(intent, 1);
                if (rela_layout.getVisibility() == View.GONE) {
                    rela_layout.setVisibility(View.VISIBLE);
                    intoPublicAct();
                }

                break;

            default:
                break;
        }
    }

    private void intoPublicAct() {
        btn_farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PublicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("farmer",true);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                if (rela_layout.getVisibility() == View.VISIBLE) {
                    rela_layout.setVisibility(View.GONE);
                }
            }
        });
        btn_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PublicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("farmer",false);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                if (rela_layout.getVisibility() == View.VISIBLE) {
                    rela_layout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                break;
            default:
                break;
        }
    }

}
