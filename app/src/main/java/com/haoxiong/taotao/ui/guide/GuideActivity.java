package com.haoxiong.taotao.ui.guide;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.main.MainActivity;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.igexin.sdk.PushManager;


public class GuideActivity extends AppCompatActivity {
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final String token = SharePreferenceUtil.get(GuideActivity.this, "token");
        final String phone = SharePreferenceUtil.get(GuideActivity.this, "phone");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(token)) {
                    PushManager.getInstance().bindAlias(GuideActivity.this,phone);
                    MyApp.token =token;
                    MyApp.login_state = 1;
                    MainActivity.luncher(GuideActivity.this);
                    finish();
                } else {
                    MainActivity.luncher(GuideActivity.this);
                    finish();
                }
            }
        }, 2000);

    }
}
