package com.lgq.fruitgrower.view.act;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.view.adapter.GridLocAdapter;
import com.lgq.fruitgrower.view.base.BaseFragment;
import com.lgq.fruitgrower.view.utils.ToastUtils;
import com.lgq.fruitgrower.view.widget.WrapHeightGridView;


import java.util.ArrayList;

public class ClassifyFragment extends BaseFragment implements LocationSource,AMapLocationListener{
    private View view ;
    private WrapHeightGridView gridView;
    private GridLocAdapter gridLocAdapter;
    private ArrayList<String> locNames;
    //定位
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    //map
    private MapView mapView;
    private AMap aMap;

    private TextView tv_loc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        mapView = (MapView) view.findViewById(R.id.map);

        setLayer();

        mapView.onCreate(savedInstanceState);// 必须要写
        return view;
    }

    private void loadData() {
        for (int i =0 ; i<10;i++) {
            locNames.add("a"+i);
        }
    }
// map  必须重载这些方法


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
//
    private void initView() {

        //map init

        //在onCreat方法中给aMap对象赋值

        view = View.inflate(activity, R.layout.activity_classify,null);
        tv_loc = (TextView) view.findViewById(R.id.tv_loc);

//        gridView = (WrapHeightGridView) view.findViewById(R.id.gridLoc);
//        locNames = new ArrayList<String>();
//        loadData();
//        gridLocAdapter = new GridLocAdapter(getContext(),locNames);
//        gridView.setAdapter(gridLocAdapter);
//        gridLocAdapter.notifyDataSetChanged();
//        gridView.setOnItemClickListener(new ItemClickListener());
    }
    /*
    * 激活定位
    * */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
//设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
//设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
//设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
//设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(2000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();//停止定位
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 选择矢量地图/卫星地图/夜景地图/导航地图事件的响应
     */
    private void setLayer() {
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

//// 设置定位的类型为定位模式
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//
//// 设置定位的类型为 跟随模式
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
//
//// 设置定位的类型为根据地图面向方向旋转
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                tv_loc.setText(amapLocation.getAddress());
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                ToastUtils.showToast(getContext(),"定位失败,请检查您的网络",Toast.LENGTH_SHORT);
                Log.e("AmapErr", errText);
            }
        }
    }


//    class ItemClickListener implements AdapterView.OnItemClickListener{
//
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            String data = (String)adapterView.getItemAtPosition(i);
//            ToastUtils.showToast(getContext(),data, Toast.LENGTH_SHORT);
//        }
//    }
}
