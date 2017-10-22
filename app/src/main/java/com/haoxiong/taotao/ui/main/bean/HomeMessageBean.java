package com.haoxiong.taotao.ui.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class HomeMessageBean implements Parcelable {
    private String title;

    public HomeMessageBean(String title) {
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

    protected HomeMessageBean(Parcel in) {
        this.title = in.readString();
    }

    public static final Parcelable.Creator<HomeMessageBean> CREATOR = new Parcelable.Creator<HomeMessageBean>() {
        @Override
        public HomeMessageBean createFromParcel(Parcel source) {
            return new HomeMessageBean(source);
        }

        @Override
        public HomeMessageBean[] newArray(int size) {
            return new HomeMessageBean[size];
        }
    };
}
