package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/14.22:57
 */

public class SendRedPacketResponse implements Parcelable {

    /**
     * err : 0
     * msg : 申请成功，请等待审核
     * data : 81
     */

    private int err;
    private String msg;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
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
        dest.writeInt(this.data);
    }

    public SendRedPacketResponse() {
    }

    protected SendRedPacketResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readInt();
    }

    public static final Parcelable.Creator<SendRedPacketResponse> CREATOR = new Parcelable.Creator<SendRedPacketResponse>() {
        @Override
        public SendRedPacketResponse createFromParcel(Parcel source) {
            return new SendRedPacketResponse(source);
        }

        @Override
        public SendRedPacketResponse[] newArray(int size) {
            return new SendRedPacketResponse[size];
        }
    };
}
