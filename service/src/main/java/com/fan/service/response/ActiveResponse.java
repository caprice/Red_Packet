package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/11/16.21:36
 */

public class ActiveResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : [{"id":1,"hdmc":"哈哈","hdtp":"--","hdurl":"s1sg","hdjl":"3434人","hdjltp":"股份","cyzt":2},{"id":2,"hdmc":"哈哈1","hdtp":"--","hdurl":"s1sg","hdjl":"3434人","hdjltp":"股份","cyzt":1},{"id":3,"hdmc":"哈哈2","hdtp":"--","hdurl":"s1sg","hdjl":"3434人","hdjltp":"股份","cyzt":1}]
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
         * id : 1
         * hdmc : 哈哈
         * hdtp : --
         * hdurl : s1sg
         * hdjl : 3434人
         * hdjltp : 股份
         * cyzt : 2
         */

        private int id;
        private String hdmc;
        private String hdtp;
        private String hdurl;
        private String hdjl;
        private String hdjltp;
        private int cyzt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHdmc() {
            return hdmc;
        }

        public void setHdmc(String hdmc) {
            this.hdmc = hdmc;
        }

        public String getHdtp() {
            return hdtp;
        }

        public void setHdtp(String hdtp) {
            this.hdtp = hdtp;
        }

        public String getHdurl() {
            return hdurl;
        }

        public void setHdurl(String hdurl) {
            this.hdurl = hdurl;
        }

        public String getHdjl() {
            return hdjl;
        }

        public void setHdjl(String hdjl) {
            this.hdjl = hdjl;
        }

        public String getHdjltp() {
            return hdjltp;
        }

        public void setHdjltp(String hdjltp) {
            this.hdjltp = hdjltp;
        }

        public int getCyzt() {
            return cyzt;
        }

        public void setCyzt(int cyzt) {
            this.cyzt = cyzt;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.hdmc);
            dest.writeString(this.hdtp);
            dest.writeString(this.hdurl);
            dest.writeString(this.hdjl);
            dest.writeString(this.hdjltp);
            dest.writeInt(this.cyzt);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.hdmc = in.readString();
            this.hdtp = in.readString();
            this.hdurl = in.readString();
            this.hdjl = in.readString();
            this.hdjltp = in.readString();
            this.cyzt = in.readInt();
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

    public ActiveResponse() {
    }

    protected ActiveResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<ActiveResponse> CREATOR = new Parcelable.Creator<ActiveResponse>() {
        @Override
        public ActiveResponse createFromParcel(Parcel source) {
            return new ActiveResponse(source);
        }

        @Override
        public ActiveResponse[] newArray(int size) {
            return new ActiveResponse[size];
        }
    };
}
