package com.haoxiong.taotao.ui.map.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoxiong.taotao.R;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/19.13:08
 */

public class PopupRecyclerAdapter extends BaseQuickAdapter<PoiItem,BaseViewHolder> {
    public PopupRecyclerAdapter(@Nullable List<PoiItem> data) {
        super(R.layout.item_map_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_item_map_content, item.getTitle());
    }
}
