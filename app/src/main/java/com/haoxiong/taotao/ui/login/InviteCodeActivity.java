package com.haoxiong.taotao.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.LoginServiceApi;
import com.fan.service.response.LoginResponse;
import com.fan.service.response.RegisterResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.main.MainActivity;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.igexin.sdk.PushManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.haoxiong.taotao.ui.login.LoginActivity.EXTRA_FINISH;

public class InviteCodeActivity extends BaseActivity {

    @BindView(R.id.et_invite_code)
    EditText etInviteCode;
    @BindView(R.id.tv_invite_login)
    TextView tvInviteLogin;
    private String inviteCode;
    private String phone;

    public static void luncher(Context context, @NonNull String inviteCode, @NonNull String phone) {
        Intent intent = new Intent(context, InviteCodeActivity.class);
        intent.putExtra("inviteCode", inviteCode);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        ButterKnife.bind(this);
        assignView();
    }

    private void assignView() {
        inviteCode = getIntent().getStringExtra("inviteCode");
        phone = getIntent().getStringExtra("phone");

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.tv_invite_login)
    public void onClick() {
        invite_login();
    }

    private void invite_login() {
        showProgressDialog("正在登陆中...");
        SharePreferenceUtil.put(InviteCodeActivity.this, "isShow", true);
        /*LoginServiceApi.setInviteCode(InviteCodeActivity.this, MyApp.token, etInviteCode.getText().toString(), new OnRequestCompletedListener<LoginResponse>() {
            @Override
            public void onCompleted(LoginResponse response, String msg) {
                login();
            }
        });*/
        register();
    }

    private void login() {
        LoginServiceApi.login(InviteCodeActivity.this, phone, "oemxffklu6", "Android", new OnRequestCompletedListener<LoginResponse>() {
            @Override
            public void onCompleted(LoginResponse response, String msg) {
                dismissProgressDialog();
                if (response == null) {
                    ToastUtils.toTosat(InviteCodeActivity.this, msg);
                    return;
                }
                if (response.getErr() != 0) {
                    ToastUtils.toTosat(InviteCodeActivity.this, response.getMsg());
                } else {

                }
            }
        });

    }
    private void register() {
        String etInviteCodeText = etInviteCode.getText().toString();
        if (TextUtils.isEmpty(etInviteCodeText)) {
            etInviteCodeText = null;
        }
        LoginServiceApi.register(InviteCodeActivity.this,phone, etInviteCodeText, new OnRequestCompletedListener<RegisterResponse>() {
            @Override
            public void onCompleted(RegisterResponse response, String msg) {
                if (response == null) {
                    ToastUtils.toTosat(InviteCodeActivity.this, msg);
                    return;
                }
                if (response.getErr() != 333) {
                    ToastUtils.toTosat(InviteCodeActivity.this, response.getMsg());
                } else {
                    PushManager.getInstance().bindAlias(InviteCodeActivity.this, phone);
                    MyApp.token = response.getData().getToken();
                    MyApp.login_state = 1;
                    SharePreferenceUtil.put(InviteCodeActivity.this, "phone", phone);
                    SharePreferenceUtil.put(InviteCodeActivity.this,"token",response.getData().getToken());
                    MainActivity.luncher(InviteCodeActivity.this);
                }
            }
        });

    }
}
