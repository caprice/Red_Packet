package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/6.21:49
 */

public class MessageResponse implements Parcelable {

    /**
     * ret : 200
     * data : {"code":200,"msg":"加载成功","time":"2017-12-06 00:25:26","list":{"steTime":1,"list_lb":[{"wdxxNum":0,"xxlx":1,"fsrname":"Zz","ltbt":"如来神掌 10元一本","ltxx":"hello word","xxzt":"1","cjsj":"1512490847"},{"wdxxNum":0,"xxlx":1,"fsrname":"Zz","ltbt":"如来神掌 10元一本","ltxx":"123请问","xxzt":"1","cjsj":"1512310299"}]}}
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
         * time : 2017-12-06 00:25:26
         * list : {"steTime":1,"list_lb":[{"wdxxNum":0,"xxlx":1,"fsrname":"Zz","ltbt":"如来神掌 10元一本","ltxx":"hello word","xxzt":"1","cjsj":"1512490847"},{"wdxxNum":0,"xxlx":1,"fsrname":"Zz","ltbt":"如来神掌 10元一本","ltxx":"123请问","xxzt":"1","cjsj":"1512310299"}]}
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
             * list_lb : [{"wdxxNum":0,"xxlx":1,"fsrname":"Zz","ltbt":"如来神掌 10元一本","ltxx":"hello word","xxzt":"1","cjsj":"1512490847"},{"wdxxNum":0,"xxlx":1,"fsrname":"Zz","ltbt":"如来神掌 10元一本","ltxx":"123请问","xxzt":"1","cjsj":"1512310299"}]
             */

            private int steTime;
            private List<ListLbBean> list_lb;

            public int getSteTime() {
                return steTime;
            }

            public void setSteTime(int steTime) {
                this.steTime = steTime;
            }

            public List<ListLbBean> getList_lb() {
                return list_lb;
            }

            public void setList_lb(List<ListLbBean> list_lb) {
                this.list_lb = list_lb;
            }

            public static class ListLbBean implements Parcelable {
                /**
                 * wdxxNum : 0
                 * xxlx : 1
                 * fsrname : Zz
                 * ltbt : 如来神掌 10元一本
                 * ltxx : hello word
                 * xxzt : 1
                 * cjsj : 1512490847
                 * rid;1
                 */

                private int wdxxNum;
                private int xxlx;
                private String fsrname;
                private String ltbt;
                private String ltxx;
                private String xxzt;
                private String cjsj;
                private String rid;
                private String ltid;

                public String getLtid() {
                    return ltid;
                }

                public void setLtid(String ltid) {
                    this.ltid = ltid;
                }

                public String getRid() {
                    return rid;
                }

                public void setRid(String rid) {
                    this.rid = rid;
                }

                public int getWdxxNum() {
                    return wdxxNum;
                }

                public void setWdxxNum(int wdxxNum) {
                    this.wdxxNum = wdxxNum;
                }

                public int getXxlx() {
                    return xxlx;
                }

                public void setXxlx(int xxlx) {
                    this.xxlx = xxlx;
                }

                public String getFsrname() {
                    return fsrname;
                }

                public void setFsrname(String fsrname) {
                    this.fsrname = fsrname;
                }

                public String getLtbt() {
                    return ltbt;
                }

                public void setLtbt(String ltbt) {
                    this.ltbt = ltbt;
                }

                public String getLtxx() {
                    return ltxx;
                }

                public void setLtxx(String ltxx) {
                    this.ltxx = ltxx;
                }

                public String getXxzt() {
                    return xxzt;
                }

                public void setXxzt(String xxzt) {
                    this.xxzt = xxzt;
                }

                public String getCjsj() {
                    return cjsj;
                }

                public void setCjsj(String cjsj) {
                    this.cjsj = cjsj;
                }

                public ListLbBean() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.wdxxNum);
                    dest.writeInt(this.xxlx);
                    dest.writeString(this.fsrname);
                    dest.writeString(this.ltbt);
                    dest.writeString(this.ltxx);
                    dest.writeString(this.xxzt);
                    dest.writeString(this.cjsj);
                    dest.writeString(this.rid);
                    dest.writeString(this.ltid);
                }

                protected ListLbBean(Parcel in) {
                    this.wdxxNum = in.readInt();
                    this.xxlx = in.readInt();
                    this.fsrname = in.readString();
                    this.ltbt = in.readString();
                    this.ltxx = in.readString();
                    this.xxzt = in.readString();
                    this.cjsj = in.readString();
                    this.rid = in.readString();
                    this.ltid = in.readString();
                }

                public static final Creator<ListLbBean> CREATOR = new Creator<ListLbBean>() {
                    @Override
                    public ListLbBean createFromParcel(Parcel source) {
                        return new ListLbBean(source);
                    }

                    @Override
                    public ListLbBean[] newArray(int size) {
                        return new ListLbBean[size];
                    }
                };
            }

            public ListBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.steTime);
                dest.writeTypedList(this.list_lb);
            }

            protected ListBean(Parcel in) {
                this.steTime = in.readInt();
                this.list_lb = in.createTypedArrayList(ListLbBean.CREATOR);
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

        public DataBean() {
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

        protected DataBean(Parcel in) {
            this.code = in.readInt();
            this.msg = in.readString();
            this.time = in.readString();
            this.list = in.readParcelable(ListBean.class.getClassLoader());
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

    public MessageResponse() {
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

    protected MessageResponse(Parcel in) {
        this.ret = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.msg = in.readString();
    }

    public static final Creator<MessageResponse> CREATOR = new Creator<MessageResponse>() {
        @Override
        public MessageResponse createFromParcel(Parcel source) {
            return new MessageResponse(source);
        }

        @Override
        public MessageResponse[] newArray(int size) {
            return new MessageResponse[size];
        }
    };
}
