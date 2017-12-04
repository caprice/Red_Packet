package com.haoxiong.taotao.ui.message.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoxiong.taotao.R;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/3.14:44
 */

public class MessageAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public MessageAdapter(@Nullable List<Object> data) {
        super(R.layout.message_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        int indexOf = getData().indexOf(item);
        helper.setText(R.id.message_title, "西南石油大学"+indexOf)
                .setText(R.id.message_time, "17/10/12"+indexOf)
                .setText(R.id.message_content, "请阿斯顿奶水多但撒娇阿斯请阿斯顿奶水多但撒娇阿斯达请阿斯顿奶水多但撒娇阿斯达达"+indexOf);
    }
}
