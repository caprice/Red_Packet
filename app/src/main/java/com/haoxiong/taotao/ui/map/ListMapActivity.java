package com.haoxiong.taotao.ui.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.eventbean.MessageEvent;
import com.haoxiong.taotao.ui.map.adapter.PopupRecyclerAdapter;
import com.haoxiong.taotao.ui.sendredpacket.ChildSendRedPacketActivity;
import com.haoxiong.taotao.ui.sendredpacket.SendRedPacketActivity;
import com.haoxiong.taotao.util.KeyboardUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMapActivity extends BaseActivity {

    @BindView(R.id.liner_map_back)
    TextView linerMapBack;
    @BindView(R.id.tv_map_search)
    TextView tvMapSearch;
    @BindView(R.id.et_map_search)
    EditText etMapSearch;
    @BindView(R.id.rel_map_search)
    RelativeLayout relMapSearch;
    @BindView(R.id.recycle_map_search)
    RecyclerView recycleMapSearch;
    @BindView(R.id.activity_list_map)
    LinearLayout activityListMap;
    private PopupRecyclerAdapter adapter;
    private List<PoiItem> allPoi;
    private PoiSearch mPoiSearch;
    private LatLonPoint latLng;
    private String address;
    private int distance;
    private PoiSearch.Query query;

    public static void luncher(Activity context, int distance, int requestCode) {
        Intent intent = new Intent(context, ListMapActivity.class);
        intent.putExtra(SendRedPacketActivity.EXTRA_TITLE, requestCode);
        intent.putExtra(SendRedPacketActivity.EXTRA_DISTANCE, distance);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_map);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        assignView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        dismissProgressDialog();
    }

    PoiSearch.OnPoiSearchListener onPoiSearchListener = new PoiSearch.OnPoiSearchListener() {
        @Override
        public void onPoiSearched(PoiResult poiResult, int i) {
            allPoi = poiResult.getPois();
            adapter.setNewData(allPoi);
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }
    };

    private void assignView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showKeyBoardDelay(ListMapActivity.this,etMapSearch);
            }
        });
        distance = getIntent().getIntExtra(SendRedPacketActivity.EXTRA_DISTANCE, 1000);

        etMapSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(etMapSearch.getText().toString())) {
                        search(etMapSearch.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
        etMapSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    search(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recycleMapSearch.setLayoutManager(new LinearLayoutManager(ListMapActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new PopupRecyclerAdapter(allPoi);
        recycleMapSearch.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showProgressDialog("地图加载中...");
                PoiItem selectPlace = (PoiItem) adapter.getData().get(position);
                latLng = selectPlace.getLatLonPoint();
                address = selectPlace.getTitle();
                etMapSearch.setText("");
                BaiduMapActivity.luncher(ListMapActivity.this, distance, 7, latLng, address);
            }
        });
    }

    private void search(String keyWord) {
        query = new PoiSearch.Query(keyWord, "", MyApp.location.getCity()==null?"": MyApp.location.getCity());
        query.setPageNum(20);
        query.setPageNum(1);
        mPoiSearch = new PoiSearch(ListMapActivity.this, query);
        mPoiSearch.setOnPoiSearchListener(onPoiSearchListener);
        mPoiSearch.searchPOIAsyn();
    }

    @OnClick(R.id.liner_map_back)
    public void onClick() {
        onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Intent intent) {
        switch (intent.getAction()) {
            case "finish_map":
                finish();
                break;
        }

    }
}
