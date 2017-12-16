package com.haoxiong.taotao.ui.redmaneger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedMangerActivity extends BaseActivity {

    @BindView(R.id.liner_manager_back)
    LinearLayout linerManagerBack;
    @BindView(R.id.tv_manager_run)
    TextView tvManagerRun;
    @BindView(R.id.tv_manager_run_underline)
    TextView tvManagerRunUnderline;
    @BindView(R.id.liner_manager_run)
    LinearLayout linerManagerRun;
    @BindView(R.id.tv_manager_end)
    TextView tvManagerEnd;
    @BindView(R.id.tv_manager_end_underline)
    TextView tvManagerEndUnderline;
    @BindView(R.id.liner_manager_end)
    LinearLayout linerManagerEnd;
    private RunFragment runFragment;
    private EndFragment endFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_mainger);
        ButterKnife.bind(this);
        assign();

    }

    private void assign() {
        runFragment = new RunFragment();
        endFragment = new EndFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_recycle, runFragment, "").commit();
    }

    @OnClick({R.id.liner_manager_back, R.id.liner_manager_run, R.id.liner_manager_end})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_manager_back:
                onBackPressed();
                break;
            case R.id.liner_manager_run:
                tvManagerRun.setTextColor(Color.parseColor("#ff7b42"));
                tvManagerEnd.setTextColor(Color.parseColor("#333333"));
                tvManagerRunUnderline.setBackgroundColor(Color.parseColor("#ff7b42"));
                tvManagerEndUnderline.setBackgroundColor(Color.parseColor("#F8F8FA"));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recycle, runFragment, "").commit();
                break;
            case R.id.liner_manager_end:
                tvManagerEnd.setTextColor(Color.parseColor("#ff7b42"));
                tvManagerRun.setTextColor(Color.parseColor("#333333"));
                tvManagerEndUnderline.setBackgroundColor(Color.parseColor("#ff7b42"));
                tvManagerRunUnderline.setBackgroundColor(Color.parseColor("#F8F8FA"));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recycle, endFragment, "").commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.luncher(RedMangerActivity.this);
    }

    public static void luncher(Context context) {
        context.startActivity(new Intent(context, RedMangerActivity.class));
    }
}
