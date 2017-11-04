package com.haoxiong.taotao.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.BalanceServiceApi;
import com.fan.service.api.FriendListServiceApi;
import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.WithDrawResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.balancedetail.BalanceDetailActivity;
import com.haoxiong.taotao.util.KeyboardUtil;
import com.haoxiong.taotao.util.ToastUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity {

    @BindView(R.id.liner_wallet_back)
    LinearLayout linerWalletBack;
    @BindView(R.id.liner_wallet_balance)
    LinearLayout linerWalletBalance;
    @BindView(R.id.tv_wallet_rule)
    TextView tvWalletRule;
    @BindView(R.id.et_wallet_alipay_num)
    EditText etWalletAlipayNum;
    @BindView(R.id.et_wallet_alipay_money)
    EditText etWalletAlipayMoney;
    @BindView(R.id.tv_wallet_withdrawal)
    TextView tvWalletWithdrawal;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.activity_wallet)
    LinearLayout activityWallet;
    @BindView(R.id.rule)
    TextView rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        assignView();
        refreshData();
    }

    private void refreshData() {
        BalanceServiceApi.balanceDetail(WalletActivity.this, MyApp.token,1,10, new OnRequestCompletedListener<BalanceDetailResponse>() {
            @Override
            public void onCompleted(BalanceDetailResponse response, String msg) {
                if (response.getErr() == 0) {
                    textView2.setText(response.getData().getBalance() + "");
                } else {
                    ToastUtils.toTosat(WalletActivity.this,response.getMsg());
                }

            }
        });

    }

    private void assignView() {
        rule.setText("1.提现将在7个工作日内到账。\n" +
                "2.提现时，“掏掏”平台将收取提现款的20%作为手续费。\n" +
                "3.目前只接受支付宝提现，暂不支持其它方式提现。");
        etWalletAlipayMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

    }

    @OnClick({R.id.liner_wallet_back, R.id.liner_wallet_balance, R.id.tv_wallet_rule, R.id.tv_wallet_withdrawal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_wallet_back:
                onBackPressed();
                break;
            case R.id.liner_wallet_balance:
                BalanceDetailActivity.luncher(WalletActivity.this);
                break;
            case R.id.tv_wallet_rule:
                break;
            case R.id.tv_wallet_withdrawal:
                if (TextUtils.isEmpty(etWalletAlipayNum.getText().toString()) || TextUtils.isEmpty(etWalletAlipayMoney.getText().toString())) {
                    ToastUtils.toTosat(WalletActivity.this, "请输入完整");
                } else {
                    if (Double.parseDouble(etWalletAlipayMoney.getText().toString()) > Double.parseDouble(MyApp.getInstance().user.getData().getUserinfo().getBalance())) {
                        ToastUtils.toTosat(WalletActivity.this, "请输入正确的金额");
                    } else {
                        BigDecimal bigDecimal = new BigDecimal(etWalletAlipayMoney.getText().toString());
                        BigDecimal decimal = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
                        FriendListServiceApi.applyWithdraw(WalletActivity.this, MyApp.token, decimal.doubleValue(), etWalletAlipayNum.getText().toString(), new OnRequestCompletedListener<WithDrawResponse>() {
                            @Override
                            public void onCompleted(WithDrawResponse response, String msg) {
                                if (response != null) {
                                    if (response.getErr() == 0) {
                                        MyApp.getInstance().user.getData().getUserinfo().setBalance(response.getData().getMoney_back() + "");
                                        WithdrawalActivity.luncher(WalletActivity.this);
                                    } else {
                                        ToastUtils.toTosat(WalletActivity.this,response.getMsg());
                                    }

                                } else {
                                    ToastUtils.toTosat(WalletActivity.this, "服务器错误");
                                }

                            }
                        });
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void luncher(Context context) {
        context.startActivity(new Intent(context, WalletActivity.class));
    }
}
