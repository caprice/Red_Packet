package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/11.21:48
 */

public class CollectRedPacketResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : [{"rId":80,"merchant":"驾了么红包","money":1,"pCount":10,"user_pic":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","give_time":"2017-09-03"}]
     */

    private int err;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * rId : 80
         * merchant : 驾了么红包
         * money : 1
         * pCount : 10
         * user_pic : http://hb.huidang2105.com:8900/public/uploads/default/user.png
         * give_time : 2017-09-03
         */

        private int rId;
        private String merchant;
        private int money;
        private int pCount;
        private String user_pic;
        private String give_time;

        public int getRId() {
            return rId;
        }

        public void setRId(int rId) {
            this.rId = rId;
        }

        public String getMerchant() {
            return merchant;
        }

        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getPCount() {
            return pCount;
        }

        public void setPCount(int pCount) {
            this.pCount = pCount;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getGive_time() {
            return give_time;
        }

        public void setGive_time(String give_time) {
            this.give_time = give_time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.rId);
            dest.writeString(this.merchant);
            dest.writeInt(this.money);
            dest.writeInt(this.pCount);
            dest.writeString(this.user_pic);
            dest.writeString(this.give_time);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.rId = in.readInt();
            this.merchant = in.readString();
            this.money = in.readInt();
            this.pCount = in.readInt();
            this.user_pic = in.readString();
            this.give_time = in.readString();
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
        dest.writeList(this.data);
    }

    public CollectRedPacketResponse() {
    }

    protected CollectRedPacketResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CollectRedPacketResponse> CREATOR = new Parcelable.Creator<CollectRedPacketResponse>() {
        @Override
        public CollectRedPacketResponse createFromParcel(Parcel source) {
            return new CollectRedPacketResponse(source);
        }

        @Override
        public CollectRedPacketResponse[] newArray(int size) {
            return new CollectRedPacketResponse[size];
        }
    };
}
