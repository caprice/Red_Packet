package com.haoxiong.taotao.ui.collect.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.Client;
import com.fan.service.response.CollectRedPacketResponse;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.collect.bean.CollectBean;
import com.haoxiong.taotao.util.GlideUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class CollectAdapter extends BaseQuickAdapter<CollectRedPacketResponse.DataBean, BaseViewHolder> {
    private Context context;

    public CollectAdapter(@LayoutRes int layoutResId, Context context, @Nullable List<CollectRedPacketResponse.DataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CollectRedPacketResponse.DataBean item) {
        helper.setText(R.id.tv_end_title, item.getMerchant());
        helper.setText(R.id.tv_end_time, item.getGive_time());
        helper.setText(R.id.tv_end_content, "");
        ImageView view = helper.getView(R.id.img_end_img);
        ImageView view1 = helper.getView(R.id.img_end_img1);
        String[] split = item.getUser_pic().split("&");
        if (split[0].contains("http")) {
            GlideUtil.loadImg(context,split[0], view);
        } else {
            GlideUtil.loadImg(context, Client.BASE_URL_IMG +split[0], view);
        }
        try {
            if (split[1].contains("http")) {
                GlideUtil.loadImg(context,split[1], view1);
            } else {
                GlideUtil.loadImg(context, Client.BASE_URL_IMG+ split[1], view1);
            }
        } catch (Exception e) {
            if (split[0].contains("http")) {
                GlideUtil.loadImg(context,split[0], view1);
            } else {
                GlideUtil.loadImg(context, Client.BASE_URL_IMG +split[0], view1);
            }
        }
    }
}
