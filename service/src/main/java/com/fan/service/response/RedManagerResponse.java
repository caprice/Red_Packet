package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.22:59
 */

public class RedManagerResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : {"reds_on":[{"rid":79,"merchant":"拮据","money":1,"status":0,"pCount":10,"remainCount":10,"time":"17-09-02","status_user":true,"pic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170902114432pL3ox.jpeg"},{"rid":55,"merchant":"我是王滨测试","money":10,"status":0,"pCount":10,"remainCount":10,"time":"17-09-01","status_user":true,"pic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170824150006BHctQ.jpeg"},{"rid":54,"merchant":"22222222222222222222","money":10,"status":1,"pCount":10,"remainCount":9,"time":"17-09-01","status_user":true,"pic":"http://hb.huidang2105.com:8900/public/uploads/default/user.png"}],"reds_off":[{"rid":79,"merchant":"拮据","money":1,"status":0,"pCount":10,"remainCount":10,"time":"17-09-02","status_user":true,"pic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170902114432pL3ox.jpeg"},{"rid":55,"merchant":"我是王滨测试","money":10,"status":0,"pCount":10,"remainCount":10,"time":"17-09-01","status_user":true,"pic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170824150006BHctQ.jpeg"},{"rid":54,"merchant":"22222222222222222222","money":10,"status":1,"pCount":10,"remainCount":9,"time":"17-09-01","status_user":true,"pic":"http://hb.huidang2105.com:8900/public/uploads/default/user.png"}]}
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
        private List<RedsOnBean> reds_on;
        private List<RedsOffBean> reds_off;

        public List<RedsOnBean> getReds_on() {
            return reds_on;
        }

        public void setReds_on(List<RedsOnBean> reds_on) {
            this.reds_on = reds_on;
        }

        public List<RedsOffBean> getReds_off() {
            return reds_off;
        }

        public void setReds_off(List<RedsOffBean> reds_off) {
            this.reds_off = reds_off;
        }

        public static class RedsOnBean implements Parcelable {
            /**
             * rid : 79
             * merchant : 拮据
             * money : 1
             * status : 0
             * pCount : 10
             * remainCount : 10
             * time : 17-09-02
             * status_user : true
             * pic : http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170902114432pL3ox.jpeg
             */

            private int rid;
            private String merchant;
            private int money;
            private int status;
            private int pCount;
            private int remainCount;
            private String time;
            private boolean status_user;
            private String pic;

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPCount() {
                return pCount;
            }

            public void setPCount(int pCount) {
                this.pCount = pCount;
            }

            public int getRemainCount() {
                return remainCount;
            }

            public void setRemainCount(int remainCount) {
                this.remainCount = remainCount;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public boolean isStatus_user() {
                return status_user;
            }

            public void setStatus_user(boolean status_user) {
                this.status_user = status_user;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.rid);
                dest.writeString(this.merchant);
                dest.writeInt(this.money);
                dest.writeInt(this.status);
                dest.writeInt(this.pCount);
                dest.writeInt(this.remainCount);
                dest.writeString(this.time);
                dest.writeByte(this.status_user ? (byte) 1 : (byte) 0);
                dest.writeString(this.pic);
            }

            public RedsOnBean() {
            }

            protected RedsOnBean(Parcel in) {
                this.rid = in.readInt();
                this.merchant = in.readString();
                this.money = in.readInt();
                this.status = in.readInt();
                this.pCount = in.readInt();
                this.remainCount = in.readInt();
                this.time = in.readString();
                this.status_user = in.readByte() != 0;
                this.pic = in.readString();
            }

            public static final Parcelable.Creator<RedsOnBean> CREATOR = new Parcelable.Creator<RedsOnBean>() {
                @Override
                public RedsOnBean createFromParcel(Parcel source) {
                    return new RedsOnBean(source);
                }

                @Override
                public RedsOnBean[] newArray(int size) {
                    return new RedsOnBean[size];
                }
            };
        }

        public static class RedsOffBean implements Parcelable {
            /**
             * rid : 79
             * merchant : 拮据
             * money : 1
             * status : 0
             * pCount : 10
             * remainCount : 10
             * time : 17-09-02
             * status_user : true
             * pic : http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170902114432pL3ox.jpeg
             */

            private int rid;
            private String merchant;
            private int money;
            private int status;
            private int pCount;
            private int remainCount;
            private String time;
            private boolean status_user;
            private String pic;

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPCount() {
                return pCount;
            }

            public void setPCount(int pCount) {
                this.pCount = pCount;
            }

            public int getRemainCount() {
                return remainCount;
            }

            public void setRemainCount(int remainCount) {
                this.remainCount = remainCount;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public boolean isStatus_user() {
                return status_user;
            }

            public void setStatus_user(boolean status_user) {
                this.status_user = status_user;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.rid);
                dest.writeString(this.merchant);
                dest.writeInt(this.money);
                dest.writeInt(this.status);
                dest.writeInt(this.pCount);
                dest.writeInt(this.remainCount);
                dest.writeString(this.time);
                dest.writeByte(this.status_user ? (byte) 1 : (byte) 0);
                dest.writeString(this.pic);
            }

            public RedsOffBean() {
            }

            protected RedsOffBean(Parcel in) {
                this.rid = in.readInt();
                this.merchant = in.readString();
                this.money = in.readInt();
                this.status = in.readInt();
                this.pCount = in.readInt();
                this.remainCount = in.readInt();
                this.time = in.readString();
                this.status_user = in.readByte() != 0;
                this.pic = in.readString();
            }

            public static final Parcelable.Creator<RedsOffBean> CREATOR = new Parcelable.Creator<RedsOffBean>() {
                @Override
                public RedsOffBean createFromParcel(Parcel source) {
                    return new RedsOffBean(source);
                }

                @Override
                public RedsOffBean[] newArray(int size) {
                    return new RedsOffBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.reds_on);
            dest.writeTypedList(this.reds_off);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.reds_on = in.createTypedArrayList(RedsOnBean.CREATOR);
            this.reds_off = in.createTypedArrayList(RedsOffBean.CREATOR);
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

    public RedManagerResponse() {
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

    protected RedManagerResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<RedManagerResponse> CREATOR = new Creator<RedManagerResponse>() {
        @Override
        public RedManagerResponse createFromParcel(Parcel source) {
            return new RedManagerResponse(source);
        }

        @Override
        public RedManagerResponse[] newArray(int size) {
            return new RedManagerResponse[size];
        }
    };
}
