package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/9.21:26
 */

public class SendMessageResponse implements Parcelable {

    /**
     * err : 0
     * msg : 登陆验证码发送成功
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

    public SendMessageResponse() {
    }

    protected SendMessageResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<SendMessageResponse> CREATOR = new Parcelable.Creator<SendMessageResponse>() {
        @Override
        public SendMessageResponse createFromParcel(Parcel source) {
            return new SendMessageResponse(source);
        }

        @Override
        public SendMessageResponse[] newArray(int size) {
            return new SendMessageResponse[size];
        }
    };
}
