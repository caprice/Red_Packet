package com.haoxiong.taotao.ui.sendredpacket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fan.service.response.GetRedPacketResponse;
import com.fan.service.response.RedManagerResponse;
import com.fan.service.response.RedPacketDetailResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.main.MainActivity;
import com.haoxiong.taotao.ui.redpacket.RedPacketActivity;
import com.haoxiong.taotao.ui.share.ShareActivity;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.Log;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

public class GetRedPacketSuccessActivity extends AppCompatActivity {

    @BindView(R.id.liner_get_red_packet_back)
    LinearLayout linerGetRedPacketBack;
    @BindView(R.id.img_get_red_packet_bg)
    ImageView imgGetRedPacketBg;
    @BindView(R.id.tv_get_red_packet_name)
    TextView tvGetRedPacketName;
    @BindView(R.id.tv_get_red_packet_money)
    TextView tvGetRedPacketMoney;
    @BindView(R.id.tv_get_red_packet_left)
    TextView tvGetRedPacketLeft;
    @BindView(R.id.tv_get_red_packet_share)
    TextView tvGetRedPacketShare;
    @BindView(R.id.activity_get_red_packet_success)
    RelativeLayout activityGetRedPacketSuccess;
    private GetRedPacketResponse successData;
    private RedPacketDetailResponse.DataBean detailData;

    public static void luncher(Context context, @NonNull RedPacketDetailResponse.DataBean dataBean, GetRedPacketResponse response) {
        Intent intent = new Intent(context, GetRedPacketSuccessActivity.class);
        intent.putExtra("content1", dataBean);
        intent.putExtra("content2", response);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_red_packet_success);
        ButterKnife.bind(this);
        assignView();
    }

    private void assignView() {
        detailData = getIntent().getParcelableExtra("content1");
        successData = getIntent().getParcelableExtra("content2");
        try {
            GlideUtil.loadImg(GetRedPacketSuccessActivity.this, detailData.getUserPic(), imgGetRedPacketBg);
        } catch (Exception e) {
            imgGetRedPacketBg.setImageResource(R.mipmap.item);
        }
        tvGetRedPacketName.setText(detailData.getMerchant());
        tvGetRedPacketMoney.setText(successData.getData().getMoney());
        tvGetRedPacketLeft.setText(successData.getData().getRemainCount() + "");
    }

    @OnClick({R.id.liner_get_red_packet_back, R.id.tv_get_red_packet_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_get_red_packet_back:
                onBackPressed();
                break;
            case R.id.tv_get_red_packet_share:
                share(tvGetRedPacketShare);
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            ToastUtils.toTosat(GetRedPacketSuccessActivity.this, "分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.toTosat(GetRedPacketSuccessActivity.this, t.getMessage());
            if (t != null) {
                Log.e("throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.toTosat(GetRedPacketSuccessActivity.this, "分享取消了");
        }
    };

    public void share(View view) {
        try {
            UMImage thumb = new UMImage(GetRedPacketSuccessActivity.this, R.drawable.logo_s);
            UMWeb web = new UMWeb("http://hb.huidang2105.com/share/login.html?yqm=" + MyApp.getInstance().user.getData().getUserinfo().getInviteCode() + "&rid=" + detailData.getRid());
            web.setTitle("和我一起来 掏掏 抢红包吧");//标题
            web.setThumb(thumb);  //缩略图
            web.setDescription("掏掏-红包不断，掏掏不绝");//描述
            new ShareAction(GetRedPacketSuccessActivity.this).withMedia(web)
                    .setDisplayList(SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(umShareListener).open();

        } catch (Exception e) {
            ToastUtils.toTosat(GetRedPacketSuccessActivity.this, "邀请码获取失败...");
        }

    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new Intent("refresh"));
        finish();
    }
}
