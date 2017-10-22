package com.haoxiong.taotao.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haoxiong.taotao.R;
import com.squareup.picasso.Picasso;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/7.22:06
 */

public class GlideUtil {
    public static void loadImg(Context context, String imageUrl, ImageView imageView) {
        Picasso
                .with(context)
                .load(imageUrl)
                .placeholder(R.mipmap.item)
                .error(R.mipmap.item)
                .resize(WindowUtil.getWidth(context),WindowUtil.getWidth(context)/2)
                .into(imageView);
    }
    public static void loadImg(Context context,int defoult, String imageUrl, ImageView imageView) {
        Picasso
                .with(context)
                .load(imageUrl)
                .placeholder(defoult)
                .error(defoult)
                .centerCrop()
                .resize(DensityUtil.dip2px(context,60),DensityUtil.dip2px(context,60))
                .into(imageView);
    }
}
