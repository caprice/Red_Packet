package com.haoxiong.taotao.ui.redmaneger.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.Client;
import com.fan.service.response.RedManagerResponse;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.redmaneger.bean.MangerMessageEndBean;
import com.haoxiong.taotao.util.GlideUtil;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class ManagerRecycleViewEndAdapter extends BaseQuickAdapter<RedManagerResponse.DataBean.RedsOffBean, BaseViewHolder> {
    private Context context;
    public ManagerRecycleViewEndAdapter(@LayoutRes int layoutResId, @Nullable List<RedManagerResponse.DataBean.RedsOffBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RedManagerResponse.DataBean.RedsOffBean item) {
        ImageView view = helper.getView(R.id.img_end_img);
        ImageView view1 = helper.getView(R.id.img_end_img1);
        helper.setText(R.id.tv_end_title, item.getMerchant());
        helper.setText(R.id.tv_end_time, item.getTime());
        helper.setText(R.id.tv_end_content, item.getPCount() + "");
        String[] split = item.getPic().split("&");
        if (split[0].contains("http")) {
            GlideUtil.loadImg(context,split[0], view);
        } else {
            GlideUtil.loadImg(context, Client.BASE_URL_IMG +split[0], view);
        }
        try {
            if (split[1].contains("http")) {
                GlideUtil.loadImg(context,split[1], view1);
            } else {
                GlideUtil.loadImg(context, Client.BASE_URL_IMG + split[1], view1);
            }
        } catch (Exception e) {
            if (split[0].contains("http")) {
                GlideUtil.loadImg(context,split[0], view1);
            } else {
                GlideUtil.loadImg(context, Client.BASE_URL_IMG+split[0], view1);
            }
        }
    }
}
