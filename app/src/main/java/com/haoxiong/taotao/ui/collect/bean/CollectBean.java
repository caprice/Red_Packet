package com.haoxiong.taotao.ui.collect.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class CollectBean implements Parcelable {
    private String title;

    public CollectBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    protected CollectBean(Parcel in) {
        this.title = in.readString();
    }

    public static final Creator<CollectBean> CREATOR = new Creator<CollectBean>() {
        @Override
        public CollectBean createFromParcel(Parcel source) {
            return new CollectBean(source);
        }

        @Override
        public CollectBean[] newArray(int size) {
            return new CollectBean[size];
        }
    };
}
