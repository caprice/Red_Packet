package com.haoxiong.taotao.ui.wallet.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.response.Bonus1Response;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/11/16.21:23
 */

public class BonusFragment extends DialogFragment {


    @BindView(R.id.et_bonus_alipay_num)
    EditText etBonusAlipayNum;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    CallBack callBack;
    @BindView(R.id.tc_monet)
    TextView tcMonet;

    public BonusFragment(CallBack callBack, String num) {
        this.callBack = callBack;
        Bundle bundle = new Bundle();
        bundle.putString("mun", num);
        setArguments(bundle);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        callBack.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_bonus, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setCancelable(false);
        String mun = getArguments().getString("mun");
        tcMonet.setText(mun);
    }


    @OnClick({R.id.tv_withdraw, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_withdraw:
                if (TextUtils.isEmpty(etBonusAlipayNum.getText().toString())) {
                    ToastUtils.toTosat(getActivity(), "请输入支付宝账号...");
                } else {
                    withDraw();
                }
                break;
            case R.id.tv_cancel:
                callBack.cancel();
                break;
        }
    }

    private void withDraw() {
        RedPacketListApi.getMoney(getActivity(), "activity.getMoney", MyApp.token, etBonusAlipayNum.getText().toString(), new OnRequestCompletedListener<Bonus1Response>() {
            @Override
            public void onCompleted(Bonus1Response response, String msg) {
                if (response != null && response.getData() != null && response.getData().getCode() == 200) {
                    ToastUtils.toTosat(getActivity(), response.getData().getList().getMsg());
                    dismiss();
                } else {
                    ToastUtils.toTosat(getActivity(), "提现失败...");
                }
            }
        });
    }

    public interface CallBack {
        void cancel();
        void dismiss();
    }
}
