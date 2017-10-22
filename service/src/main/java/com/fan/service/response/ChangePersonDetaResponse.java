package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.11:12
 */

public class ChangePersonDetaResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
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

    public ChangePersonDetaResponse() {
    }

    protected ChangePersonDetaResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<ChangePersonDetaResponse> CREATOR = new Parcelable.Creator<ChangePersonDetaResponse>() {
        @Override
        public ChangePersonDetaResponse createFromParcel(Parcel source) {
            return new ChangePersonDetaResponse(source);
        }

        @Override
        public ChangePersonDetaResponse[] newArray(int size) {
            return new ChangePersonDetaResponse[size];
        }
    };
}
