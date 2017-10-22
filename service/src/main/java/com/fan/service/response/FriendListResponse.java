package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/12.22:45
 */

public class FriendListResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : [{"uid":1,"userPic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170906212847vwovG.jpeg","packetIn":59,"userName":"银进","moneyToMe":"0.00","sumMoney":"102.23"}]
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
         * uid : 1
         * userPic : http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170906212847vwovG.jpeg
         * packetIn : 59
         * userName : 银进
         * moneyToMe : 0.00
         * sumMoney : 102.23
         */

        private int uid;
        private String userPic;
        private int packetIn;
        private String userName;
        private String moneyToMe;
        private String sumMoney;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public int getPacketIn() {
            return packetIn;
        }

        public void setPacketIn(int packetIn) {
            this.packetIn = packetIn;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMoneyToMe() {
            return moneyToMe;
        }

        public void setMoneyToMe(String moneyToMe) {
            this.moneyToMe = moneyToMe;
        }

        public String getSumMoney() {
            return sumMoney;
        }

        public void setSumMoney(String sumMoney) {
            this.sumMoney = sumMoney;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.uid);
            dest.writeString(this.userPic);
            dest.writeInt(this.packetIn);
            dest.writeString(this.userName);
            dest.writeString(this.moneyToMe);
            dest.writeString(this.sumMoney);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.uid = in.readInt();
            this.userPic = in.readString();
            this.packetIn = in.readInt();
            this.userName = in.readString();
            this.moneyToMe = in.readString();
            this.sumMoney = in.readString();
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
        dest.writeTypedList(this.data);
    }

    public FriendListResponse() {
    }

    protected FriendListResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<FriendListResponse> CREATOR = new Parcelable.Creator<FriendListResponse>() {
        @Override
        public FriendListResponse createFromParcel(Parcel source) {
            return new FriendListResponse(source);
        }

        @Override
        public FriendListResponse[] newArray(int size) {
            return new FriendListResponse[size];
        }
    };
}
