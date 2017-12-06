package com.haoxiong.taotao.ui.message.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/4.23:23
 */

public class Message implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;
    private String time;
    private String content;
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Message(int itemType, String time, String content) {
        this.itemType = itemType;
        this.time = time;
        this.content = content;
    }

    public Message() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
