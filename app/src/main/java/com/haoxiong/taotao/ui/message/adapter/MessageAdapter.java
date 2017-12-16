package com.haoxiong.taotao.ui.message.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.response.MessageResponse;
import com.haoxiong.taotao.R;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/3.14:44
 */

public class MessageAdapter extends BaseQuickAdapter<MessageResponse.DataBean.ListBean.ListLbBean, BaseViewHolder> {
    public MessageAdapter(@Nullable List<MessageResponse.DataBean.ListBean.ListLbBean> data) {
        super(R.layout.message_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageResponse.DataBean.ListBean.ListLbBean item) {
        if (item.getWdxxNum() == 0) {
            helper.setVisible(R.id.message_num1, false);
        } else {
            helper.setVisible(R.id.message_num1, true);

        }
        helper.setText(R.id.message_title, item.getLtbt())
                .setText(R.id.message_time, item.getSj())
                .setText(R.id.message_num1, item.getWdxxNum()+"")
                .setText(R.id.message_content, item.getLtxx());

    }
}
