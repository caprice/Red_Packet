package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.23:38
 */

public class StopOrNotResponse implements Parcelable {

    /**
     * err : 0
     * msg : 修改成功
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

    public StopOrNotResponse() {
    }

    protected StopOrNotResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<StopOrNotResponse> CREATOR = new Parcelable.Creator<StopOrNotResponse>() {
        @Override
        public StopOrNotResponse createFromParcel(Parcel source) {
            return new StopOrNotResponse(source);
        }

        @Override
        public StopOrNotResponse[] newArray(int size) {
            return new StopOrNotResponse[size];
        }
    };
}
