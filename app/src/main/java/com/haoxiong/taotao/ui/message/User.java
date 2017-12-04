package com.haoxiong.taotao.ui.message;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/4.23:23
 */

public class User implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
