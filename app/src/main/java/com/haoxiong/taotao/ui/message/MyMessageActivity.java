package com.haoxiong.taotao.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.MessageApi;
import com.fan.service.response.MessageResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.message.adapter.MessageAdapter;
import com.haoxiong.taotao.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMessageActivity extends BaseActivity {

    @BindView(R.id.liner_message_back)
    LinearLayout linerMessageBack;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.recycle_view_message)
    RecyclerView recycleViewMessage;
    private MessageAdapter adapter;
    List<MessageResponse.DataBean.ListBean.ListLbBean> data = new ArrayList<>();
    private Timer timer;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MyMessageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        recycleViewMessage.setLayoutManager(new LinearLayoutManager(MyMessageActivity.this, LinearLayoutManager.VERTICAL, false));

        adapter = new MessageAdapter(data);
        recycleViewMessage.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageDetailActivity.launch(MyMessageActivity.this, (MessageResponse.DataBean.ListBean.ListLbBean) adapter.getData().get(position));
            }
        });
        refreshDate(true);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshDate(false);
            }
        },10000,10000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDate(true);
    }

    private void refreshDate(final boolean b) {
        if (b) {
            showProgressDialog("数据加载中...");
        }
        MessageApi.messageList(MyMessageActivity.this, MyApp.token, new OnRequestCompletedListener<MessageResponse>() {
            @Override
            public void onCompleted(MessageResponse response, String msg) {
                if (b) {
                    dismissProgressDialog();
                }
                if (response != null && response.getRet() == 200) {
                    if (response.getData() != null) {
                        if (response.getData().getCode() == 200) {
                            adapter.setNewData(response.getData().getList().getList_lb());
                        } else {
                            ToastUtils.toTosat(MyMessageActivity.this, response.getData().getMsg());
                        }
                    }
                }
            }
        });
    }

    @OnClick(R.id.liner_message_back)
    public void onClick() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
        }
    }
}
