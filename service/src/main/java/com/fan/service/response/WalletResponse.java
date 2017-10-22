package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/10/13.20:29
 */

public class WalletResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : {"balance":"212.50"}
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
         * balance : 212.50
         */

        private String balance;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.balance);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.balance = in.readString();
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

    public WalletResponse() {
    }

    protected WalletResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<WalletResponse> CREATOR = new Parcelable.Creator<WalletResponse>() {
        @Override
        public WalletResponse createFromParcel(Parcel source) {
            return new WalletResponse(source);
        }

        @Override
        public WalletResponse[] newArray(int size) {
            return new WalletResponse[size];
        }
    };
}
