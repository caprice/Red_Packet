package com.haoxiong.taotao.ui.redmaneger.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class MangerMessageEndBean implements Parcelable {
    private String title;

    public MangerMessageEndBean(String title) {
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

    protected MangerMessageEndBean(Parcel in) {
        this.title = in.readString();
    }

    public static final Creator<MangerMessageEndBean> CREATOR = new Creator<MangerMessageEndBean>() {
        @Override
        public MangerMessageEndBean createFromParcel(Parcel source) {
            return new MangerMessageEndBean(source);
        }

        @Override
        public MangerMessageEndBean[] newArray(int size) {
            return new MangerMessageEndBean[size];
        }
    };
}
