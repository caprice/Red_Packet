package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/6.21:41
 */

public class UnreadResponse implements Parcelable {

    /**
     * ret : 200
     * data : {"code":200,"msg":"加载成功","time":"2017-12-06 00:29:29","list":{"steTime":1,"unread":0}}
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
         * msg : 加载成功
         * time : 2017-12-06 00:29:29
         * list : {"steTime":1,"unread":0}
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
             * steTime : 1
             * unread : 0
             */

            private int steTime;
            private int unread;

            public int getSteTime() {
                return steTime;
            }

            public void setSteTime(int steTime) {
                this.steTime = steTime;
            }

            public int getUnread() {
                return unread;
            }

            public void setUnread(int unread) {
                this.unread = unread;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.steTime);
                dest.writeInt(this.unread);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.steTime = in.readInt();
                this.unread = in.readInt();
            }

            public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeString(this.msg);
            dest.writeString(this.time);
            dest.writeParcelable(this.list, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.code = in.readInt();
            this.msg = in.readString();
            this.time = in.readString();
            this.list = in.readParcelable(ListBean.class.getClassLoader());
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

    public UnreadResponse() {
    }

    protected UnreadResponse(Parcel in) {
        this.ret = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.msg = in.readString();
    }

    public static final Parcelable.Creator<UnreadResponse> CREATOR = new Parcelable.Creator<UnreadResponse>() {
        @Override
        public UnreadResponse createFromParcel(Parcel source) {
            return new UnreadResponse(source);
        }

        @Override
        public UnreadResponse[] newArray(int size) {
            return new UnreadResponse[size];
        }
    };
}
