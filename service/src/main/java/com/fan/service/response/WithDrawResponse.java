package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/12.23:00
 */

public class WithDrawResponse implements Parcelable {

    /**
     * err : 0
     * msg : 提现申请己受理，请等待审核
     * data : {"money":"5","money_back":4}
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
         * money : 5
         * money_back : 4
         */

        private String money;
        private String money_back;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMoney_back() {
            return money_back;
        }

        public void setMoney_back(String money_back) {
            this.money_back = money_back;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.money);
            dest.writeString(this.money_back);
        }

        protected DataBean(Parcel in) {
            this.money = in.readString();
            this.money_back = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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

    public WithDrawResponse() {
    }

    protected WithDrawResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<WithDrawResponse> CREATOR = new Parcelable.Creator<WithDrawResponse>() {
        @Override
        public WithDrawResponse createFromParcel(Parcel source) {
            return new WithDrawResponse(source);
        }

        @Override
        public WithDrawResponse[] newArray(int size) {
            return new WithDrawResponse[size];
        }
    };
}
