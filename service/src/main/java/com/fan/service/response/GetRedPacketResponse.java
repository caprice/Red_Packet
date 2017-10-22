package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/12.21:54
 */

public class GetRedPacketResponse implements Parcelable {

    /**
     * err : 0
     * msg : 成功
     * data : {"money":"0.32","remainCount":15}
     */

    private int err;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * money : 0.32
         * remainCount : 15
         */

        private String money;
        private int remainCount;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getRemainCount() {
            return remainCount;
        }

        public void setRemainCount(int remainCount) {
            this.remainCount = remainCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.money);
            dest.writeInt(this.remainCount);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.money = in.readString();
            this.remainCount = in.readInt();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.err);
        dest.writeString(this.msg);
        dest.writeParcelable(this.data, flags);
    }

    public GetRedPacketResponse() {
    }

    protected GetRedPacketResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetRedPacketResponse> CREATOR = new Parcelable.Creator<GetRedPacketResponse>() {
        @Override
        public GetRedPacketResponse createFromParcel(Parcel source) {
            return new GetRedPacketResponse(source);
        }

        @Override
        public GetRedPacketResponse[] newArray(int size) {
            return new GetRedPacketResponse[size];
        }
    };
}
