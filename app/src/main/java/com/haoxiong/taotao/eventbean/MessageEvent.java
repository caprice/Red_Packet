package com.haoxiong.taotao.eventbean;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.LatLonPoint;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/6.23:30
 */

public class MessageEvent implements Parcelable {
    private  String address;
    private LatLonPoint latLng;

    public MessageEvent(String address, LatLonPoint latLng) {
        this.address = address;
        this.latLng = latLng;
    }

    public MessageEvent() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLonPoint getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLonPoint latLng) {
        this.latLng = latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeParcelable(this.latLng, flags);
    }

    protected MessageEvent(Parcel in) {
        this.address = in.readString();
        this.latLng = in.readParcelable(LatLonPoint.class.getClassLoader());
    }

    public static final Creator<MessageEvent> CREATOR = new Creator<MessageEvent>() {
        @Override
        public MessageEvent createFromParcel(Parcel source) {
            return new MessageEvent(source);
        }

        @Override
        public MessageEvent[] newArray(int size) {
            return new MessageEvent[size];
        }
    };
}
