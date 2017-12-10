package com.haoxiong.taotao.ui.redpacket;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.fan.service.Client;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.PayServiceApi;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.request.SendRedPacketRequest;
import com.fan.service.response.AlipayResponse;
import com.fan.service.response.CollectRedPacketResponse;
import com.fan.service.response.GetRedPacketResponse;
import com.fan.service.response.PayResponse;
import com.fan.service.response.RedManagerResponse;
import com.fan.service.response.RedOwerResponse;
import com.fan.service.response.RedPacketDetailResponse;
import com.fan.service.response.RedPacketListResponse;
import com.fan.service.response.SaveResponse;
import com.fan.service.response.SendRedPacketResponse;
import com.fan.service.response.WxPayResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.pay.PayResult;
import com.haoxiong.taotao.ui.login.LoginActivity;
import com.haoxiong.taotao.ui.message.MessageDetailActivity;
import com.haoxiong.taotao.ui.redmaneger.RedMangerActivity;
import com.haoxiong.taotao.ui.redpacket.adapter.RecycleRedPacketWinerAdapter;
import com.haoxiong.taotao.ui.sendredpacket.GetRedPacketSuccessActivity;
import com.haoxiong.taotao.util.Constants;
import com.haoxiong.taotao.util.DensityUtil;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.MD5;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.haoxiong.taotao.util.Util;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

public class RedPacketActivity extends BaseActivity {
    @BindView(R.id.liner_tel)
    LinearLayout linerTel;
    @BindView(R.id.liner_address)
    LinearLayout linerAddress;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.liner_all)
    LinearLayout linerAll;
    @BindView(R.id.liner_red_packet_share)
    LinearLayout linerRedPacketShare;
    @BindView(R.id.red_all)
    TextView redAll;
    @BindView(R.id.consulting)
    LinearLayout consulting;
    @BindView(R.id.red_title_icon)
    ImageView redTitleIcon;
    //    public static final String PARTNER = "2088621558884290";
