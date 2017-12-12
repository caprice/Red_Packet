package com.haoxiong.taotao.ui.redmaneger.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.Client;
import com.fan.service.response.RedManagerResponse;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.callback.AdapterCallBack;
import com.haoxiong.taotao.ui.redmaneger.bean.MangerMessageRunBean;
import com.haoxiong.taotao.util.GlideUtil;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class ManagerRecycleViewRunAdapter extends BaseQuickAdapter<RedManagerResponse.DataBean.RedsOnBean, BaseViewHolder> {
    private Context context;
    public ManagerRecycleViewRunAdapter(@LayoutRes int layoutResId, Context context, @Nullable List<RedManagerResponse.DataBean.RedsOnBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RedManagerResponse.DataBean.RedsOnBean item) {
        helper.setText(R.id.tv_manager_title, item.getMerchant());
        ImageView imageView = helper.getView(R.id.img_manager_picture);
        ImageView imageView1 = helper.getView(R.id.img_manager_picture1);
        TextView item_state = helper.getView(R.id.item_state);
        helper.setText(R.id.tv_manger_charge,item.getMoney() +"元/"+item.getPCount()+"个");
        helper.setText(R.id.tv_manger_num, item.getRemainCount()+"个");
        helper.addOnClickListener(R.id.liner_state);
        switch (item.getStatus()) {
            case 0:
                item_state.setText("审核中");
                item_state.setTextColor(Color.parseColor("#333333"));
                item_state.setBackgroundResource(R.drawable.red_pacekt_inreview);
                break;
            case 1:
                item_state.setText("暂 停");
                item_state.setTextColor(Color.parseColor("#ffffff"));
                item_state.setBackgroundResource(R.drawable.red_pacekt_stop);
                break;
            case 2:
                item_state.setText("开 始");
                item_state.setTextColor(Color.parseColor("#ffffff"));
                item_state.setBackgroundResource(R.drawable.red_pacekt_enalbe);
                break;
        }
        String[] split = item.getPic().split("&");
        if (split[0].contains("http")) {
            GlideUtil.loadImg(context,split[0], imageView);
        } else {
            GlideUtil.loadImg(context, Client.BASE_URL_IMG +split[0], imageView);
        }
        try {
            if (split[1].contains("http")) {
                GlideUtil.loadImg(context,split[1], imageView1);
            } else {
                GlideUtil.loadImg(context, Client.BASE_URL_IMG+ split[1], imageView1);
            }
        } catch (Exception e) {
            if (split[0].contains("http")) {
                GlideUtil.loadImg(context,split[0], imageView1);
            } else {
                GlideUtil.loadImg(context, Client.BASE_URL_IMG+split[0], imageView1);
            }
        }
    }

}
