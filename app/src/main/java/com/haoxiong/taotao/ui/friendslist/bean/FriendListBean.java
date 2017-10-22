package com.haoxiong.taotao.ui.friendslist.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:23
 */

public class FriendListBean implements Parcelable {
    private String title;

    public FriendListBean(String title) {
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

    protected FriendListBean(Parcel in) {
        this.title = in.readString();
    }

    public static final Creator<FriendListBean> CREATOR = new Creator<FriendListBean>() {
        @Override
        public FriendListBean createFromParcel(Parcel source) {
            return new FriendListBean(source);
        }

        @Override
        public FriendListBean[] newArray(int size) {
            return new FriendListBean[size];
        }
    };
}
