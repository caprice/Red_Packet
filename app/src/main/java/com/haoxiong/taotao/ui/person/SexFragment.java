package com.haoxiong.taotao.ui.person;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxiong.taotao.R;
import com.haoxiong.taotao.callback.Icallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SexFragment extends BottomSheetDialogFragment {


    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    @BindView(R.id.tv_man)
    TextView tvMan;
    @BindView(R.id.tv_woman)
    TextView tvWoman;
    Icallback callback;
    String content;

    public SexFragment(Icallback callback,String sex) {
        this.callback = callback;
        Bundle bundle = new Bundle();
        bundle.putString("SEX", sex);
        setArguments(bundle);
    }
    public SexFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sex, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        content = bundle.getString("SEX");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (TextUtils.isEmpty(content)) {
            content = "男";
        }
        switch (content) {
            case "男":

                tvMan.setTextColor(Color.parseColor("#333333"));
                tvWoman.setTextColor(Color.parseColor("#888888"));

                break;
            case "女":
                tvWoman.setTextColor(Color.parseColor("#333333"));
                tvMan.setTextColor(Color.parseColor("#888888"));

                break;
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ensure,R.id.tv_man, R.id.tv_woman})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ensure:
                if (content == null) {
                    content = "男";
                }
                callback.check(content);
                dismiss();
                break;
            case R.id.tv_man:
                content = "男";
                tvMan.setTextColor(Color.parseColor("#333333"));
                tvWoman.setTextColor(Color.parseColor("#888888"));
                callback.check(content);
                dismiss();
                break;
            case R.id.tv_woman:
                content = "女";
                tvWoman.setTextColor(Color.parseColor("#333333"));
                tvMan.setTextColor(Color.parseColor("#888888"));
                callback.check(content);
                dismiss();
                break;
        }
    }

}
