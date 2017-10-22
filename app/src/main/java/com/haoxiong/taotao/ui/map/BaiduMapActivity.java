package com.haoxiong.taotao.ui.map;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.eventbean.MessageEvent;
import com.haoxiong.taotao.ui.sendredpacket.ChildSendRedPacketActivity;
import com.haoxiong.taotao.ui.sendredpacket.SendRedPacketActivity;
import com.haoxiong.taotao.util.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaiduMapActivity extends BaseActivity {

    private static Intent intent;
    @BindView(R.id.liner_map_back)
    LinearLayout linerMapBack;
    @BindView(R.id.liner_map_save)
    LinearLayout linerMapSave;
    @BindView(R.id.activity_baidu_map)
    RelativeLayout activityBaiduMap;
    private MapView mMapView;
    private AMap mBaiduMap;
    private Marker marker;
    private Circle circle;
    private int distance;
    private Intent data;
    private String address;
    private LatLonPoint latLng;

    /*    private PopupRecyclerAdapter adapter;
        private List<PoiInfo> allPoi;*/
    public static void luncher(Activity context, int distance, int requestCode, LatLonPoint latLng, String address) {
        intent = new Intent(context, BaiduMapActivity.class);
        intent.putExtra(SendRedPacketActivity.EXTRA_TITLE, requestCode);
        intent.putExtra(SendRedPacketActivity.EXTRA_DISTANCE, distance);
        intent.putExtra("latLng", latLng);
        intent.putExtra("address", address);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);

        ButterKnife.bind(this);
        mMapView = (MapView) findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        mMapView.onCreate(savedInstanceState);
        assignView();
    }
    private void assignView() {
        dismissProgressDialog();
        distance = getIntent().getIntExtra(SendRedPacketActivity.EXTRA_DISTANCE, 1000);
        latLng = getIntent().getParcelableExtra("latLng");
        address = getIntent().getStringExtra("address");
        distance = getIntent().getIntExtra(SendRedPacketActivity.EXTRA_DISTANCE, 1000);

        //普通地图
        mBaiduMap.setMapType(AMap.MAP_TYPE_NORMAL);
        mBaiduMap.setTrafficEnabled(true);
        setCenterPosition(latLng);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    private void setCenterPosition(LatLonPoint cenpt) {
        LatLng latLng = new LatLng(cenpt.getLatitude(),cenpt.getLongitude());
        //设置中心点和缩放比例
        mBaiduMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        mBaiduMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //定义Maker坐标点
        setMarker(cenpt);
    }


    private void setMarker(final LatLonPoint point) {
        final LatLng postion = new LatLng(point.getLatitude(),point.getLongitude());
        //构建Marker图标
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (marker != null) {
                    marker.setPosition(postion);
                } else {
                    marker = mBaiduMap.addMarker(new MarkerOptions().position(postion).title(address).snippet("DefultMarker"));
                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.position(postion);
                    markerOption.title("address").snippet("");

                    markerOption.draggable(false);//设置Marker可拖动
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),R.drawable.ic_local)));
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    markerOption.setFlat(true);//设置marker平贴地图效果
                }

                if (circle != null) {
                    circle.setCenter(postion);
                } else {
                    circle = mBaiduMap.addCircle(new CircleOptions().
                            center(postion)
                            .fillColor(Color.parseColor("#00D53E35"))
                            .radius(distance)
                            .strokeColor(Color.parseColor("#D53E35"))
                            .strokeWidth(2));
                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @OnClick({R.id.liner_map_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_map_back:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        back();
    }
    public void back() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setIcon(R.drawable.ic_logo)
                .setMessage("是否保存该定位？")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save();
                    }
                })
                .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new Intent("finish_map"));
                        finish();
                    }
                })
                .show();
    }
    @OnClick(R.id.liner_map_save)
    public void onClick() {
        save();
    }

    private void save() {
        SharePreferenceUtil.put(BaiduMapActivity.this, "lng",latLng.getLongitude()+"");
        SharePreferenceUtil.put(BaiduMapActivity.this, "lat",latLng.getLatitude()+"");
        SharePreferenceUtil.put(BaiduMapActivity.this, "mapaddress",address+"");
        MessageEvent messageEvent = new MessageEvent(address, latLng);
        EventBus.getDefault().post(new Intent("finish_map"));
        EventBus.getDefault().post(messageEvent);
        finish();
    }
}
