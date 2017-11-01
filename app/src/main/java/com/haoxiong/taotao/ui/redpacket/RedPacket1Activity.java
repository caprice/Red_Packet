package com.haoxiong.taotao.ui.redpacket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.request.SendRedPacketRequest;
import com.fan.service.response.CollectRedPacketResponse;
import com.fan.service.response.RedManagerResponse;
import com.fan.service.response.RedPacketDetailResponse;
import com.fan.service.response.RedPacketListResponse;
import com.fan.service.response.SaveResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.pay.PayResult;
import com.haoxiong.taotao.ui.login.LoginActivity;
import com.haoxiong.taotao.ui.redpacket.adapter.RecycleRedPacketWinerAdapter;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.SignUtils;
import com.haoxiong.taotao.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

public class RedPacket1Activity extends BaseActivity {
    public static final String PARTNER = "2088621558884290";
    // 商户收款账号
    public static final String SELLER = "haoxiong2017@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "";

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
    MyRecycleView recycleRedPacketWiner;
    @BindView(R.id.activity_red_packet)
    RelativeLayout activityRedPacket;
    @BindView(R.id.img_red_packet_bottom)
    ImageView imgRedPacketBottom;
    @BindView(R.id.tv_red_packet_bottom)
    TextView tvRedPacketBottom;
    @BindView(R.id.liner_red_packet_bottom)
    LinearLayout linerRedPacketBottom;
    @BindView(R.id.srl_red_packet)
    ScrollView srlRedPacket;
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
    private SendRedPacketRequest sendRedPacketRequest;
    private RedPacketListResponse.DataBean dataBean;
    private RedManagerResponse.DataBean.RedsOnBean redsOnBean;
    private RedPacketDetailResponse.DataBean detailResponse;
    private CollectRedPacketResponse.DataBean collcetbean;
    private List<RedPacketDetailResponse.DataBean.GetterBean> data = new ArrayList<>();
    private RecycleRedPacketWinerAdapter adapter;
    private boolean love;

    public static void luncher(Context context, @NonNull SendRedPacketRequest redPacketRequest) {
        Intent intent = new Intent(context, RedPacket1Activity.class);
        intent.putExtra("content", redPacketRequest);
        context.startActivity(intent);
    }

    public static void luncher(Context context, @NonNull CollectRedPacketResponse.DataBean redPacketRequest) {
        Intent intent = new Intent(context, RedPacket1Activity.class);
        intent.putExtra("content", redPacketRequest);
        context.startActivity(intent);
    }

    public static void luncher(Context context, @NonNull RedManagerResponse.DataBean.RedsOnBean dataBean) {
        Intent intent = new Intent(context, RedPacket1Activity.class);
        intent.putExtra("content", dataBean);
        context.startActivity(intent);
    }

    public static void luncher(Context context, @NonNull RedPacketListResponse.DataBean dataBean) {
        Intent intent = new Intent(context, RedPacket1Activity.class);
        intent.putExtra("content", dataBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet1);
        ButterKnife.bind(this);
        assignView();
    }

