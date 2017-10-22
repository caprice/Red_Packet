package com.fan.service.request;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.20:40
 */

public class ChangeUserHeadImgRequest implements Parcelable {
   private String token;
    private  File file;

    public ChangeUserHeadImgRequest(String token, File file) {
        this.token = token;
        this.file = file;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeSerializable(this.file);
    }

    protected ChangeUserHeadImgRequest(Parcel in) {
        this.token = in.readString();
        this.file = (File) in.readSerializable();
    }

    public static final Parcelable.Creator<ChangeUserHeadImgRequest> CREATOR = new Parcelable.Creator<ChangeUserHeadImgRequest>() {
        @Override
        public ChangeUserHeadImgRequest createFromParcel(Parcel source) {
            return new ChangeUserHeadImgRequest(source);
        }

        @Override
        public ChangeUserHeadImgRequest[] newArray(int size) {
            return new ChangeUserHeadImgRequest[size];
        }
    };
}
