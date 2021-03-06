package com.haoxiong.taotao.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fan.service.Client;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.MessageApi;
import com.fan.service.response.MessageResponse;
import com.fan.service.response.MessageSendResponse;
import com.fan.service.response.ReadMessageResponse;
import com.fan.service.response.RefreshTimeResponse;
import com.fan.service.response.UnReadMessageResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.message.adapter.MessageDetailAdapter;
import com.haoxiong.taotao.ui.message.bean.Message;
import com.haoxiong.taotao.util.DateUtils;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.KeyboardUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.haoxiong.taotao.util.Util;
import com.haoxiong.taotao.util.WindowUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.liner_message_detail_back)
    LinearLayout linerMessageDetailBack;
    @BindView(R.id.tv_message_detail_title)
    TextView tvMessageDetailTitle;
    @BindView(R.id.liner_message_detail_detail)
    LinearLayout linerMessageDetailDetail;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.img_message_detail_picture)
    ImageView imgMessageDetailPicture;
    @BindView(R.id.tv_message_detail_content)
    TextView tvMessageDetailContent;
    @BindView(R.id.tv_message_detail_close)
    TextView tvMessageDetailClose;
    @BindView(R.id.recycle_view_message_detail)
    RecyclerView recycleViewMessageDetail;
    @BindView(R.id.et_message_detail_message)
    EditText etMessageDetailMessage;
    @BindView(R.id.liner_message_detail_red_packet)
    LinearLayout linerMessageDetailRedPacket;
    @BindView(R.id.swipe_refresh_layout_message_detail)
    SwipeRefreshLayout swipeRefreshLayoutMessageDetail;
    @BindView(R.id.tv_message_detail_send)
    TextView tvMessageDetailSend;
    private MessageDetailAdapter adapter;
    ArrayList<Message> data = new ArrayList<>();
    ArrayList<Message> dataRecord = new ArrayList<>();
    private int page = 0;
    public static int Type = 0;
    private String ltbt;
    private String rid;
    private String message;
    private String pic;
    private String ltid;
    private Timer timer;

    public static void launch(Context context, MessageResponse.DataBean.ListBean.ListLbBean data) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
        Type = 0;
    }

    public static void launch(Context context, String title, String rid, String message, String pic, String ltid) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("rid", rid);
        intent.putExtra("message", message);
        intent.putExtra("pic", pic);
        intent.putExtra("ltid", ltid);
        context.startActivity(intent);
        Type = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        assignView();
        refreshDate(true);
    }

    private void assignView() {
        swipeRefreshLayoutMessageDetail.setRefreshing(false);
        switch (Type) {
            case 0:
                MessageResponse.DataBean.ListBean.ListLbBean bean = getIntent().getParcelableExtra("data");
                ltbt = bean.getLtbt();
                rid = bean.getRid();
                ltid = bean.getLtid();
                break;
            case 1:
                ltid = getIntent().getStringExtra("ltid");
                ltbt = getIntent().getStringExtra("title");
                rid = getIntent().getStringExtra("rid");
                pic = getIntent().getStringExtra("pic");
                message = getIntent().getStringExtra("message");
                linerMessageDetailRedPacket.setVisibility(View.VISIBLE);
                if (pic.contains("http")) {
                    GlideUtil.loadImg(MessageDetailActivity.this, pic, imgMessageDetailPicture);
                } else {
                    GlideUtil.loadImg(MessageDetailActivity.this, Client.BASE_URL_IMG + pic, imgMessageDetailPicture);
                }

                tvMessageDetailContent.setText(message != null ? message : "");
                break;
        }

        tvMessageDetailTitle.setText(ltbt);
        recycleViewMessageDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MessageDetailAdapter(new ArrayList<Message>());
        recycleViewMessageDetail.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KeyboardUtil.hideKeybord(MessageDetailActivity.this);
            }
        });
        swipeRefreshLayoutMessageDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecordMessages(false);
            }
        });

        MessageApi.refreshTime(MessageDetailActivity.this, new OnRequestCompletedListener<RefreshTimeResponse>() {
            @Override
            public void onCompleted(RefreshTimeResponse response, String msg) {
                try {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            refreshDate(false);
                        }
                    }, 10000, response.getData().getSteTime() * 1000);

                } catch (Exception e) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            refreshDate(false);
                        }
                    }, 10000, 10000);
                }
            }
        });
    }

    private void loadRecordMessages(final boolean b) {
        if (!b) {
            swipeRefreshLayoutMessageDetail.setRefreshing(true);
        }
        MessageApi.readMessageList(MessageDetailActivity.this, MyApp.token, rid, ltid, page, new OnRequestCompletedListener<ReadMessageResponse>() {
            @Override
            public void onCompleted(ReadMessageResponse response, String msg) {
                swipeRefreshLayoutMessageDetail.setRefreshing(false);
                if (response != null && response.getRet() == 200) {
                    if (response.getData() != null && response.getData().getCode() == 200 & response.getData().getList() != null) {
                        if (response.getData().getList().getLt_list() != null && response.getData().getList().getLt_list().size() > 0) {
                            List<ReadMessageResponse.DataBean.ListBean.LtListBean> lt_list = response.getData().getList().getLt_list();
                            List<Message> datas = new ArrayList<>();
                            for (int i = 0; i < lt_list.size(); i++) {
                                boolean tag = true;
                                for (int j = 0; j < dataRecord.size(); j++) {
                                    if (dataRecord.get(j).getId() == lt_list.get(i).getId()) {
                                        tag = false;
                                        break;
                                    }
                                }
                                if (!tag) {
                                    break;
                                }
                                Message message = new Message();
                                message.setContent(lt_list.get(i).getJlxx());
                                message.setItemType(lt_list.get(i).getXxlx());
                                message.setTime(lt_list.get(i).getSj());
                                message.setId(lt_list.get(i).getId());
                                datas.add(message);
                                if (b) {
                                    data.add(message);
                                }
                            }
                            adapter.addData(0, datas);
                            recycleViewMessageDetail.scrollToPosition(datas.size() - 1);
                            if (lt_list.size() < 10) {
                                swipeRefreshLayoutMessageDetail.setEnabled(false);
                            } else {

                            }
                            page++;
                        }

                    } else if (response.getData() != null && response.getData().getCode() == 206) {
                        swipeRefreshLayoutMessageDetail.setEnabled(false);
                    }

                }

            }
        });
    }

    private void refreshDate(final boolean b) {
        if (b) {
            showProgressDialog("数据加载中...");
        }
        MessageApi.unReadMessageList(MessageDetailActivity.this, MyApp.token, rid, ltid, new OnRequestCompletedListener<UnReadMessageResponse>() {
            @Override
            public void onCompleted(UnReadMessageResponse response, String msg) {
                if (b) {
                    dismissProgressDialog();
                }
                if (response != null) {
                    if (response.getRet() == 200) {
                        if (response.getData() != null && response.getData().getList() != null) {
                            if (b && Type == 0) {
                                linerMessageDetailRedPacket.setVisibility(View.VISIBLE);
                                GlideUtil.loadImg(MessageDetailActivity.this, Client.BASE_URL_IMG + response.getData().getList().getMer_pics(), imgMessageDetailPicture);
                                tvMessageDetailContent.setText(response.getData().getList().getMerchant_des() != null ? response.getData().getList().getMerchant_des() : "");
                            }
                        }
                        if (response.getData() != null && response.getData().getCode() == 200 && response.getData().getList() != null) {
                            page = 0;

                            if (response.getData().getList().getLt_list() != null && response.getData().getList().getLt_list().size() > 0) {
                                data.clear();
                                List<UnReadMessageResponse.DataBean.ListBean.LtListBean> lt_list = response.getData().getList().getLt_list();
                                List<Message> datas = new ArrayList<>();
                                for (int i = 0; i < lt_list.size(); i++) {
                                    if (adapter.getData() != null && adapter.getData().size() != 0) {
                                        if (lt_list.get(i).getXxlx() != 1) {
                                            Message message = new Message();
                                            message.setContent(lt_list.get(i).getJlxx());
                                            message.setItemType(lt_list.get(i).getXxlx());
                                            message.setTime(lt_list.get(i).getSj());
                                            message.setId(lt_list.get(i).getId());
                                            datas.add(message);
                                            data.add(message);
                                            dataRecord.add(message);
                                        }
                                    } else {
                                        Message message = new Message();
                                        message.setContent(lt_list.get(i).getJlxx());
                                        message.setItemType(lt_list.get(i).getXxlx());
                                        message.setTime(lt_list.get(i).getSj());
                                        message.setId(lt_list.get(i).getId());
                                        datas.add(message);
                                        data.add(message);
                                        dataRecord.add(message);
                                    }
                                }
                                swipeRefreshLayoutMessageDetail.setEnabled(true);
                                if (datas.size() != 0) {
                                    if (adapter.getData() != null && adapter.getData().size() != 0) {
                                        adapter.addData(datas);
                                        recycleViewMessageDetail.scrollToPosition(adapter.getData().size() - 1);
                                    } else {
                                        Log.e("setNewData", datas.size() + "");
                                        adapter.setNewData(datas);
                                        recycleViewMessageDetail.scrollToPosition(datas.size() - 1);
                                        if (datas.size() < 6) {
                                            page = 0;
                                            if (b) {
                                                loadRecordMessages(true);
                                            }
                                        }

                                    }
                                }

                            }
                        } else if (response.getData() != null && response.getData().getCode() == 206) {

                            page = 0;
                            if (b) {
                                loadRecordMessages(true);
                            }

                        } else {
                            if (b && Type == 0) {
                                linerMessageDetailRedPacket.setVisibility(View.GONE);
                                linerMessageDetailDetail.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        if (b && Type == 0) {
                            linerMessageDetailRedPacket.setVisibility(View.GONE);
                            linerMessageDetailDetail.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    if (b && Type == 0) {
                        linerMessageDetailRedPacket.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @OnClick({R.id.liner_message_detail_back, R.id.liner_message_detail_detail, R.id.tv_message_detail_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_message_detail_back:
                onBackPressed();
                break;
            case R.id.liner_message_detail_detail:
                linerMessageDetailRedPacket.setVisibility(View.VISIBLE);
                linerMessageDetailDetail.setVisibility(View.GONE);
                break;
            case R.id.tv_message_detail_close:
                linerMessageDetailRedPacket.setVisibility(View.GONE);
                linerMessageDetailDetail.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.tv_message_detail_send)
    public void onClick() {
        if (!TextUtils.isEmpty(etMessageDetailMessage.getText().toString())) {
            MessageApi.sendMessage(MessageDetailActivity.this, MyApp.token, rid, ltid, etMessageDetailMessage.getText().toString().trim(), new OnRequestCompletedListener<MessageSendResponse>() {
                @Override
                public void onCompleted(MessageSendResponse response, String msg) {
                }
            });
            Message message = new Message();
            message.setTime("");
            message.setItemType(1);
            message.setContent(etMessageDetailMessage.getText().toString());
            adapter.addData(message);
            recycleViewMessageDetail.scrollToPosition(adapter.getData().size() - 1);
            etMessageDetailMessage.setText("");
        } else {
            ToastUtils.toTosat(MessageDetailActivity.this, "请输入内容...");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (timer != null) {
                timer.cancel();
            }
        } catch (Exception e) {

        }

    }
}
