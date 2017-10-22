package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/10/8.11:02
 */

public class AlipayResponse implements Parcelable {

    /**
     * err : 0
     * msg : 支付成功
     * data :
     */

    private int err;
    private String msg;
    private String data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.err);
        dest.writeString(this.msg);
        dest.writeString(this.data);
    }

    public AlipayResponse() {
    }

    protected AlipayResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<AlipayResponse> CREATOR = new Parcelable.Creator<AlipayResponse>() {
        @Override
        public AlipayResponse createFromParcel(Parcel source) {
            return new AlipayResponse(source);
        }

        @Override
        public AlipayResponse[] newArray(int size) {
            return new AlipayResponse[size];
        }
    };
}
