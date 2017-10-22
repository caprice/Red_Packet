package com.haoxiong.taotao.ui.friendslist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fan.service.response.FriendListResponse;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.friendslist.bean.FriendListBean;
import com.haoxiong.taotao.util.GlideUtil;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class FriendListAdapter extends BaseQuickAdapter<FriendListResponse.DataBean, BaseViewHolder> {
    Context context;
    public FriendListAdapter(Context context,@Nullable List<FriendListResponse.DataBean> data) {
        super(R.layout.friend_list_recycle_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final FriendListResponse.DataBean item) {
        helper.setText(R.id.tv_item_name, item.getUserName());
        ImageView imageView = helper.getView(R.id.img_end_img);
        GlideUtil.loadImg(context,R.mipmap.head,item.getUserPic(),imageView);
        helper.setText(R.id.tv_item_self_money, putTextColor("已抢到的红包：",item.getSumMoney()+"元"));
        helper.setText(R.id.tv_item_for_me_money, putTextColor("给我创造的奖金：",item.getMoneyToMe()+"元"));
    }

    private SpannableStringBuilder putTextColor(String content, String changeColor) {
        String detail = content + changeColor;
        SpannableStringBuilder builder = new SpannableStringBuilder(detail);

//ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#d43c33"));
        ForegroundColorSpan blankSpan = new ForegroundColorSpan(Color.parseColor("#666666"));

        builder.setSpan(blankSpan,0,content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(redSpan,content.length(),detail.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
