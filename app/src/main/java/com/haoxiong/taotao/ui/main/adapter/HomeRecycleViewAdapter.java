package com.haoxiong.taotao.ui.main.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.response.RedPacketListResponse;
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
        helper.setText(R.id.home_money, item.getMoney() + "元/" + item.getPCount() + "个");
        helper.setText(R.id.home_mun, item.getRemainCount() + "个");

        if (TextUtils.isEmpty(item.getRealdistance())) {
            helper.setVisible(R.id.item_distance, false);
        } else {
            helper.setVisible(R.id.item_distance, true);
            helper.setText(R.id.item_distance, item.getRealdistance());
        }
        ImageView imageView = helper.getView(R.id.imageView2);
        TextView home_donging = helper.getView(R.id.home_donging);
        switch (item.getGot()) {
            case 2:
                if (item.getRemainCount() != 0) {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_enalbe);
                    home_donging.setText("抢红包");
                } else {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_albe);
                    home_donging.setText("已抢完");
                }
                break;
            case 1:
                if (item.getRemainCount() != 0) {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_albe);
                    home_donging.setText("已抢过");
                } else {
                    home_donging.setBackgroundResource(R.drawable.red_pacekt_albe);
                    home_donging.setText("已抢完");
                }
                break;
        }
        GlideUtil.loadImg(context, "http://hb.huidang2105.com:8900/public/"+item.getMer_pics(), imageView);
    }
}
