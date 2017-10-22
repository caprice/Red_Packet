package com.haoxiong.taotao.ui.redpacket.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.response.RedPacketDetailResponse;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.util.GlideUtil;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.0:29
 */

public class RecycleRedPacketWinerAdapter extends BaseQuickAdapter<RedPacketDetailResponse.DataBean.GetterBean, BaseViewHolder> {
    Context context;

    public RecycleRedPacketWinerAdapter(@LayoutRes int layoutResId, Context context, @Nullable List<RedPacketDetailResponse.DataBean.GetterBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RedPacketDetailResponse.DataBean.GetterBean item) {
        helper.setText(R.id.tv_item_red_name, item.getUserName());
        helper.setText(R.id.tv_item_red_add, item.getAddress());
        helper.setText(R.id.tv_item_red_time, item.getGetTime());
        helper.setText(R.id.tv_item_red_money, item.getMoney()+"元");
        ImageView img_item_red_pic = helper.getView(R.id.img_item_red_pic);
        GlideUtil.loadImg(context, item.getUserPic(), img_item_red_pic);
    }
}
