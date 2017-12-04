package com.haoxiong.taotao.ui.message.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.message.User;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/4.23:23
 */

public class MessageDetailAdapter extends BaseMultiItemQuickAdapter<User,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MessageDetailAdapter(List<User> data) {
        super(data);
        addItemType(User.TEXT, R.layout.message_detail_right_item);
        addItemType(User.IMG, R.layout.message_detail_left_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        int indexOf = getData().indexOf(item);
        switch (helper.getItemViewType()) {
            case User.TEXT:
                helper.setText(R.id.tv_right_time, "12:12"+ indexOf);
                helper.setText(R.id.tv_right_content, "你右边"+ indexOf);
                break;
            case User.IMG:
                helper.setText(R.id.tv_left_time, "12:12"+ indexOf);
                helper.setText(R.id.tv_left_content, "你左边"+ indexOf);
                break;
        }


    }
}
