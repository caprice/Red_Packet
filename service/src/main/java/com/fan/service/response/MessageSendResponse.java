package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/6.22:35
 */

public class MessageSendResponse implements Parcelable {

    /**
     * ret : 200
     * data : {"code":200,"msg":"消息发送成功","time":"2017-12-06 22:38:57","list":1}
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

    public static class DataBean implements Parcelable {
        /**
         * code : 200
         * msg : 消息发送成功
         * time : 2017-12-06 22:38:57
         * list : 1
         */

        private int code;
        private String msg;
        private String time;
        private int list;

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

        public int getList() {
            return list;
        }

        public void setList(int list) {
            this.list = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeString(this.msg);
            dest.writeString(this.time);
            dest.writeInt(this.list);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.code = in.readInt();
            this.msg = in.readString();
            this.time = in.readString();
            this.list = in.readInt();
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
        dest.writeInt(this.ret);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.msg);
    }

    public MessageSendResponse() {
    }

    protected MessageSendResponse(Parcel in) {
        this.ret = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.msg = in.readString();
    }

    public static final Parcelable.Creator<MessageSendResponse> CREATOR = new Parcelable.Creator<MessageSendResponse>() {
        @Override
        public MessageSendResponse createFromParcel(Parcel source) {
            return new MessageSendResponse(source);
        }

        @Override
        public MessageSendResponse[] newArray(int size) {
            return new MessageSendResponse[size];
        }
    };
}