    private void assignView() {
        switch (MyApp.TYPE) {
            case 3:
            case 4:
                linerRedPacketBottom.setVisibility(View.GONE);
                break;

        }
        recycleRedPacketWiner.setLayoutManager(new FullyLinearLayoutManager(RedPacket1Activity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecycleRedPacketWinerAdapter(R.layout.item_red_packet_winer_adapter, RedPacket1Activity.this, data);
        recycleRedPacketWiner.setAdapter(adapter);
        switch (MyApp.TYPE) {
            case 1:
                sendRedPacketRequest = getIntent().getParcelableExtra("content");
                linerRedPacketLove.setVisibility(View.GONE);
                tvRedPacketMoney.setText(sendRedPacketRequest.getMoney() + "元/" + sendRedPacketRequest.getPcount() + "个");
                tvRedPacketNum.setText(sendRedPacketRequest.getPcount() + "个");
                tvReaPacketContent.setText(sendRedPacketRequest.getMerchant_des());
                tvReaPacketTel.setText(sendRedPacketRequest.getTel() != null ? sendRedPacketRequest.getTel() : "");
                tvReaPacketAdd.setText(sendRedPacketRequest.getAddress() != null ? sendRedPacketRequest.getAddress() : "");
                tvReaPacketQuestion.setText(sendRedPacketRequest.getQuestion());
                tvReaPacketAnswer1.setText(sendRedPacketRequest.getFirst_answer());
                tvReaPacketAnswer2.setText(sendRedPacketRequest.getSecond_answer());
                tvReaPacketAnswer3.setText(sendRedPacketRequest.getThird_answer());
                GlideUtil.loadImg(RedPacket1Activity.this, sendRedPacketRequest.getPic1_filecode(), imgRedPacketPic);
                imgRedPacketBottom.setVisibility(View.GONE);
                tvRedPacketBottom.setText("塞钱进红包");
                break;
            case 2:
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                dataBean = getIntent().getParcelableExtra("content");
                getDetailData(dataBean.getRid());
                break;
            case 3:
                imgRedPacketLove.setImageResource(R.drawable.ic_love_select);
                linerRedPacketBottom.setVisibility(View.GONE);
                collcetbean = getIntent().getParcelableExtra("content");
                getDetailData(collcetbean.getRId());
                break;
            case 4:
                linerRedPacketLove.setVisibility(View.GONE);
                linerRedPacketBottom.setVisibility(View.GONE);
                redsOnBean = getIntent().getParcelableExtra("content");
                getDetailData(redsOnBean.getRid());
                break;
            case 5:
                imgRedPacketBottom.setVisibility(View.VISIBLE);
                tvRedPacketBottom.setText("抢红包");
                dataBean = getIntent().getParcelableExtra("content");
                linerRedPacketBottom.setBackgroundColor(Color.parseColor("#7ed43c33"));
                getDetailData(dataBean.getRid());
                break;
        }

    }

    private void getDetailData(int rid) {
        showProgressDialog("数据加载中...");
        switch (MyApp.login_state) {
            case 0:
                RedPacketListApi.redPacketDetailUnLogin(RedPacket1Activity.this, rid, new OnRequestCompletedListener<RedPacketDetailResponse>() {
                    @Override
                    public void onCompleted(RedPacketDetailResponse response, String msg) {
                        dismissProgressDialog();
                        if (response != null) {
                            detailResponse = response.getData();
                            refreshView();
                        } else {
                            ToastUtils.toTosat(RedPacket1Activity.this, response.getMsg());
                        }
                    }
                });

                break;
            case 1:
                RedPacketListApi.redPacketDetailLogin(RedPacket1Activity.this, MyApp.token, rid, new OnRequestCompletedListener<RedPacketDetailResponse>() {
                    @Override
                    public void onCompleted(RedPacketDetailResponse response, String msg) {
                        dismissProgressDialog();
                        if (response != null) {
                            detailResponse = response.getData();
                            refreshView();
                        } else {
                            ToastUtils.toTosat(RedPacket1Activity.this, response.getMsg());
                        }
                    }
                });

                break;
        }
    }

    private void refreshView() {
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
        GlideUtil.loadImg(RedPacket1Activity.this, detailResponse.getUserPic(), imgRedPacketPic);
        if (detailResponse.getGetter() != null) {
            Log.e("...", detailResponse.getGetter().size() + "");
            adapter.setNewData(detailResponse.getGetter());
        }
        if (detailResponse.isIscollect()) {
            imgRedPacketLove.setImageResource(R.drawable.ic_love_select);
        }
        love = detailResponse.isIscollect();
    }

    @OnClick({R.id.liner_red_packet_back, R.id.liner_red_packet_love
            , R.id.tv_rea_packet_answer1, R.id.tv_rea_packet_answer2
            , R.id.tv_rea_packet_answer3,R.id.liner_red_packet_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_red_packet_back:
                onBackPressed();
                break;
            case R.id.liner_red_packet_love:
                if (MyApp.login_state == 0) {
                    LoginActivity.luncher(RedPacket1Activity.this);
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
                break;
            case R.id.tv_rea_packet_answer2:
                tvReaPacketAnswer2.setTextColor(Color.parseColor("#ffffff"));
                tvReaPacketAnswer1.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer3.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer2.setBackgroundResource(R.drawable.answer_ringht);
                tvReaPacketAnswer1.setBackgroundResource(R.drawable.answer_wrong);
                tvReaPacketAnswer3.setBackgroundResource(R.drawable.answer_wrong);
                break;
            case R.id.tv_rea_packet_answer3:
                tvReaPacketAnswer3.setTextColor(Color.parseColor("#ffffff"));
                tvReaPacketAnswer2.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer1.setTextColor(Color.parseColor("#989898"));
                tvReaPacketAnswer3.setBackgroundResource(R.drawable.answer_ringht);
                tvReaPacketAnswer2.setBackgroundResource(R.drawable.answer_wrong);
                tvReaPacketAnswer1.setBackgroundResource(R.drawable.answer_wrong);
                break;
            case R.id.liner_red_packet_share:
                share(linerRedPacketShare);
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
                rid = collcetbean.getRId();
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
        UMImage thumb = new UMImage(RedPacket1Activity.this, R.drawable.logo_s);
        UMWeb web = new UMWeb("http://hb.huidang2105.com/share/login.html?yqm=" + MyApp.getInstance().user.getData().getUserinfo().getInviteCode() + "&rid=" + rid);
        web.setTitle("和我一起来 掏掏 抢红包吧");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("掏掏-红包不断，掏掏不绝");//描述
        new ShareAction(RedPacket1Activity.this).withMedia(web)
                .setDisplayList(SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);
            ToastUtils.toTosat(RedPacket1Activity.this, "分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.toTosat(RedPacket1Activity.this,  t.getMessage());
            if (t != null) {
                com.umeng.socialize.utils.Log.e("throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.toTosat(RedPacket1Activity.this, "分享取消了");
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

                break;
            case 3:
                loveOrNot();
                break;
            case 4:

                break;
            case 5:
                break;
        }
    }

    private void loveOrNot() {
        RedPacketListApi.saveOrNot(RedPacket1Activity.this, MyApp.token, collcetbean.getRId(), !love, new OnRequestCompletedListener<SaveResponse>() {
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
                    ToastUtils.toTosat(RedPacket1Activity.this, response.getMsg());
                }
            }
        });
    }

    @OnClick(R.id.liner_red_packet_bottom)
    public void onClick() {
        switch (MyApp.TYPE) {
            case 1:
             /*   PayParams params = new PayParams.Builder(this)
                        .wechatAppID("your_wechat_appid")// 仅当支付方式选择微信支付时需要此参数
                        .payWay(PayWay.ALiPay)
                        .goodsPrice(6)// 单位为：分
                        .goodsName("皮皮虾")
                        .goodsIntroduction("此商品属性过于强大，难以调教，一般人切勿轻易购买，吼吼！")
                        .httpType(HttpType.Get)// 使用get请求
                        .httpClientType(NetworkClientType.Retrofit)
                        .requestBaseUrl("http://hb.huidang2105.com:8900/")// 此处替换为为你的app服务器host主机地址
                        .build();
                EasyPay.newInstance(params).requestPayInfo(new OnPayInfoRequestListener() {
                    @Override
                    public void onPayInfoRequetStart() {
                        // TODO 在此处做一些loading操作,progressbar.show();
                    }

                    @Override
                    public void onPayInfoRequstSuccess() {
                        // TODO 可以将loading状态去掉了。请求预支付信息成功，开始跳转到客户端支付。
                    }

                    @Override
                    public void onPayInfoRequestFailure() {
                        // / TODO 可以将loading状态去掉了。获取预支付信息失败，会同时得到一个支付失败的回调。
                    }
                }).toPay(new OnPayResultListener() {

                    @Override
                    public void onPaySuccess(PayWay payWay) {
                        // 支付成功
                    }

                    @Override
                    public void onPayCancel(PayWay payWay) {
                        // 支付流程被用户中途取消
                    }

                    @Override
                    public void onPayFailure(PayWay payWay, int errCode) {
                        // 支付失败，errCode码详见来源博客或者github项目主页的README文档
                    }
                });*/
                String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

/**
 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
 */
                String sign = sign(orderInfo);
                try {
                    /**
                     * 仅需对sign 做URL编码
                     */
                    sign = URLEncoder.encode(sign, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

/**
 * 完整的符合支付宝参数规范的订单信息
 */
                final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(RedPacket1Activity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

// 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
            case 2:
                if (MyApp.login_state == 0) {
                    LoginActivity.luncher(RedPacket1Activity.this);
                } else {
                    ToastUtils.toTosat(RedPacket1Activity.this, "红包");
                }

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                ToastUtils.toTosat(RedPacket1Activity.this, "您已抢过此红包");
                break;
        }
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
                        Toast.makeText(RedPacket1Activity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(RedPacket1Activity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(RedPacket1Activity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }


}
