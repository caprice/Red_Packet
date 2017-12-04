package com.haoxiong.taotao.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.LoginServiceApi;
import com.fan.service.response.LoginResponse;
import com.fan.service.response.RegisterResponse;
import com.fan.service.response.SendMessageResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.main.MainActivity;
import com.haoxiong.taotao.util.KeyboardUtil;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.igexin.sdk.PushManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    public static final String EXTRA_FINISH = "EXTRA_FINISH";
    @BindView(R.id.liner_login_close)
    LinearLayout linerLoginClose;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.tv_login_send_code)
    TextView tvLoginSendCode;
    @BindView(R.id.et_login_code)
    EditText etLoginCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private CountDownTimer countDownTimer;
    private boolean exit = false;
    private int errCode;

    public static void luncher(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void luncher(Context context, boolean exit) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("exit", exit);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        exit = getIntent().getBooleanExtra("exit", false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showKeyBoardDelay(LoginActivity.this, etLoginPhone);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.liner_login_close, R.id.tv_login_send_code, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_login_close:
                if (exit) {
                    MainActivity.luncher(LoginActivity.this);
                } else {
                    onBackPressed();
                }
                break;
            case R.id.tv_login_send_code:
                if (TextUtils.isEmpty(etLoginPhone.getText().toString())) {
                    ToastUtils.toTosat(LoginActivity.this, "请输入手机号码");
                } else {
                    if (etLoginPhone.getText().toString().length() == 11) {
                        startCountDown();
                        sendMessageCode();
                    } else {
                        ToastUtils.toTosat(LoginActivity.this, "请输入正确的手机号码");
                    }
                }
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(etLoginPhone.getText().toString())) {
                    ToastUtils.toTosat(LoginActivity.this, "请输入手机号码");
                } else {
                    if (etLoginPhone.getText().toString().length() == 11) {
                        if (TextUtils.isEmpty(etLoginCode.getText().toString())) {
                            ToastUtils.toTosat(LoginActivity.this, "请输入验证码");
                        } else {
                            login();
                        }
                    } else {
                        ToastUtils.toTosat(LoginActivity.this, "请输入正确的手机号码");
                    }
                }
                break;
        }
    }

    private void finshCountDown() {
        if (countDownTimer == null) {
            return;
        }
        countDownTimer.onFinish();
    }

    private void startCountDown() {
        tvLoginSendCode.setEnabled(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvLoginSendCode.setBackgroundResource(R.drawable.shape_login_code_enable_bg);
                tvLoginSendCode.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvLoginSendCode.setEnabled(true);
                tvLoginSendCode.setBackgroundResource(R.drawable.shape_login_code_bg);
                tvLoginSendCode.setText("发送验证码");
            }
        };
        countDownTimer.start();
    }

    private void login() {
        EventBus.getDefault().post(new Intent("REFRESH"));
        LoginServiceApi.login(LoginActivity.this, etLoginPhone.getText().toString(), etLoginCode.getText().toString(), "Android", new OnRequestCompletedListener<LoginResponse>() {
            @Override
            public void onCompleted(LoginResponse response, String msg) {
                if (response == null) {
                    ToastUtils.toTosat(LoginActivity.this, msg);
                    return;
                }
                if (response.getErr() == 0) {
                    PushManager.getInstance().bindAlias(LoginActivity.this,response.getData().getUserinfo().getMobile());
                    MyApp.token = response.getData().getToken();
                    SharePreferenceUtil.put(LoginActivity.this,"token",response.getData().getToken());
                    MyApp.login_state = 1;
                    MyApp.getInstance().user = response;
                    Intent event = new Intent("refreshUser");
                    EventBus.getDefault().post(event);
                    if (exit) {
                        MainActivity.luncher(LoginActivity.this);
                    } else {
                        MainActivity.luncher(LoginActivity.this);
                    }
                } else if (response.getErr() == -1) {
                    SharePreferenceUtil.put(LoginActivity.this,"phone",etLoginPhone.getText().toString());
                    InviteCodeActivity.luncher(LoginActivity.this,etLoginCode.getText().toString(),etLoginPhone.getText().toString());
                } else {
                    ToastUtils.toTosat(LoginActivity.this, msg);
                }
            }
        });

    }

    private void sendMessageCode() {
        LoginServiceApi.sendCode(LoginActivity.this, etLoginPhone.getText().toString(), new OnRequestCompletedListener<SendMessageResponse>() {
            @Override
            public void onCompleted(SendMessageResponse response, String msg) {
                if (response.getErr() == 0 || response.getErr() == -1) {
                    errCode = response.getErr();
                } else {
                    ToastUtils.toTosat(LoginActivity.this, response.getMsg());
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Intent intent) {
        switch (intent.getAction()) {
            case EXTRA_FINISH:
                finish();
                break;
        }
    }
}
