package com.haoxiong.taotao.ui.sendredpacket;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxiong.taotao.R;
import com.haoxiong.taotao.util.KeyboardUtil;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChildSendRedPacketActivity extends AppCompatActivity {
    @BindView(R.id.liner_send_red_packet_child_back)
    LinearLayout linerSendRedPacketChildBack;
    @BindView(R.id.tv_send_red_packet_child_title)
    TextView tvSendRedPacketChildTitle;
    @BindView(R.id.liner_send_red_packet_child_save)
    LinearLayout linerSendRedPacketChildSave;
    @BindView(R.id.et_send_red_packet_child_one)
    EditText etSendRedPacketChildOne;
    @BindView(R.id.tv_send_red_packet_child_one)
    TextView tvSendRedPacketChildOne;
    @BindView(R.id.liner_send_red_packet_child_one)
    LinearLayout linerSendRedPacketChildOne;
    @BindView(R.id.et_send_red_packet_child_two)
    EditText etSendRedPacketChildTwo;
    @BindView(R.id.tv_send_red_packet_child_two)
    TextView tvSendRedPacketChildTwo;
    @BindView(R.id.liner_send_red_packet_child_two)
    LinearLayout linerSendRedPacketChildTwo;
    @BindView(R.id.et_send_red_packet_child_three)
    EditText etSendRedPacketChildThree;
    @BindView(R.id.tv_send_red_packet_child_three)
    TextView tvSendRedPacketChildThree;
    @BindView(R.id.liner_send_red_packet_child_three)
    LinearLayout linerSendRedPacketChildThree;
    @BindView(R.id.activity_child_send_red_packet)
    LinearLayout activityChildSendRedPacket;
    @BindView(R.id.tv_send_red_packet_child_rule)
    TextView tvSendRedPacketChildRule;
    @BindView(R.id.cb_send_red_packet_child_rule)
    CheckBox cbSendRedPacketChildRule;
    private int text_num;
    private int text_num1;
    private int code;

    public static void luncher(Activity context, int title, int requestCode) {
        Intent intent = new Intent(context, ChildSendRedPacketActivity.class);
        intent.putExtra(SendRedPacketActivity.EXTRA_TITLE, title);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_send_red_packet);
        ButterKnife.bind(this);
        assignView();
    }

    private void assignView() {

        code = getIntent().getIntExtra(SendRedPacketActivity.EXTRA_TITLE, -1);
        switch (code) {
            case 0:
                String merchant = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "merchant");
                etSendRedPacketChildOne.setText(merchant);
                KeyboardUtil.showKeyBoardDelay(ChildSendRedPacketActivity.this, etSendRedPacketChildOne);
                text_num = 20;
                linerSendRedPacketChildOne.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setText("标题内容不得超过20个字");
                etSendRedPacketChildOne.setHint("输入标题");
                tvSendRedPacketChildOne.setText(text_num - merchant.length() + "");
                tvSendRedPacketChildTitle.setText("标题");
                etSendRedPacketChildOne.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text_num)});
                etSendRedPacketChildOne.setSelection(etSendRedPacketChildOne.getText().toString().length());
                break;
            case 1:
                String merchant_des = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "merchant_des");
                etSendRedPacketChildThree.setText(merchant_des);
                KeyboardUtil.showKeyBoardDelay(ChildSendRedPacketActivity.this, etSendRedPacketChildThree);
                text_num = 50;
                linerSendRedPacketChildThree.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setText("文字内容不得超过50个字");
                etSendRedPacketChildThree.setHint("输入文字内容");
                tvSendRedPacketChildThree.setText(text_num - merchant_des.length() + "");
                tvSendRedPacketChildTitle.setText("内容");
                etSendRedPacketChildThree.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text_num)});
                etSendRedPacketChildThree.setSelection(etSendRedPacketChildThree.getText().toString().length());
                break;
            case 2:
                etSendRedPacketChildOne.setText(SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "tel"));
                String address = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "address");
                etSendRedPacketChildThree.setText(address);
                KeyboardUtil.showKeyBoardDelay(ChildSendRedPacketActivity.this, etSendRedPacketChildOne);
                text_num = 50;
                linerSendRedPacketChildOne.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setText("联系地址不得超过50个字");
                etSendRedPacketChildOne.setHint("联系电话");
                tvSendRedPacketChildOne.setVisibility(View.GONE);
                tvSendRedPacketChildTitle.setText("联系方式");
                etSendRedPacketChildOne.setInputType(InputType.TYPE_CLASS_NUMBER);
                String digits = "1234567890-";
                etSendRedPacketChildOne.setKeyListener(DigitsKeyListener.getInstance(digits));
                etSendRedPacketChildOne.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                linerSendRedPacketChildThree.setVisibility(View.VISIBLE);
                etSendRedPacketChildThree.setHint("联系地址");
                tvSendRedPacketChildThree.setText(text_num - address.length() + "");
                etSendRedPacketChildThree.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text_num)});
                break;
            case 3:
                String question = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "question");
                etSendRedPacketChildOne.setText(question);
                KeyboardUtil.showKeyBoardDelay(ChildSendRedPacketActivity.this, etSendRedPacketChildOne);
                text_num = 15;
                linerSendRedPacketChildOne.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setText("红包提问不得超过15个字");
                etSendRedPacketChildOne.setHint("输入问题");
                tvSendRedPacketChildOne.setText(text_num - question.length() + "");
                tvSendRedPacketChildTitle.setText("红包提问");
                etSendRedPacketChildOne.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text_num)});
                etSendRedPacketChildOne.setSelection(etSendRedPacketChildOne.getText().toString().length());

                break;
            case 4:
                String first_answer = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "first_answer");
                etSendRedPacketChildOne.setText(first_answer);
                KeyboardUtil.showKeyBoardDelay(ChildSendRedPacketActivity.this, etSendRedPacketChildOne);
                text_num = 15;
                linerSendRedPacketChildOne.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setText("选项内容不得超过15个字");
                etSendRedPacketChildOne.setHint("正确选项");
                tvSendRedPacketChildOne.setText(text_num - first_answer.length() + "");
                tvSendRedPacketChildTitle.setText("正确选项");
                etSendRedPacketChildOne.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text_num)});
                etSendRedPacketChildOne.setSelection(etSendRedPacketChildOne.getText().toString().length());
                break;
            case 5:
                String second_answer = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "second_answer");
                etSendRedPacketChildOne.setText(second_answer);
                String third_answer = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "third_answer");
                etSendRedPacketChildTwo.setText(third_answer);
                KeyboardUtil.showKeyBoardDelay(ChildSendRedPacketActivity.this, etSendRedPacketChildOne);
                text_num = 15;
                linerSendRedPacketChildOne.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setText("选项内容不得超过15个字");
                etSendRedPacketChildOne.setHint("错误选项1");
                tvSendRedPacketChildOne.setText(text_num - second_answer.length() + "");
                tvSendRedPacketChildTitle.setText("错误选项");
                etSendRedPacketChildOne.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text_num)});
                text_num1 = 15;
                linerSendRedPacketChildTwo.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setText("选项内容不得超过15个字");
                etSendRedPacketChildTwo.setHint("错误选项2");
                tvSendRedPacketChildTwo.setText(text_num - third_answer.length() + "");
                etSendRedPacketChildTwo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text_num1)});
                break;
            case 6:
                Boolean type = SharePreferenceUtil.getBoolean(ChildSendRedPacketActivity.this, "type", false);
                String distance = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "distance");
                if (type) {
                    etSendRedPacketChildOne.setEnabled(false);
                    etSendRedPacketChildOne.setText("");
                } else {
                    etSendRedPacketChildOne.setEnabled(true);
                    etSendRedPacketChildOne.setText(distance);
                }
                KeyboardUtil.showKeyBoardDelay(ChildSendRedPacketActivity.this, etSendRedPacketChildOne);
                cbSendRedPacketChildRule.setVisibility(View.VISIBLE);
                cbSendRedPacketChildRule.setChecked(type);

                linerSendRedPacketChildOne.setVisibility(View.VISIBLE);
                tvSendRedPacketChildRule.setVisibility(View.GONE);
                etSendRedPacketChildOne.setHint("输入范围    （km）");
                tvSendRedPacketChildOne.setText("km");
                tvSendRedPacketChildTitle.setText("发放范围");
                etSendRedPacketChildOne.setInputType(InputType.TYPE_CLASS_NUMBER);
                etSendRedPacketChildOne.setSelection(etSendRedPacketChildOne.getText().toString().length());
                break;
        }
        cbSendRedPacketChildRule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChildSendRedPacketActivity.this);
                    builder.setTitle("提示")
                            .setIcon(R.drawable.ic_logo)
                            .setMessage("确认清空范围改为全国发放？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etSendRedPacketChildOne.setEnabled(false);
                                    etSendRedPacketChildOne.setText("");
                                    cbSendRedPacketChildRule.setChecked(true);
                                    Intent intent = new Intent();
                                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "type", true);
                                    intent.putExtra("EXTRA_RESULT", "");
                                    setResult(6, intent);
                                    finish();

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etSendRedPacketChildOne.setEnabled(true);
                                    cbSendRedPacketChildRule.setChecked(false);
                                }
                            })
                            .show();
                } else {
                    etSendRedPacketChildOne.setEnabled(true);
                }
            }
        });
        etSendRedPacketChildOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (code) {
                    case 0:
                        if ((s.length() <= text_num)) {
                            tvSendRedPacketChildOne.setText((text_num - s.length()) + "");
                        }
                        break;
                    case 3:
                        if ((s.length() <= text_num)) {
                            tvSendRedPacketChildOne.setText((text_num - s.length()) + "");
                        }
                        break;
                    case 4:
                        if ((s.length() <= text_num)) {
                            tvSendRedPacketChildOne.setText((text_num - s.length()) + "");
                        }
                        break;
                    case 5:
                        if ((s.length() <= text_num)) {
                            tvSendRedPacketChildOne.setText((text_num - s.length()) + "");
                        }
                        break;

                }
            }
        });
        etSendRedPacketChildTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (code) {
                    case 5:
                        if ((s.length() <= text_num1)) {
                            tvSendRedPacketChildTwo.setText((text_num1 - s.length()) + "");
                        }
                        break;

                }
            }
        });
        etSendRedPacketChildThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (code) {
                    case 1:
                        if ((s.length() <= text_num)) {
                            tvSendRedPacketChildThree.setText((text_num - s.length()) + "");
                        }
                        break;
                    case 2:
                        if ((s.length() <= text_num)) {
                            tvSendRedPacketChildThree.setText((text_num - s.length()) + "");
                        }
                        break;

                }
            }
        });
    }

    @OnClick({R.id.liner_send_red_packet_child_back, R.id.liner_send_red_packet_child_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_send_red_packet_child_back:
                switch (code) {
                    case 0:
                        String merchant = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "merchant");
                        if (!(etSendRedPacketChildOne.getText().toString()).equals(merchant)) {
                            back();
                        } else {
                            onBackPressed();
                        }
                        break;
                    case 1:
                        String merchant_des = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "merchant_des");
                        if (!etSendRedPacketChildThree.getText().toString().equals(merchant_des)) {
                            back();
                        } else {
                            onBackPressed();
                        }
                        break;
                    case 2:
                        String address = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "address");
                        if (!etSendRedPacketChildThree.getText().toString().equals(address) || !etSendRedPacketChildOne.getText().toString().equals(SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "tel"))) {
                            back();
                        } else {
                            onBackPressed();
                        }
                        break;
                    case 3:
                        String question = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "question");
                        if (!etSendRedPacketChildOne.getText().toString().equals(question)) {
                            back();
                        } else {
                            onBackPressed();
                        }
                        break;
                    case 4:
                        String first_answer = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "first_answer");
                        if (!etSendRedPacketChildOne.getText().toString().equals(first_answer)) {
                            back();
                        } else {
                            onBackPressed();
                        }
                        break;
                    case 5:
                        String second_answer = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "second_answer");

                        String third_answer = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "third_answer");
                        if (!etSendRedPacketChildOne.getText().toString().equals(second_answer) || !etSendRedPacketChildTwo.getText().toString().equals(third_answer)) {
                            back();
                        } else {
                            onBackPressed();
                        }
                        break;
                    case 6:
                        String distance = SharePreferenceUtil.get(ChildSendRedPacketActivity.this, "distance");
                        if (!etSendRedPacketChildOne.getText().toString().equals(distance)) {
                            back();
                        } else {
                            onBackPressed();
                        }
                        break;

                }
                break;
            case R.id.liner_send_red_packet_child_save:
                save();
                break;
        }
    }

    private void save() {
        Intent intent = new Intent();
        switch (code) {
            case 0:
                if (TextUtils.isEmpty(etSendRedPacketChildOne.getText().toString())) {
                    ToastUtils.toTosat(ChildSendRedPacketActivity.this, "请输入标题");
                } else {
                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "merchant", etSendRedPacketChildOne.getText().toString());
                    intent.putExtra("EXTRA_RESULT", etSendRedPacketChildOne.getText().toString());
                    setResult(0, intent);
                    finish();
                }

                break;
            case 1:
                if (TextUtils.isEmpty(etSendRedPacketChildThree.getText().toString())) {
                    ToastUtils.toTosat(ChildSendRedPacketActivity.this, "请输入内容");
                } else {
                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "merchant_des", etSendRedPacketChildThree.getText().toString());
                    intent.putExtra("EXTRA_RESULT", etSendRedPacketChildThree.getText().toString());
                    setResult(1, intent);
                    finish();
                }

                break;
            case 2:
                SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "tel", etSendRedPacketChildOne.getText().toString());
                SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "address", etSendRedPacketChildThree.getText().toString());
                intent.putExtra("EXTRA_RESULT", etSendRedPacketChildOne.getText().toString() + "--" + etSendRedPacketChildThree.getText().toString());
                setResult(2, intent);
                finish();
                break;
            case 3:
                if (TextUtils.isEmpty(etSendRedPacketChildOne.getText().toString())) {
                    ToastUtils.toTosat(ChildSendRedPacketActivity.this, "请输入提问内容");
                } else {
                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "question", etSendRedPacketChildOne.getText().toString());
                    intent.putExtra("EXTRA_RESULT", etSendRedPacketChildOne.getText().toString());
                    setResult(3, intent);
                    finish();
                }

                break;
            case 4:
                if (TextUtils.isEmpty(etSendRedPacketChildOne.getText().toString())) {
                    ToastUtils.toTosat(ChildSendRedPacketActivity.this, "请输入正确选项");
                } else {
                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "first_answer", etSendRedPacketChildOne.getText().toString());
                    intent.putExtra("EXTRA_RESULT", etSendRedPacketChildOne.getText().toString());
                    setResult(4, intent);
                    finish();
                }

                break;
            case 5:
                if (TextUtils.isEmpty(etSendRedPacketChildOne.getText().toString()) || TextUtils.isEmpty(etSendRedPacketChildTwo.getText().toString())) {
                    ToastUtils.toTosat(ChildSendRedPacketActivity.this, "请输入错误选项");
                } else {
                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "second_answer", etSendRedPacketChildOne.getText().toString());
                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "third_answer", etSendRedPacketChildTwo.getText().toString());
                    intent.putExtra("EXTRA_RESULT", etSendRedPacketChildOne.getText().toString() + "--" + etSendRedPacketChildTwo.getText().toString());
                    setResult(5, intent);
                    finish();
                }

                break;
            case 6:
                if (cbSendRedPacketChildRule.isChecked()) {
                    SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "type", true);
                    intent.putExtra("EXTRA_RESULT", "");
                    setResult(6, intent);
                    finish();
                } else {
                    if (TextUtils.isEmpty(etSendRedPacketChildOne.getText().toString())) {
                        ToastUtils.toTosat(ChildSendRedPacketActivity.this, "请输入提醒范围");
                    } else {
                        if (Integer.parseInt(etSendRedPacketChildOne.getText().toString()) >= 1) {
                            SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "type", false);
                            SharePreferenceUtil.put(ChildSendRedPacketActivity.this, "distance", etSendRedPacketChildOne.getText().toString());
                            intent.putExtra("EXTRA_RESULT", etSendRedPacketChildOne.getText().toString());
                            setResult(6, intent);
                            finish();
                        } else {
                            ToastUtils.toTosat(ChildSendRedPacketActivity.this, "提醒范围不得小于1km");
                        }
                    }
                }

                break;
        }
    }

    public void back() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setIcon(R.drawable.ic_logo)
                .setMessage("是否保存修改后的内容？")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save();
                    }
                })
                .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }


}
