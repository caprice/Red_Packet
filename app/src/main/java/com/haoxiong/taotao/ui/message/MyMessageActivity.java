package com.haoxiong.taotao.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.message.adapter.MessageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMessageActivity extends AppCompatActivity {

    @BindView(R.id.liner_message_back)
    LinearLayout linerMessageBack;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.recycle_view_message)
    RecyclerView recycleViewMessage;
    private MessageAdapter adapter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context,MyMessageActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        recycleViewMessage.setLayoutManager(new LinearLayoutManager(MyMessageActivity.this, LinearLayoutManager.VERTICAL, false));
        ArrayList<Object> data = new ArrayList<>();
        adapter = new MessageAdapter(data);
        recycleViewMessage.setAdapter(adapter);
        for (int i = 0; i < 10; i++) {
            data.add(new Object());
        }
        adapter.setNewData(data);

    }

    @OnClick(R.id.liner_message_back)
    public void onClick() {
        onBackPressed();
    }
}
