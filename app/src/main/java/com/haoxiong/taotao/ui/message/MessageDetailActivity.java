package com.haoxiong.taotao.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.message.adapter.MessageAdapter;
import com.haoxiong.taotao.ui.message.adapter.MessageDetailAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetailActivity extends AppCompatActivity {

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
    private MessageDetailAdapter adapter;
    ArrayList<User> data = new ArrayList<>();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MessageDetailActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        recycleViewMessageDetail.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new MessageDetailAdapter(data);
        recycleViewMessageDetail.setAdapter(adapter);
        for (int i = 0; i < 10; i++) {
            User user = new User();
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                    user.setItemType(User.TEXT);
                    break;
                default:
                    user.setItemType(User.IMG);
                    break;
            }
            data.add(user);
        }
        adapter.setNewData(data);
        swipeRefreshLayoutMessageDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutMessageDetail.setRefreshing(true);
                ArrayList<User> data1 = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    User user = new User();
                    switch (i) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 9:
                            user.setItemType(User.TEXT);
                            break;
                        default:
                            user.setItemType(User.IMG);
                            break;
                    }
                    data1.add(user);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adapter.addData(0, data1);
                recycleViewMessageDetail.scrollToPosition(data1.size()-1);
                swipeRefreshLayoutMessageDetail.setRefreshing(false);
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
                break;
            case R.id.tv_message_detail_close:
                linerMessageDetailRedPacket.setVisibility(View.GONE);
                break;
        }
    }
}
