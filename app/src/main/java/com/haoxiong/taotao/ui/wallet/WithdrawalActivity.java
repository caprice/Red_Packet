package com.haoxiong.taotao.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalActivity extends AppCompatActivity {

    @BindView(R.id.liner_withDrawal_back)
    LinearLayout linerWithDrawalBack;
    @BindView(R.id.activity_withdrawal)
    LinearLayout activityWithdrawal;
    public static void luncher(Context context) {
        context.startActivity(new Intent(context, WithdrawalActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.liner_withDrawal_back)
    public void onClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        MainActivity.luncher(WithdrawalActivity.this);
    }
}
