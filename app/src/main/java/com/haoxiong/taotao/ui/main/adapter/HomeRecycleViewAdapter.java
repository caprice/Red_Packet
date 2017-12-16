package com.haoxiong.taotao.ui.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.Client;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.response.RedPacketDetailResponse;
import com.fan.service.response.RedPacketListResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.main.bean.HomeMessageBean;
import com.haoxiong.taotao.util.GlideUtil;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class HomeRecycleViewAdapter extends BaseQuickAdapter<RedPacketListResponse.DataBean, BaseViewHolder> {

    private Context context;

    public HomeRecycleViewAdapter(@LayoutRes int layoutResId, Context context, @Nullable List<RedPacketListResponse.DataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RedPacketListResponse.DataBean item) {
        helper.setText(R.id.item_title, item.getMerchant());
        helper.setText(R.id.home_money, item.getMoney() + "元/" + item.getPCount() + "份");
        helper.setText(R.id.home_mun, item.getRemainCount() + "份");

        if (TextUtils.isEmpty(item.getRealdistance())) {
            helper.setVisible(R.id.item_distance, false);
        } else {
            helper.setVisible(R.id.item_distance, true);
            if (MyApp.location == null) {
                helper.setVisible(R.id.item_distance, false);
            } else {
                helper.setText(R.id.item_distance, item.getRealdistance());
            }
        }
        ImageView imageView = helper.getView(R.id.imageView2);
        ImageView imageView1 = helper.getView(R.id.imageView3);
        TextView home_donging = helper.getView(R.id.home_donging);
        switch (item.getGot()) {
            case 2:
                if (item.getRemainCount() != 0) {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_enalbe);
                    home_donging.setTextColor(Color.parseColor("#343338"));
                    home_donging.setText("顶一个");
                } else {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_albe);
                    home_donging.setTextColor(Color.parseColor("#aea174"));
                    home_donging.setText("顶满了");
                }
                break;
            case 1:
                if (item.getRemainCount() != 0) {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_albe);
                    home_donging.setTextColor(Color.parseColor("#aea174"));
                    home_donging.setText("已顶过");
                } else {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_albe);
                    home_donging.setTextColor(Color.parseColor("#aea174"));
                    home_donging.setText("顶满了");
                }
                break;
        }
        String[] split = item.getMer_pics().split("&");
        if (split[0].contains("http")) {
            GlideUtil.loadImg(context,split[0], imageView);
        } else {
            GlideUtil.loadImg(context, Client.BASE_URL_IMG+split[0], imageView);
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
                GlideUtil.loadImg(context, Client.BASE_URL_IMG +split[0], imageView1);
            }
        }


    }

    public void replaceItem(int position, RedPacketDetailResponse.DataBean dataBean) {
        getData().get(position).setGot(getData().get(position).getGot());
        getData().get(position).setRemainCount(dataBean.getRemainCount());
        notifyItemChanged(position);
    }
}
