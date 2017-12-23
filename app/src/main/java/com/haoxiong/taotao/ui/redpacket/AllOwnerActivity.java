package com.haoxiong.taotao.ui.redpacket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.response.RedOwerResponse;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.redpacket.adapter.RecycleRedPacketWinerAdapter;
import com.haoxiong.taotao.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllOwnerActivity extends BaseActivity {

    @BindView(R.id.liner_all_back)
    LinearLayout linerAllBack;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private int rid;
    private RecycleRedPacketWinerAdapter adapter;
    private List<RedOwerResponse.DataBean> data = new ArrayList<>();
    private int page = 0;

    public static void launch(Context context, int rid) {
        Intent intent = new Intent(context, AllOwnerActivity.class);
        intent.putExtra("rid", rid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_owner);
        ButterKnife.bind(this);
        assignView();
    }

    private void assignView() {
        rid = getIntent().getIntExtra("rid", -1);
        recycleView.setLayoutManager(new FullyLinearLayoutManager(AllOwnerActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecycleRedPacketWinerAdapter(R.layout.item_red_packet_winer_adapter, AllOwnerActivity.this, data);
        adapter.setEnableLoadMore(false);
        recycleView.setAdapter(adapter);
        if (rid != -1) {
            getOwener(0, rid);
        }

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getOwener(page, rid);
            }
        }, recycleView);
    }

    private void getOwener(final int page1, final int rid) {
        if (page1 == 0) {
            showProgressDialog("数据加载中");
        }
        RedPacketListApi.redpostterList(AllOwnerActivity.this, page1, rid, new OnRequestCompletedListener<RedOwerResponse>() {
            @Override
            public void onCompleted(RedOwerResponse response, String msg) {
                if (page1 == 0) {
                   dismissProgressDialog();
                }
                adapter.loadMoreComplete();
                if (response == null) {
                    adapter.loadMoreEnd();
                    adapter.setEnableLoadMore(false);
                    ToastUtils.toTosat(AllOwnerActivity.this, "网络跑丢了");
                }
                if (response.getErr() == 0) {
                    if (response.getData() != null) {
                        if (page1 == 0) {
                            adapter.setNewData(response.getData());
                        } else {
                            adapter.addData(response.getData());
                        }
                        if (response.getData().size() < 10) {
                            adapter.loadMoreEnd();
                            adapter.setEnableLoadMore(false);
                        } else if (response.getData().size() == 0) {
                            ToastUtils.toTosat(AllOwnerActivity.this,"暂无红包得主...");
                        } else {
                            adapter.setEnableLoadMore(true);
                        }

                    } else {
                        ToastUtils.toTosat(AllOwnerActivity.this, response.getMsg());
                    }
                } else {
                    ToastUtils.toTosat(AllOwnerActivity.this, response.getMsg());
                }
            }
        });
    }

    @OnClick(R.id.liner_all_back)
    public void onClick() {
        onBackPressed();
    }
}
