package com.haoxiong.taotao.ui.friendslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.FriendListServiceApi;
import com.fan.service.response.FriendListResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.friendslist.adapter.FriendListAdapter;
import com.haoxiong.taotao.ui.friendslist.bean.FriendListBean;
import com.haoxiong.taotao.ui.share.ShareActivity;
import com.haoxiong.taotao.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendsListActivity extends BaseActivity {

    @BindView(R.id.liner_friend_back)
    LinearLayout linerFriendBack;
    @BindView(R.id.recycle_friend_list)
    RecyclerView recycleFriendList;
    private FriendListAdapter adapter;
    List<FriendListResponse.DataBean>  data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        ButterKnife.bind(this);
        assign();
        refreshData();
    }
    private void refreshData() {
        showProgressDialog("正在加载中...");
        FriendListServiceApi.firendList(FriendsListActivity.this, MyApp.token, new OnRequestCompletedListener<FriendListResponse>() {
            @Override
            public void onCompleted(FriendListResponse response, String msg) {
                dismissProgressDialog();
                if (response.getErr() == 0) {
                    data = response.getData();
                    adapter.setNewData(response.getData());
                } else {
                    ToastUtils.toTosat(FriendsListActivity.this,response.getMsg());
                }
            }
        });
    }
    private void assign() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleFriendList.setLayoutManager(manager);
        adapter = new FriendListAdapter(FriendsListActivity.this,data);
        recycleFriendList.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                return true;
            }
        });
    }

    @OnClick(R.id.liner_friend_back)
    public void onClick() {
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShareActivity.luncher(FriendsListActivity.this);
        finish();
    }
    public static void luncher(Context context) {
        context.startActivity(new Intent(context, FriendsListActivity.class));
    }
}
