package com.haoxiong.taotao.ui.balancedetail.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/29.17:49
 */

public class BanlanceBean implements Parcelable {
    private String title;

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

    public BanlanceBean() {
    }

    protected BanlanceBean(Parcel in) {
        this.title = in.readString();
    }

    public static final Parcelable.Creator<BanlanceBean> CREATOR = new Parcelable.Creator<BanlanceBean>() {
        @Override
        public BanlanceBean createFromParcel(Parcel source) {
            return new BanlanceBean(source);
        }

        @Override
        public BanlanceBean[] newArray(int size) {
            return new BanlanceBean[size];
        }
    };
}
