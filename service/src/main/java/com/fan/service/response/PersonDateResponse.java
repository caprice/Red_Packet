package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.10:49
 */

public class PersonDateResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : {"userinfo":{"inviter_count":0,"colect_count":0,"balance":"13.75","username":"天空之城","birthday":"2017年08月24日","gender":"男","inviteCode":3289523,"userPic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170824145803sBhJb.jpeg","packetIn":42,"packetOut":0,"balance_dot":false,"invite_dot":false}}
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
         * userinfo : {"inviter_count":0,"colect_count":0,"balance":"13.75","username":"天空之城","birthday":"2017年08月24日","gender":"男","inviteCode":3289523,"userPic":"http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170824145803sBhJb.jpeg","packetIn":42,"packetOut":0,"balance_dot":false,"invite_dot":false}
         */

        private UserinfoBean userinfo;

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public static class UserinfoBean implements Parcelable {
            /**
             * inviter_count : 0
             * colect_count : 0
             * balance : 13.75
             * username : 天空之城
             * birthday : 2017年08月24日
             * gender : 男
             * inviteCode : 3289523
             * userPic : http://hb.huidang2105.com:8900/public/uploads/user_pic/1/20170824145803sBhJb.jpeg
             * packetIn : 42
             * packetOut : 0
             * balance_dot : false
             * invite_dot : false
             */

            private int inviter_count;
            private int colect_count;
            private String balance;
            private String username;
            private String birthday;
            private String gender;
            private String inviteCode;
            private String userPic;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            private String mobile;
            private int packetIn;
            private int packetOut;
            private boolean balance_dot;
            private boolean invite_dot;

            public int getInviter_count() {
                return inviter_count;
            }

            public void setInviter_count(int inviter_count) {
                this.inviter_count = inviter_count;
            }

            public int getColect_count() {
                return colect_count;
            }

            public void setColect_count(int colect_count) {
                this.colect_count = colect_count;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
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

            public int getPacketOut() {
                return packetOut;
            }

            public void setPacketOut(int packetOut) {
                this.packetOut = packetOut;
            }

            public boolean isBalance_dot() {
                return balance_dot;
            }

            public void setBalance_dot(boolean balance_dot) {
                this.balance_dot = balance_dot;
            }

            public boolean isInvite_dot() {
                return invite_dot;
            }

            public void setInvite_dot(boolean invite_dot) {
                this.invite_dot = invite_dot;
            }

            public UserinfoBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.inviter_count);
                dest.writeInt(this.colect_count);
                dest.writeString(this.balance);
                dest.writeString(this.username);
                dest.writeString(this.birthday);
                dest.writeString(this.gender);
                dest.writeString(this.inviteCode);
                dest.writeString(this.userPic);
                dest.writeString(this.mobile);
                dest.writeInt(this.packetIn);
                dest.writeInt(this.packetOut);
                dest.writeByte(this.balance_dot ? (byte) 1 : (byte) 0);
                dest.writeByte(this.invite_dot ? (byte) 1 : (byte) 0);
            }

            protected UserinfoBean(Parcel in) {
                this.inviter_count = in.readInt();
                this.colect_count = in.readInt();
                this.balance = in.readString();
                this.username = in.readString();
                this.birthday = in.readString();
                this.gender = in.readString();
                this.inviteCode = in.readString();
                this.userPic = in.readString();
                this.mobile = in.readString();
                this.packetIn = in.readInt();
                this.packetOut = in.readInt();
                this.balance_dot = in.readByte() != 0;
                this.invite_dot = in.readByte() != 0;
            }

            public static final Creator<UserinfoBean> CREATOR = new Creator<UserinfoBean>() {
                @Override
                public UserinfoBean createFromParcel(Parcel source) {
                    return new UserinfoBean(source);
                }

                @Override
                public UserinfoBean[] newArray(int size) {
                    return new UserinfoBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.userinfo, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.userinfo = in.readParcelable(UserinfoBean.class.getClassLoader());
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

    public PersonDateResponse() {
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

    protected PersonDateResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<PersonDateResponse> CREATOR = new Creator<PersonDateResponse>() {
        @Override
        public PersonDateResponse createFromParcel(Parcel source) {
            return new PersonDateResponse(source);
        }

        @Override
        public PersonDateResponse[] newArray(int size) {
            return new PersonDateResponse[size];
        }
    };
}
