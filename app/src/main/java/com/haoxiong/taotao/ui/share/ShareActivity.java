package com.haoxiong.taotao.ui.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.friendslist.FriendsListActivity;
import com.haoxiong.taotao.ui.main.MainActivity;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.pkmmte.view.CircularImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends BaseActivity {

    @BindView(R.id.liner_share_back)
    LinearLayout linerShareBack;
    @BindView(R.id.liner_share_friend)
    LinearLayout linerShareFriend;
    @BindView(R.id.img_share_head)
    CircularImageView imgShareHead;
    @BindView(R.id.tv_share)
    ImageView tvShare;
    @BindView(R.id.tv_share_rule)
    TextView tvShareRule;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        GlideUtil.loadImg(ShareActivity.this, R.mipmap.head, MyApp.getInstance().user.getData().getUserinfo().getUserPic(), imgShareHead);
        tvCode.setText("我的邀请码：" + MyApp.getInstance().user.getData().getUserinfo().getInviteCode());
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            ToastUtils.toTosat(ShareActivity.this, "分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.toTosat(ShareActivity.this, t.getMessage());
            if (t != null) {
                Log.e("throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.toTosat(ShareActivity.this, "分享取消了");
        }
    };

    public void share(View view) {
        try {
            UMImage thumb = new UMImage(ShareActivity.this, R.drawable.logo_s);
            UMWeb web = new UMWeb("http://hb.huidang2105.com/share/share.html?yqm=" + MyApp.getInstance().user.getData().getUserinfo().getInviteCode());
            web.setTitle("和我一起来 掏掏 抢红包吧");//标题
            web.setThumb(thumb);  //缩略图
            web.setDescription("掏掏-红包不断，掏掏不绝");//描述
            new ShareAction(ShareActivity.this).withMedia(web)
                    .setDisplayList(SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(umShareListener).open();
        } catch (Exception e) {
            ToastUtils.toTosat(ShareActivity.this,"获取邀请码失败...");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.luncher(ShareActivity.this);
    }

    public static void luncher(Context context) {
        context.startActivity(new Intent(context, ShareActivity.class));
    }

    @OnClick({R.id.liner_share_back, R.id.liner_share_friend, R.id.tv_share, R.id.tv_share_rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_share_back:
                onBackPressed();
                break;
            case R.id.liner_share_friend:
                FriendsListActivity.luncher(ShareActivity.this);
                break;
            case R.id.tv_share:
                share(tvShare);
                break;
            case R.id.tv_share_rule:
                break;
        }
    }
}
