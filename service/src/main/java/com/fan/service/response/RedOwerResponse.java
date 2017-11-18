package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/11/18.20:54
 */

public class RedOwerResponse implements Parcelable {

    /**
     * err : 0
     * msg : 成功
     * data : [{"userPic":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","userName":"我是被王滨邀请的","address":"暂无位置信息","money":"0.01","maxmoney":0,"getTime":"17/10/18 11:38:06"},{"userPic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20171024101921n6PzF.jpg","userName":"王滨","address":"暂无位置信息","money":"0.08","maxmoney":0,"getTime":"17/10/18 11:30:14"},{"userPic":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","userName":"****","address":"暂无位置信息","money":"0.07","maxmoney":0,"getTime":"17/10/17 21:11:14"},{"userPic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/6/20171014142657Z5YZp.jpg","userName":"白翔","address":"暂无位置信息","money":"0.09","maxmoney":0,"getTime":"17/10/17 18:26:28"},{"userPic":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","userName":"156****4494","address":"暂无位置信息","money":"0.14","maxmoney":0,"getTime":"17/10/16 20:47:48"}]
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
         * userPic : http://hb.huidang2105.com:8900/public/uploads/default/user.png
         * userName : 我是被王滨邀请的
         * address : 暂无位置信息
         * money : 0.01
         * maxmoney : 0
         * getTime : 17/10/18 11:38:06
         */

        private String userPic;
        private String userName;
        private String address;
        private String money;
        private String maxmoney;
        private String getTime;

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMaxmoney() {
            return maxmoney;
        }

        public void setMaxmoney(String maxmoney) {
            this.maxmoney = maxmoney;
        }

        public String getGetTime() {
            return getTime;
        }

        public void setGetTime(String getTime) {
            this.getTime = getTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userPic);
            dest.writeString(this.userName);
            dest.writeString(this.address);
            dest.writeString(this.money);
            dest.writeString(this.maxmoney);
            dest.writeString(this.getTime);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.userPic = in.readString();
            this.userName = in.readString();
            this.address = in.readString();
            this.money = in.readString();
            this.maxmoney = in.readString();
            this.getTime = in.readString();
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

    public RedOwerResponse() {
    }

    protected RedOwerResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<RedOwerResponse> CREATOR = new Parcelable.Creator<RedOwerResponse>() {
        @Override
        public RedOwerResponse createFromParcel(Parcel source) {
            return new RedOwerResponse(source);
        }

        @Override
        public RedOwerResponse[] newArray(int size) {
            return new RedOwerResponse[size];
        }
    };
}
