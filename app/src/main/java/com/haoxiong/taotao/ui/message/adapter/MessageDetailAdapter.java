package com.haoxiong.taotao.ui.message.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.message.bean.Message;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/4.23:23
 */

public class MessageDetailAdapter extends BaseMultiItemQuickAdapter<Message,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MessageDetailAdapter(List<Message> data) {
        super(data);
        addItemType(Message.TEXT, R.layout.message_detail_right_item);
        addItemType(Message.IMG, R.layout.message_detail_left_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        int indexOf = getData().indexOf(item);
        switch (helper.getItemViewType()) {
            case Message.TEXT:
                helper.setText(R.id.tv_right_time, item.getTime());
                helper.setText(R.id.tv_right_content,  item.getContent());
                break;
            case Message.IMG:
                helper.setText(R.id.tv_left_time,  item.getTime());
                helper.setText(R.id.tv_left_content,  item.getContent());
                break;
        }


    }
}
