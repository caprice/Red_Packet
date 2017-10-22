package com.haoxiong.taotao.ui.balancedetail.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.response.BalanceDetailResponse;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.balancedetail.bean.BanlanceBean;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/29.17:49
 */

public class BalanceDetailAdapter extends BaseQuickAdapter<BalanceDetailResponse.DataBean.RecordsBean.RecordBean, BaseViewHolder> {

    public BalanceDetailAdapter(@Nullable List<BalanceDetailResponse.DataBean.RecordsBean.RecordBean> data) {
        super(R.layout.balance_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalanceDetailResponse.DataBean.RecordsBean.RecordBean item) {
        helper.setText(R.id.tv_balance_year, item.getTime().split(" ")[0]);



        switch (item.getType().trim()) {
            case "1":
                helper.setText(R.id.tv_balance_money, item.getMoney());
                helper.setText(R.id.tv_balance_content, item.getTitle() != null ? item.getTitle() : "其他");
                helper.setImageResource(R.id.img_balance_type, R.drawable.red);
                break;
            case "2":
                helper.setText(R.id.tv_balance_money, "-"+item.getMoney());
                helper.setText(R.id.tv_balance_content, item.getTitle() != null ? item.getTitle() : "其他");
                helper.setImageResource(R.id.img_balance_type, R.drawable.ad);
                break;
            case "3":
                helper.setText(R.id.tv_balance_money, item.getMoney());
                helper.setText(R.id.tv_balance_content, item.getTitle() != null ? "(奖金)"+item.getTitle() : "其他");
                helper.setImageResource(R.id.img_balance_type, R.drawable.diamond);
                break;
            case "4":
                helper.setText(R.id.tv_balance_money, item.getMoney());
                helper.setText(R.id.tv_balance_content, item.getTitle() != null ? item.getTitle() : "其他");
                helper.setImageResource(R.id.img_balance_type, R.drawable.withdrawal);
                break;
            default:
                helper.setText(R.id.tv_balance_money, item.getMoney());
                helper.setText(R.id.tv_balance_content, "其他");
                helper.setImageResource(R.id.img_balance_type, R.drawable.withdrawal);
                break;
        }
    }
}