//    // 商户收款账号
//    public static final String SELLER = "haoxiong2017@163.com";
    private StringBuffer sb;
    private Map<String, String> resultunifiedorder;
    private PayReq req;
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    private AlertDialog.Builder builder;
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANJiz8dPeKgcgV0I" +
            "1oZCo/D//5Kuw1GQHznZfKBAqi21M6wYKR/HbiVSji97fVdfmMHbQiDHsTRszafb" +
            "uZG3TUmLx8RssaxWUIwdp7Gv7UhzxuAQIH25qR8FmXuWD9rqGSumTSSSRe1rDkNv" +
            "FpThqAUNe5/YwzUz0gkN2KaNFB+RAgMBAAECgYAzZfaTH+Q/Ve941i7q0B8TQsei" +
            "g1WueIJYdLzQvduVnR5JngF2BRfwwFGCYi4GCZHdUn6+WWr+ftJjfvfAFMMpki7J" +
            "6DPKRPWVLsZ0x7ieW0rZI4eqGjd+l5v+vHDNaKj7DRgUMqbKMZ5tbAWzDdUm6dc3" +
            "2Fo7yREfngnYgwaWAQJBAO66dQqnrvGQBEgPsW9PXUQBR5rpkxzG4Tdy5mUXdJLs" +
            "AYsmof3KFhzj4So+Uj6K4BcJJLjsj0QtpDfCgbweYDECQQDhm2tAf7mvIqMKuyLN" +
            "RAsR8vcnm2WytxcUB36fs5n6RijwCr+dRyTMKj1DXzC4kVtHB5l2XqYC4N6O2Rrf" +
            "hj1hAkArh5g73yOJLId7VFE4SLJq6gjSeHC4uJLsd2kHWeWJvLrzUpILIsQxdGPk" +
            "PgTlHpGJ4cLubUQaXHArMq5RTQChAkB2/cg1vqgrDTO9RzJ13TuRffqJs1aSSisr" +
            "70AD73B5JmQVJzYlOA7aeDTTwfMqhdAEyKdSXV1mC0CXSz73QhBBAkEAlJpSAbzZ" +
            "0dsSm3Al3In+wRQxahGKtnx23Fk4+2ZWTGZb/y07aGVPuiyhD62xc8qA3E15heKA" +
            "/Hr2hy9RGYR8Bg==";     // 支付宝公钥
    // 商户私钥，pkcs8格式
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSYs/HT3ioHIFdCNaGQqPw//+S" +
            "rsNRkB852XygQKottTOsGCkfx24lUo4ve31XX5jB20Igx7E0bM2n27mRt01Ji8fE" +
            "bLGsVlCMHaexr+1Ic8bgECB9uakfBZl7lg/a6hkrpk0kkkXtaw5DbxaU4agFDXuf" +
            "2MM1M9IJDdimjRQfkQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    @BindView(R.id.liner_red_packet_back)
    LinearLayout linerRedPacketBack;
    @BindView(R.id.img_red_packet_love)
    ImageView imgRedPacketLove;
    @BindView(R.id.liner_red_packet_love)
    LinearLayout linerRedPacketLove;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.img_red_packet_pic)
    ImageView imgRedPacketPic;
    @BindView(R.id.tv_red_packet_money)
    TextView tvRedPacketMoney;
    @BindView(R.id.tv_red_packet_num)
    TextView tvRedPacketNum;
    @BindView(R.id.tv_rea_packet_content)
    TextView tvReaPacketContent;
    @BindView(R.id.tv_rea_packet_tel)
    TextView tvReaPacketTel;
    @BindView(R.id.tv_rea_packet_add)
    TextView tvReaPacketAdd;
    @BindView(R.id.tv_rea_packet_question)
    TextView tvReaPacketQuestion;
    @BindView(R.id.tv_rea_packet_answer1)
    TextView tvReaPacketAnswer1;
    @BindView(R.id.tv_rea_packet_answer2)
    TextView tvReaPacketAnswer2;
    @BindView(R.id.tv_rea_packet_answer3)
    TextView tvReaPacketAnswer3;
    @BindView(R.id.recycle_red_packet_winer)
    RecyclerView recycleRedPacketWiner;
    @BindView(R.id.activity_red_packet)
    RelativeLayout activityRedPacket;
    @BindView(R.id.img_red_packet_bottom)
    ImageView imgRedPacketBottom;
    @BindView(R.id.tv_red_packet_bottom)
    TextView tvRedPacketBottom;
    @BindView(R.id.liner_red_packet_bottom)
    LinearLayout linerRedPacketBottom;
    @BindView(R.id.srl_red_packet)
    NestedScrollView srlRedPacket;
    private SendRedPacketRequest sendRedPacketRequest;
    private RedPacketListResponse.DataBean dataBean;
    private CollectRedPacketResponse.DataBean dataBean1;
    private RedManagerResponse.DataBean.RedsOnBean redsOnBean;
    private RedPacketDetailResponse.DataBean detailResponse;
    private List<RedOwerResponse.DataBean> data = new ArrayList<>();
    private RecycleRedPacketWinerAdapter adapter;
    private boolean love;
    private int answer = -1;
    private PopupWindow popupwindowShow;
    private int page = 1;
    private int rid;
    private String redTitle;
    private String redPic;
    private boolean isGetRedPacket = false;
    private View footerView;

    public static void luncher(Context context, @NonNull SendRedPacketRequest redPacketRequest) {
        Intent intent = new Intent(context, RedPacketActivity.class);
        intent.putExtra("content", redPacketRequest);
        context.startActivity(intent);
    }

    public static void luncher(Context context, @NonNull RedManagerResponse.DataBean.RedsOnBean dataBean) {
        Intent intent = new Intent(context, RedPacketActivity.class);
        intent.putExtra("content", dataBean);
        context.startActivity(intent);
    }

    public static void luncher(Context context, @NonNull RedPacketListResponse.DataBean dataBean) {
        Intent intent = new Intent(context, RedPacketActivity.class);
        intent.putExtra("content", dataBean);
        context.startActivity(intent);
    }

    public static void luncher(Context context, @NonNull CollectRedPacketResponse.DataBean dataBean) {
        Intent intent = new Intent(context, RedPacketActivity.class);
        intent.putExtra("content", dataBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        assignView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        srlRedPacket.scrollTo(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void assignView() {

        srlRedPacket.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // 将透明度声明成局部变量用于判断
                int alpha = 0;
                int count = 0;
                float scale = 0;
                Log.e("...", scrollY + "");
                int y = imgRedPacketPic.getHeight() - DensityUtil.dip2px(RedPacketActivity.this, 44);
                if (scrollY <= y) {
redTitleIcon.setVisibility(View.GONE);
                    imgRedPacketLove.setVisibility(View.GONE);
                    scale = (float) scrollY / y;
                    alpha = (int) (255 * scale);
                    // 随着滑动距离改变透明度
                    // Log.e("al=","="+alpha);
                    relativeLayout.setBackgroundColor(Color.argb(alpha, 213, 62, 53));
                } else {
                    if (alpha < 255) {
                        redTitleIcon.setVisibility(View.VISIBLE);
                        imgRedPacketLove.setVisibility(View.VISIBLE);
                        Log.e("执行次数", "=" + (++count));
                        // 防止频繁重复设置相同的值影响性能
                        alpha = 255;
                        relativeLayout.setBackgroundColor(Color.argb(alpha, 213, 62, 53));
                    }
                }
            }

        });
        switch (MyApp.TYPE) {
            case 4:
                linerRedPacketBottom.setVisibility(View.GONE);
                break;
            case 1:
                consulting.setVisibility(View.GONE);

                break;
        }
        recycleRedPacketWiner.setNestedScrollingEnabled(false);
        recycleRedPacketWiner.setLayoutManager(new FullyLinearLayoutManager(RedPacketActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecycleRedPacketWinerAdapter(R.layout.item_red_packet_winer_adapter, RedPacketActivity.this, data);
        recycleRedPacketWiner.setAdapter(adapter);
        footerView = getLayoutInflater().inflate(R.layout.footer, null);
        adapter.addFooterView(footerView);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllOwnerActivity.launch(RedPacketActivity.this, rid);
            }
        });
        if (data.size() == 0) {
            footerView.setVisibility(View.GONE);
        } else {
            footerView.setVisibility(View.VISIBLE);
        }
        switch (MyApp.TYPE) {
            case 1:
                sendRedPacketRequest = getIntent().getParcelableExtra("content");
                linerRedPacketLove.setVisibility(View.GONE);
                tvRedPacketMoney.setText(sendRedPacketRequest.getMoney() + "元/" + sendRedPacketRequest.getPcount() + "个");
                tvRedPacketNum.setText(sendRedPacketRequest.getPcount() + "个");
                tvReaPacketContent.setText(sendRedPacketRequest.getMerchant_des());
                if (!TextUtils.isEmpty(sendRedPacketRequest.getTel())) {
                    tvReaPacketTel.setText(sendRedPacketRequest.getTel());
                } else {
                    linerTel.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(sendRedPacketRequest.getAddress())) {
                    tvReaPacketAdd.setText(sendRedPacketRequest.getAddress());
                } else {
                    linerAddress.setVisibility(View.GONE);
                }
                if (TextUtils.isEmpty(sendRedPacketRequest.getTel()) && TextUtils.isEmpty(sendRedPacketRequest.getAddress())) {
                    linerAll.setVisibility(View.GONE);
                }
                tvReaPacketAnswer1.setEnabled(false);
                tvReaPacketAnswer2.setEnabled(false);
                tvReaPacketAnswer3.setEnabled(false);
                tvReaPacketQuestion.setText(sendRedPacketRequest.getQuestion());
                tvReaPacketAnswer1.setText(sendRedPacketRequest.getFirst_answer());
                tvReaPacketAnswer2.setText(sendRedPacketRequest.getSecond_answer());
                tvReaPacketAnswer3.setText(sendRedPacketRequest.getThird_answer());
                tvReaPacketAnswer1.setTextColor(Color.parseColor("#ffffff"));
                tvReaPacketAnswer2.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer3.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer1.setBackgroundResource(R.drawable.answer_ringht);
                tvReaPacketAnswer2.setBackgroundResource(R.drawable.answer_wrong);
                tvReaPacketAnswer3.setBackgroundResource(R.drawable.answer_wrong);
                if (sendRedPacketRequest.getPic1_filecode().split("&")[0].contains("http")) {
                    GlideUtil.loadImg(RedPacketActivity.this, sendRedPacketRequest.getPic1_filecode().split("&")[0], imgRedPacketPic);
                } else {
                    GlideUtil.loadImg(RedPacketActivity.this,Client.BASE_URL+"public/"+ sendRedPacketRequest.getPic1_filecode().split("&")[0], imgRedPacketPic);

                }
                imgRedPacketBottom.setVisibility(View.GONE);
                tvRedPacketBottom.setText("塞钱进红包");
                rid = sendRedPacketRequest.getRid();
                redTitle = sendRedPacketRequest.getMerchant();
                adapter.removeAllFooterView();
                break;
            case 2:
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                dataBean = getIntent().getParcelableExtra("content");
                getDetailData(dataBean.getRid());
                rid = dataBean.getRid();
                redTitle = dataBean.getMerchant();

                break;
            case 3:
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                dataBean1 = getIntent().getParcelableExtra("content");
                getDetailData(dataBean1.getRId());
                rid = dataBean1.getRId();
                redTitle = dataBean1.getMerchant();

                break;
            case 4:
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) srlRedPacket.getLayoutParams();
                layoutParams.setMarginEnd(0);
                srlRedPacket.setLayoutParams(layoutParams);
                redsOnBean = getIntent().getParcelableExtra("content");
                getDetailData(redsOnBean.getRid());
                rid = redsOnBean.getRid();
                redTitle = redsOnBean.getMerchant();
                break;

            case 5:
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                dataBean = getIntent().getParcelableExtra("content");
                linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                getDetailData(dataBean.getRid());
                rid = dataBean.getRid();
                redTitle = dataBean.getMerchant();
                break;
            case 6:
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                dataBean = getIntent().getParcelableExtra("content");
                linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                getDetailData(dataBean.getRid());
                rid = dataBean.getRid();
                redTitle = dataBean.getMerchant();
                break;
        }
        srlRedPacket.scrollTo(0, 0);
    }

    private void getDetailData(int rid) {
        getOwener(page, rid);
        showProgressDialog("数据加载中...");
        switch (MyApp.login_state) {
            case 0:
                RedPacketListApi.redPacketDetailUnLogin(RedPacketActivity.this, rid, new OnRequestCompletedListener<RedPacketDetailResponse>() {
                    @Override
                    public void onCompleted(RedPacketDetailResponse response, String msg) {
                        dismissProgressDialog();
                        if (response != null) {
                            detailResponse = response.getData();
                            redPic = detailResponse.getUserPic().split("&")[0];
                            refreshView();
                        } else {
                            ToastUtils.toTosat(RedPacketActivity.this, msg);
                        }
                    }
                });
                break;
            case 1:
                RedPacketListApi.redPacketDetailLogin(RedPacketActivity.this, MyApp.token, rid, new OnRequestCompletedListener<RedPacketDetailResponse>() {
                    @Override
                    public void onCompleted(RedPacketDetailResponse response, String msg) {
                        dismissProgressDialog();
                        if (response != null && response.getErr() == 0) {
                            detailResponse = response.getData();
                            redPic = detailResponse.getUserPic().split("&")[0];
                            if (MyApp.TYPE == 2 || MyApp.TYPE == 3) {
                                if (detailResponse.getIsgot() == 1) {
                                    MyApp.TYPE = 5;
                                } else {
                                    MyApp.TYPE = 2;
                                }
                                if (detailResponse.getRemainCount() == 0) {
                                    MyApp.TYPE = 6;
                                }
                            }
                            refreshView();
                        } else if (response.getErr() == -666) {
                            MyApp.login_state = 0;
                            ToastUtils.toTosat(RedPacketActivity.this, msg);
                        } else {
                            ToastUtils.toTosat(RedPacketActivity.this, msg);
                        }
                    }
                });

                break;
        }

    }

    private void getOwener(final int page1, final int rid) {
        RedPacketListApi.redpostterList(RedPacketActivity.this, page1, rid, new OnRequestCompletedListener<RedOwerResponse>() {
            @Override
            public void onCompleted(RedOwerResponse response, String msg) {

                if (response == null) {
                    ToastUtils.toTosat(RedPacketActivity.this, "网络跑丢了");
                }
                if (response.getErr() == 0) {
                    if (response.getData() != null && response.getData().size() > 0) {
                        adapter.setNewData(response.getData());
                        footerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtils.toTosat(RedPacketActivity.this, response.getMsg());
                }
                srlRedPacket.scrollTo(0, 0);
            }
        });

    }

    private void refreshView() {
        srlRedPacket.scrollTo(0, 0);
        linerRedPacketLove.setVisibility(View.VISIBLE);
        tvRedPacketMoney.setText(detailResponse.getMoney() + "元/" + detailResponse.getPCount() + "个");
        tvRedPacketNum.setText(detailResponse.getRemainCount() + "个");
        tvReaPacketContent.setText(detailResponse.getMerchant_des());
        if (!TextUtils.isEmpty(detailResponse.getTel())) {
            tvReaPacketTel.setText(detailResponse.getTel());
        } else {
            linerTel.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(detailResponse.getAddress())) {
            tvReaPacketAdd.setText(detailResponse.getAddress());
        } else {
            linerAddress.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(detailResponse.getTel()) && TextUtils.isEmpty(detailResponse.getAddress())) {
            linerAll.setVisibility(View.GONE);
        }
        tvReaPacketQuestion.setText(detailResponse.getQuestion());
        tvReaPacketAnswer1.setText(detailResponse.getAnswer0());
        tvReaPacketAnswer2.setText(detailResponse.getAnswer1());
        tvReaPacketAnswer3.setText(detailResponse.getAnswer2());
        String[] split = detailResponse.getUserPic().split("&");
        if (split[0].contains("http")) {
            GlideUtil.loadImg(RedPacketActivity.this, split[0], imgRedPacketPic);
        } else {
            GlideUtil.loadImg(RedPacketActivity.this, Client.BASE_URL + "public/" + split[0], imgRedPacketPic);
        }

        if (detailResponse.isIscollect()) {
            imgRedPacketLove.setImageResource(R.drawable.ic_love_select);
        }
        love = detailResponse.isIscollect();
    }

    @Override
    public void onBackPressed() {
        if (!isGetRedPacket) {
            EventBus.getDefault().post(new Intent("REFRESH_POSITION"));
        }
        super.onBackPressed();
    }

    @OnClick({R.id.liner_red_packet_back, R.id.liner_red_packet_love
            , R.id.tv_rea_packet_answer1, R.id.tv_rea_packet_answer2
            , R.id.tv_rea_packet_answer3, R.id.liner_red_packet_share
            , R.id.red_all, R.id.consulting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_red_packet_back:
                onBackPressed();
                break;
            case R.id.liner_red_packet_love:
                if (MyApp.login_state == 0) {
                    LoginActivity.luncher(RedPacketActivity.this);
                } else {
                    love();
                }
                break;
            case R.id.tv_rea_packet_answer1:
                tvReaPacketAnswer1.setTextColor(Color.parseColor("#ffffff"));
                tvReaPacketAnswer2.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer3.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer1.setBackgroundResource(R.drawable.answer_ringht);
                tvReaPacketAnswer2.setBackgroundResource(R.drawable.answer_wrong);
                tvReaPacketAnswer3.setBackgroundResource(R.drawable.answer_wrong);
                answer = 0;
                break;
            case R.id.tv_rea_packet_answer2:
                tvReaPacketAnswer2.setTextColor(Color.parseColor("#ffffff"));
                tvReaPacketAnswer1.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer3.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer2.setBackgroundResource(R.drawable.answer_ringht);
                tvReaPacketAnswer1.setBackgroundResource(R.drawable.answer_wrong);
                tvReaPacketAnswer3.setBackgroundResource(R.drawable.answer_wrong);
                answer = 1;
                break;
            case R.id.tv_rea_packet_answer3:
                tvReaPacketAnswer3.setTextColor(Color.parseColor("#ffffff"));
                tvReaPacketAnswer2.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer1.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer3.setBackgroundResource(R.drawable.answer_ringht);
                tvReaPacketAnswer2.setBackgroundResource(R.drawable.answer_wrong);
                tvReaPacketAnswer1.setBackgroundResource(R.drawable.answer_wrong);
                answer = 2;
                break;
            case R.id.liner_red_packet_share:
                share(linerRedPacketShare);
                break;
            case R.id.red_all:
                AllOwnerActivity.launch(RedPacketActivity.this, rid);
                break;
            case R.id.consulting:
                if (MyApp.TYPE != 1) {
                    if (MyApp.login_state == 1) {
                        MessageDetailActivity.launch(RedPacketActivity.this, redTitle, String.valueOf(rid), tvReaPacketContent.getText().toString(), redPic);
                    } else {
                        LoginActivity.luncher(RedPacketActivity.this);
                    }
                }
                break;
        }
    }

    public void share(View view) {
        int rid = 0;
        switch (MyApp.TYPE) {
            case 1:
                rid = sendRedPacketRequest.getRid();
                break;
            case 2:
                rid = dataBean.getRid();
                break;
            case 3:

                break;
            case 4:
                rid = redsOnBean.getRid();
                break;

            case 5:
                rid = dataBean.getRid();
                break;
            case 6:
                rid = dataBean.getRid();
                break;
        }
        try {
            UMImage thumb = new UMImage(RedPacketActivity.this, R.drawable.logo_s);
            UMWeb web = new UMWeb("http://hb.huidang2105.com/share/login.html?yqm=" + MyApp.getInstance().user.getData().getUserinfo().getInviteCode() + "&rid=" + rid);
            web.setTitle("和我一起来 掏掏 抢红包吧");//标题
            web.setThumb(thumb);  //缩略图
            web.setDescription("掏掏-红包不断，掏掏不绝");//描述
            new ShareAction(RedPacketActivity.this).withMedia(web)
                    .setDisplayList(SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(umShareListener).open();
        } catch (Exception e) {
            ToastUtils.toTosat(RedPacketActivity.this, "邀请码获取失败...");
        }

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);
            ToastUtils.toTosat(RedPacketActivity.this, "分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.toTosat(RedPacketActivity.this, t.getMessage());
            if (t != null) {
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.toTosat(RedPacketActivity.this, "分享取消了");
        }
    };

    private void love() {
        /**
         * 1 发红包 2 抢红包 3 收藏 4 红包管理 5一抢过
         */
        switch (MyApp.TYPE) {
            case 1:

                break;
            case 2:
                loveOrNot();
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                loveOrNot();
                break;
            case 6:
                loveOrNot();
                break;
        }
    }

    private void loveOrNot() {
        RedPacketListApi.saveOrNot(RedPacketActivity.this, MyApp.token, dataBean.getRid(), !love, new OnRequestCompletedListener<SaveResponse>() {
            @Override
            public void onCompleted(SaveResponse response, String msg) {
                if (response.getErr() == 0) {
                    if (!love) {
                        imgRedPacketLove.setImageResource(R.drawable.ic_love_select);
                    } else {
                        imgRedPacketLove.setImageResource(R.drawable.ic_love_unselect);
                    }
                    love = !love;
                } else {
                    ToastUtils.toTosat(RedPacketActivity.this, response.getMsg());
                }
            }
        });
    }

    @OnClick(R.id.liner_red_packet_bottom)
    public void onClick() {
        switch (MyApp.TYPE) {
            case 1:
                showPopwin();
                break;
            case 2:
                isGetRedPacket = true;
                EventBus.getDefault().post(new Intent("REFRESH"));
                if (MyApp.login_state == 0) {
                    LoginActivity.luncher(RedPacketActivity.this);
                } else {
                    if (MyApp.location == null) {
                        if (builder == null) {
                            builder = new AlertDialog.Builder(RedPacketActivity.this);
                        }
                        builder.setTitle("提示")
                                .setIcon(R.drawable.ic_logo)
                                .setMessage("需要开启定位才能抢红包？")
                                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getAppDetailSettingIntent(RedPacketActivity.this);

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();

                    } else {

                        if (answer != -1) {
                            RedPacketListApi.getRedPacket(RedPacketActivity.this, MyApp.token, dataBean.getRid(), answer, new OnRequestCompletedListener<GetRedPacketResponse>() {
                                @Override
                                public void onCompleted(GetRedPacketResponse response, String msg) {
                                    if (response != null) {
                                        if (response.getErr() == 0) {
                                            GetRedPacketSuccessActivity.luncher(RedPacketActivity.this, detailResponse, response);
                                        } else {
                                            MyApp.TYPE = 5;
                                            imgRedPacketBottom.setVisibility(View.VISIBLE);
                                            tvRedPacketBottom.setText("抢红包");
                                            linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                                            ToastUtils.toTosat(RedPacketActivity.this, response.getMsg());
                                        }
                                    } else {
                                        ToastUtils.toTosat(RedPacketActivity.this, msg);
                                        MyApp.TYPE = 5;
                                        imgRedPacketBottom.setVisibility(View.VISIBLE);
                                        tvRedPacketBottom.setText("抢红包");
                                        linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                                    }
                                }
                            });
                        } else {
                            ToastUtils.toTosat(RedPacketActivity.this, "请选择答案");
                        }
                    }

                }

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                isGetRedPacket = true;
                EventBus.getDefault().post(new Intent("REFRESH"));
                ToastUtils.toTosat(RedPacketActivity.this, "您已抢过此红包");
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                break;
            case 6:
                isGetRedPacket = true;
                EventBus.getDefault().post(new Intent("REFRESH"));
                ToastUtils.toTosat(RedPacketActivity.this, "此红包已抢完");
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                break;
        }
    }

    private void showPopwin() {
        popupwindowShow = new PopupWindow(linerRedPacketBottom, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pay_popupwindow_layout, null);
        popupwindowShow.setContentView(view);
        popupwindowShow.setFocusable(true);
        popupwindowShow.setOutsideTouchable(true);
        popupwindowShow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupwindowShow.showAtLocation(linerRedPacketBottom, Gravity.BOTTOM, 0, 0);
        TextView wxpay = (TextView) view.findViewById(R.id.wxpay);
        TextView walletpay = (TextView) view.findViewById(R.id.walletpay);
        TextView alipay = (TextView) view.findViewById(R.id.alipay);
        TextView cansol = (TextView) view.findViewById(R.id.cansol);
        wxpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowShow.dismiss();
                sb = new StringBuffer();
                req = new PayReq();
                String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                PrePayIdAsyncTask prePayIdAsyncTask = new PrePayIdAsyncTask();
                prePayIdAsyncTask.execute(urlString);
            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowShow.dismiss();
                PayServiceApi.alipay(RedPacketActivity.this, MyApp.token, sendRedPacketRequest.getMoney(), sendRedPacketRequest.getRid(), new OnRequestCompletedListener<WxPayResponse>() {
                    @Override
                    public void onCompleted(WxPayResponse response, String msg) {
                        if (response == null) {
                            ToastUtils.toTosat(RedPacketActivity.this, "支付宝支付失败");
                            return;
                        }
                        zhifubaopay(response.getName());
                    }
                });
            }
        });
        walletpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RedPacketActivity.this);
                builder.setTitle("提示")
                        .setIcon(R.drawable.ic_logo)
                        .setMessage("确定支付" + sendRedPacketRequest.getMoney() + "元？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                popupwindowShow.dismiss();
                                PayServiceApi.pay(RedPacketActivity.this, MyApp.token, sendRedPacketRequest.getMoney(), sendRedPacketRequest.getRid(), new OnRequestCompletedListener<PayResponse>() {
                                    @Override
                                    public void onCompleted(PayResponse response, String msg) {
                                        if (response == null) {
                                            ToastUtils.toTosat(RedPacketActivity.this, "钱包余额不足");
                                            return;
                                        }
                                        if (response.getErr() == 0) {
                                            ToastUtils.toTosat(RedPacketActivity.this, "发布成功");
                                            clearSP();
                                            RedMangerActivity.luncher(RedPacketActivity.this);
                                        } else {
                                            ToastUtils.toTosat(RedPacketActivity.this, "钱包余额不足");
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();

            }
        });
        cansol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowShow.dismiss();

            }
        });
    }

    private void zhifubaopay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RedPacketActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /*
     * 调起微信支付
     */
    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
        Log.i(">>>>>", req.partnerId);
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        if (resultunifiedorder != null) {
            req.prepayId = resultunifiedorder.get("prepay_id");
            req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
        } else {
            Toast.makeText(RedPacketActivity.this, "prepayid为空", Toast.LENGTH_SHORT).show();
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");


    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        return appSign;
    }


    private class PrePayIdAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = ProgressDialog.show(RedPacketActivity.this, "提示", "正在提交订单");

        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = String.format(params[0]);
            String entity = getProductArgs();
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result;
            genPayReq();//生成签名参数
            sendPayReq();
        }
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
        }
        return null;

    }

    private String getProductArgs() {
        // TODO Auto-generated method stub
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = getNonceStr();
            xml.append("<xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("body", "掏掏商品"));
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://huidangchina.com/ "));//写你们的回调地址
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("total_fee", sendRedPacketRequest.getMoney() * 100 + ""));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            String sign = getPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlString = toXml(packageParams);
            return xmlString;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    //生成订单号,测试用，在客户端生成
    private String genOutTradNo() {
        Random random = new Random();
//		return "dasgfsdg1234"; //订单号写死的话只能支付一次，第二次不能生成订单
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    //生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 生成签名
     */
    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    /*
     * 转换成xml
     */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        return sb.toString();
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Intent intent) {
        switch (intent.getAction()) {
            case "success":
                RedPacketListApi.get_pay_param(RedPacketActivity.this, MyApp.token, sendRedPacketRequest.getMoney(), sendRedPacketRequest.getRid(), new OnRequestCompletedListener<Object>() {
                    @Override
                    public void onCompleted(Object response, String msg) {
                        if (response != null) {
                            RedPacketListApi.alipayrp(RedPacketActivity.this, MyApp.token, new OnRequestCompletedListener<Object>() {
                                @Override
                                public void onCompleted(Object response, String msg) {
                                    if (!TextUtils.isEmpty(sendRedPacketRequest.getLat())) {
                                        sendLocal();
                                    } else {
                                        sendPacket();
                                    }
                                }
                            });
                        } else {
                            ToastUtils.toTosat(RedPacketActivity.this, "提交服务器失败");
                        }
                    }
                });
                break;
            case "fail":
                break;
            case "refresh":
                MyApp.TYPE = 5;
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                break;
        }
    }


    private void sendPacket() {
        RedPacketListApi.sendRedPacket(RedPacketActivity.this, MyApp.token,
                sendRedPacketRequest.getMoney(), sendRedPacketRequest.getPcount(),
                1, sendRedPacketRequest.getMerchant(), sendRedPacketRequest.getMerchant_des(),
                sendRedPacketRequest.getQuestion(), sendRedPacketRequest.getFirst_answer(), sendRedPacketRequest.getSecond_answer(), sendRedPacketRequest.getThird_answer()
                , sendRedPacketRequest.getAddress(), sendRedPacketRequest.getTel(), sendRedPacketRequest.getFilecode()
                , new OnRequestCompletedListener<SendRedPacketResponse>() {
                    @Override
                    public void onCompleted(SendRedPacketResponse response, String msg) {
                        if (response == null) {
                            return;
                        }
                        if (response.getErr() == 0) {
                            clearSP();
                            RedMangerActivity.luncher(RedPacketActivity.this);
                        } else {
                            ToastUtils.toTosat(RedPacketActivity.this, response.getMsg());
                        }
                    }
                });
    }

    private void sendLocal() {
        RedPacketListApi.sendLocalRedPacket(RedPacketActivity.this, MyApp.token,
                sendRedPacketRequest.getMoney(), sendRedPacketRequest.getPcount(),
                2, sendRedPacketRequest.getDistance(), sendRedPacketRequest.getMerchant(), sendRedPacketRequest.getMerchant_des(),
                sendRedPacketRequest.getQuestion(), sendRedPacketRequest.getFirst_answer(), sendRedPacketRequest.getSecond_answer(), sendRedPacketRequest.getThird_answer()
                , sendRedPacketRequest.getAddress(), sendRedPacketRequest.getTel(), sendRedPacketRequest.getLng(), sendRedPacketRequest.getLat(), sendRedPacketRequest.getFilecode()
                , new OnRequestCompletedListener<SendRedPacketResponse>() {
                    @Override
                    public void onCompleted(SendRedPacketResponse response, String msg) {
                        if (response == null) {
                            return;
                        }
                        if (response.getErr() == 0) {
                            clearSP();
                            RedMangerActivity.luncher(RedPacketActivity.this);
                        } else {
                            ToastUtils.toTosat(RedPacketActivity.this, response.getMsg());
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(RedPacketActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        RedPacketListApi.get_alipay_param(RedPacketActivity.this, MyApp.token, new OnRequestCompletedListener<AlipayResponse>() {
                            @Override
                            public void onCompleted(AlipayResponse response, String msg) {
                                if (response != null && response.getErr() == 0) {
                                    if (!TextUtils.isEmpty(sendRedPacketRequest.getLat())) {
                                        sendLocal();
                                    } else {
                                        sendPacket();
                                    }
                                } else {
                                    ToastUtils.toTosat(RedPacketActivity.this, "提交服务器失败");
                                }
                            }
                        });
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(RedPacketActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(RedPacketActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    private void clearSP() {
        SharePreferenceUtil.remove(RedPacketActivity.this, "lng");
        SharePreferenceUtil.remove(RedPacketActivity.this, "lat");
        SharePreferenceUtil.remove(RedPacketActivity.this, "mapaddress");
        SharePreferenceUtil.remove(RedPacketActivity.this, "merchant");
        SharePreferenceUtil.remove(RedPacketActivity.this, "merchant_des");
        SharePreferenceUtil.remove(RedPacketActivity.this, "tel");
        SharePreferenceUtil.remove(RedPacketActivity.this, "address");
        SharePreferenceUtil.remove(RedPacketActivity.this, "question");
        SharePreferenceUtil.remove(RedPacketActivity.this, "first_answer");
        SharePreferenceUtil.remove(RedPacketActivity.this, "second_answer");
        SharePreferenceUtil.remove(RedPacketActivity.this, "third_answer");
        SharePreferenceUtil.remove(RedPacketActivity.this, "type");
        SharePreferenceUtil.remove(RedPacketActivity.this, "distance");
        SharePreferenceUtil.remove(RedPacketActivity.this, "pic");
        SharePreferenceUtil.remove(RedPacketActivity.this, "money");
        SharePreferenceUtil.remove(RedPacketActivity.this, "pcount");
        SharePreferenceUtil.remove(RedPacketActivity.this, "filecode");
    }

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }
}
