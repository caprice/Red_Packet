package com.haoxiong.taotao.ui.sendredpacket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.util.BroadUtil;
import com.haoxiong.taotao.util.KeyboardUtil;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetMoneyActivity extends BaseActivity {

    @BindView(R.id.liner_set_money_back)
    LinearLayout linerSetMoneyBack;
    @BindView(R.id.tv_set_money_num)
    TextView tvSetMoneyNum;
    @BindView(R.id.et_set_money_num)
    EditText etSetMoneyNum;
    @BindView(R.id.et_set_money_red_num)
    EditText etSetMoneyRedNum;
    @BindView(R.id.tv_set_money_next)
    TextView tvSetMoneyNext;
    @BindView(R.id.activity_set_money)
    LinearLayout activitySetMoney;

    public static void luncher(Context context) {
        context.startActivity(new Intent(context, SetMoneyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_money);
        ButterKnife.bind(this);
        assignView();
    }

   /* Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (isBoolean()) {
                        tvSetMoneyNext.setBackgroundResource(R.drawable.set_money_next_unpress_shape);
                    } else {
                        tvSetMoneyNext.setBackgroundResource(R.drawable.set_money_next_unpress_shape);
                    }
                    tvSetMoneyNext.setEnabled(isBoolean());
                    break;
            }
        }
    };*/

    private void assignView() {
        tvSetMoneyNum.setText(SharePreferenceUtil.get(SetMoneyActivity.this, "money"));
        etSetMoneyNum.setText(SharePreferenceUtil.get(SetMoneyActivity.this, "money"));
        etSetMoneyRedNum.setText(SharePreferenceUtil.get(SetMoneyActivity.this, "pcount"));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showKeyBoardDelay(SetMoneyActivity.this, etSetMoneyNum);
            }
        });
        etSetMoneyNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etSetMoneyNum.setSelection(s.length());
                tvSetMoneyNum.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                handler.sendEmptyMessage(0);
            }
        });

        etSetMoneyRedNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etSetMoneyRedNum.setSelection(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                handler.sendEmptyMessage(0);
            }
        });
    }

    @OnClick({R.id.liner_set_money_back, R.id.tv_set_money_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_set_money_back:
                onBackPressed();
                break;
            case R.id.tv_set_money_next:
                if (isBoolean()) {
                    SharePreferenceUtil.put(SetMoneyActivity.this, "money", etSetMoneyNum.getText().toString());
                    SharePreferenceUtil.put(SetMoneyActivity.this, "pcount", etSetMoneyRedNum.getText().toString());
                    SendRedPacketActivity.luncher(SetMoneyActivity.this, etSetMoneyNum.getText().toString(), etSetMoneyRedNum.getText().toString());
                }
                break;
        }
    }

    private boolean isBoolean() {
        return !TextUtils.isEmpty(etSetMoneyNum.getText().toString()) &&
                !TextUtils.isEmpty(etSetMoneyRedNum.getText().toString()) &&
                changeRule();
    }

    private boolean changeRule() {
        String s = etSetMoneyNum.getText().toString();
        String s1 = etSetMoneyRedNum.getText().toString();
        float v = Float.parseFloat(s);
        float v1 = Float.parseFloat(s1);
        if (v >= 1) {
            if ((v / v1 >= 0.1)) {
                return true;
            } else {
                ToastUtils.toTosat(SetMoneyActivity.this, "人均不得少于0.1元");
            }

        } else {
            ToastUtils.toTosat(SetMoneyActivity.this, "金额不得少于1元");
        }
        return false;
    }

}
