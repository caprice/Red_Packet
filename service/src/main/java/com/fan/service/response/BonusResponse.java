package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/11/27.22:38
 */

public class BonusResponse {

    /**
     * ret : 200
     * data : {"code":200,"msg":"加载成功","time":"2017-11-27 22:42:09","list":{"sumMoney":10,"sfcz":1}}
     * msg :
     */

    private int ret;
    private DataBean data;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * code : 200
         * msg : 加载成功
         * time : 2017-11-27 22:42:09
         * list : {"sumMoney":10,"sfcz":1}
         */

        private int code;
        private String msg;
        private String time;
        private ListBean list;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            /**
             * sumMoney : 10
             * sfcz : 1
             */

            private String sumMoney;
            private int sfcz;

            public String getSumMoney() {
                return sumMoney;
            }

            public void setSumMoney(String sumMoney) {
                this.sumMoney = sumMoney;
            }

            public int getSfcz() {
                return sfcz;
            }

            public void setSfcz(int sfcz) {
                this.sfcz = sfcz;
            }

            public ListBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.sumMoney);
                dest.writeInt(this.sfcz);
            }

            protected ListBean(Parcel in) {
                this.sumMoney = in.readString();
                this.sfcz = in.readInt();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }
    }
}
