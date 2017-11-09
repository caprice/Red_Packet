package com.haoxiong.taotao.ui.balancedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.BalanceServiceApi;
import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.rest.service.BalanceService;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.balancedetail.adapter.BalanceDetailAdapter;
import com.haoxiong.taotao.ui.balancedetail.bean.BanlanceBean;
import com.haoxiong.taotao.ui.wallet.WalletActivity;
import com.haoxiong.taotao.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BalanceDetailActivity extends BaseActivity {

    @BindView(R.id.liner_balance_back)
    LinearLayout linerBalanceBack;
    @BindView(R.id.recycle_content)
    RecyclerView recycleContent;
    private BalanceDetailAdapter adapter;
    private List<BalanceDetailResponse.DataBean.RecordsBean.RecordBean> recordBeens = new ArrayList<>();
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_detail);
        ButterKnife.bind(this);
        assign();
        page = 1;
        refreshData(false);
    }

    private void refreshData(final boolean more) {
        BalanceServiceApi.balanceDetail(BalanceDetailActivity.this, MyApp.token, page,10,new OnRequestCompletedListener<BalanceDetailResponse>() {
            @Override
            public void onCompleted(BalanceDetailResponse response, String msg) {
                if (response == null) {
                    adapter.setEnableLoadMore(true);
                    ToastUtils.toTosat(BalanceDetailActivity.this, msg);
                    return;
                }
                if (response.getErr() == 0) {
                    List<BalanceDetailResponse.DataBean.RecordsBean> records = response.getData().getRecords();
                    recordBeens.clear();
                    for (int i = 0; i < records.size(); i++) {
                        recordBeens.addAll(records.get(i).getRecord());
                    }
                    if (more) {
                        adapter.addData(recordBeens);
                    } else {
                        adapter.setNewData(recordBeens);
                    }
                    if (response.getData().getRecords() != null && response.getData().getRecords().size() > 0) {
                        adapter.setEnableLoadMore(true);
                        adapter.loadMoreEnd();
                    } else {
                        adapter.setEnableLoadMore(false);
                        adapter.loadMoreEnd();
                    }
                } else {
                    ToastUtils.toTosat(BalanceDetailActivity.this, response.getMsg());
                }

            }
        });

    }

    private void assign() {
        LinearLayoutManager manager = new LinearLayoutManager(BalanceDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        recycleContent.setLayoutManager(manager);
        adapter = new BalanceDetailAdapter(recordBeens);
        recycleContent.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
      /*  adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                refreshData(true);
            }
        }, recycleContent);*/
    }

    @OnClick(R.id.liner_balance_back)
    public void onClick() {
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WalletActivity.luncher(BalanceDetailActivity.this);
        finish();
    }
    public static void luncher(Context context) {
        context.startActivity(new Intent(context, BalanceDetailActivity.class));
    }
}
