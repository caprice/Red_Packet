package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.11:22
 */

public class ChangePersonImgResponse implements Parcelable {

    /**
     * err : 0
     * msg : 上传成功
     * filecode : 7vTjgKe3qhFwaUoKJXhS
     * preview_url : http://hb.huidang2105.com:8900/public/uploads/user_pic/1/201709032108182NsqR.jpg
     */

    private int err;
    private String msg;
    private String filecode;
    private String preview_url;

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

    public String getFilecode() {
        return filecode;
    }

    public void setFilecode(String filecode) {
        this.filecode = filecode;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.err);
        dest.writeString(this.msg);
        dest.writeString(this.filecode);
        dest.writeString(this.preview_url);
    }

    public ChangePersonImgResponse() {
    }

    protected ChangePersonImgResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.filecode = in.readString();
        this.preview_url = in.readString();
    }

    public static final Parcelable.Creator<ChangePersonImgResponse> CREATOR = new Parcelable.Creator<ChangePersonImgResponse>() {
        @Override
        public ChangePersonImgResponse createFromParcel(Parcel source) {
            return new ChangePersonImgResponse(source);
        }

        @Override
        public ChangePersonImgResponse[] newArray(int size) {
            return new ChangePersonImgResponse[size];
        }
    };
}
