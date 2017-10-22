package com.haoxiong.taotao.ui.collect;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.CollectServiceApi;
import com.fan.service.response.CollectRedPacketResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.collect.adapter.CollectAdapter;
import com.haoxiong.taotao.ui.collect.bean.CollectBean;
import com.haoxiong.taotao.ui.main.MainActivity;
import com.haoxiong.taotao.ui.redpacket.RedPacket1Activity;
import com.haoxiong.taotao.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends BaseActivity {

    @BindView(R.id.liner_collect_back)
    LinearLayout linerCollectBack;
    List<CollectRedPacketResponse.DataBean> data = new ArrayList<>();
    @BindView(R.id.sml_collect)
    RecyclerView smlCollect;
    private CollectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        assign();

    }

    @Override
    protected void onResume() {
        super.onResume();
        smlCollect.scrollToPosition(0);
        refreshData();
    }

    private void refreshData() {
        CollectServiceApi.collectRedPacket(CollectActivity.this, MyApp.token, new OnRequestCompletedListener<CollectRedPacketResponse>() {
            @Override
            public void onCompleted(CollectRedPacketResponse response, String msg) {
                if (response != null) {
                    if (response.getErr() == 0) {
                        data.addAll(response.getData());
                        adapter.setNewData(response.getData());
                    } else {
                        ToastUtils.toTosat(CollectActivity.this,response.getMsg());
                        adapter.setNewData(new ArrayList<CollectRedPacketResponse.DataBean>());
                    }
                } else {
                    adapter.setNewData(new ArrayList<CollectRedPacketResponse.DataBean>());
                }
            }
        });

    }

    private void assign() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        smlCollect.setLayoutManager(manager);
        adapter = new CollectAdapter(R.layout.manager_recycle_end_item, CollectActivity.this, data);
        smlCollect.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyApp.TYPE = 3;
                RedPacket1Activity.luncher(CollectActivity.this, data.get(position));
            }
        });
    }


    @OnClick(R.id.liner_collect_back)
    public void onClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.luncher(CollectActivity.this);
    }

    public static void luncher(Context context) {
        context.startActivity(new Intent(context, CollectActivity.class));
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
