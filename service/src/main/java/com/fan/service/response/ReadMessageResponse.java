package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/6.22:44
 */

public class ReadMessageResponse implements Parcelable {

    /**
     * ret : 200
     * data : {"code":200,"msg":"加载成功","time":"2017-12-06 00:37:58","list":{"steTime":1,"merchant":"如来神掌 10元一本","merchant_des":"掏掏现面向全国征召各类人才，不论是CS、灌水还是气功，只要有一技之长我们都要，看好如来神掌的请速砸我邮箱","mer_pics":"uploads/user_pic/3/20171031102003gKROV.jpg","lt_list":[{"jlxx":"hello word","xxlx":1,"sj":"5分钟前: 00:32:02"},{"jlxx":"hello word","xxlx":1,"sj":"17分钟前: 00:20:47"}]}}
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
         * time : 2017-12-06 00:37:58
         * list : {"steTime":1,"merchant":"如来神掌 10元一本","merchant_des":"掏掏现面向全国征召各类人才，不论是CS、灌水还是气功，只要有一技之长我们都要，看好如来神掌的请速砸我邮箱","mer_pics":"uploads/user_pic/3/20171031102003gKROV.jpg","lt_list":[{"jlxx":"hello word","xxlx":1,"sj":"5分钟前: 00:32:02"},{"jlxx":"hello word","xxlx":1,"sj":"17分钟前: 00:20:47"}]}
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
             * merchant : 如来神掌 10元一本
             * merchant_des : 掏掏现面向全国征召各类人才，不论是CS、灌水还是气功，只要有一技之长我们都要，看好如来神掌的请速砸我邮箱
             * mer_pics : uploads/user_pic/3/20171031102003gKROV.jpg
             * lt_list : [{"jlxx":"hello word","xxlx":1,"sj":"5分钟前: 00:32:02"},{"jlxx":"hello word","xxlx":1,"sj":"17分钟前: 00:20:47"}]
             */

            private int steTime;
            private String merchant;
            private String merchant_des;
            private String mer_pics;
            private List<LtListBean> lt_list;

            public int getSteTime() {
                return steTime;
            }

            public void setSteTime(int steTime) {
                this.steTime = steTime;
            }

            public String getMerchant() {
                return merchant;
            }

            public void setMerchant(String merchant) {
                this.merchant = merchant;
            }

            public String getMerchant_des() {
                return merchant_des;
            }

            public void setMerchant_des(String merchant_des) {
                this.merchant_des = merchant_des;
            }

            public String getMer_pics() {
                return mer_pics;
            }

            public void setMer_pics(String mer_pics) {
                this.mer_pics = mer_pics;
            }

            public List<LtListBean> getLt_list() {
                return lt_list;
            }

            public void setLt_list(List<LtListBean> lt_list) {
                this.lt_list = lt_list;
            }

            public static class LtListBean implements Parcelable {
                /**
                 * jlxx : hello word
                 * xxlx : 1
                 * sj : 5分钟前: 00:32:02
                 */

                private String jlxx;
                private int xxlx;
                private String sj;
                private int id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getJlxx() {
                    return jlxx;
                }

                public void setJlxx(String jlxx) {
                    this.jlxx = jlxx;
                }

                public int getXxlx() {
                    return xxlx;
                }

                public void setXxlx(int xxlx) {
                    this.xxlx = xxlx;
                }

                public String getSj() {
                    return sj;
                }

                public void setSj(String sj) {
                    this.sj = sj;
                }

                public LtListBean() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.jlxx);
                    dest.writeInt(this.xxlx);
                    dest.writeString(this.sj);
                    dest.writeInt(this.id);
                }

                protected LtListBean(Parcel in) {
                    this.jlxx = in.readString();
                    this.xxlx = in.readInt();
                    this.sj = in.readString();
                    this.id = in.readInt();
                }

                public static final Creator<LtListBean> CREATOR = new Creator<LtListBean>() {
                    @Override
                    public LtListBean createFromParcel(Parcel source) {
                        return new LtListBean(source);
                    }

                    @Override
                    public LtListBean[] newArray(int size) {
                        return new LtListBean[size];
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
                dest.writeString(this.merchant);
                dest.writeString(this.merchant_des);
                dest.writeString(this.mer_pics);
                dest.writeTypedList(this.lt_list);
            }

            protected ListBean(Parcel in) {
                this.steTime = in.readInt();
                this.merchant = in.readString();
                this.merchant_des = in.readString();
                this.mer_pics = in.readString();
                this.lt_list = in.createTypedArrayList(LtListBean.CREATOR);
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

    public ReadMessageResponse() {
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

    protected ReadMessageResponse(Parcel in) {
        this.ret = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.msg = in.readString();
    }

    public static final Creator<ReadMessageResponse> CREATOR = new Creator<ReadMessageResponse>() {
        @Override
        public ReadMessageResponse createFromParcel(Parcel source) {
            return new ReadMessageResponse(source);
        }

        @Override
        public ReadMessageResponse[] newArray(int size) {
            return new ReadMessageResponse[size];
        }
    };
}
