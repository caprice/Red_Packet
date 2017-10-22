package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/9.22:09
 */

public class RedPacketListResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : [{"rid":80,"merchant":"驾了么红包","money":1,"pCount":10,"remainCount":10,"mer_pics":"uploads/user_pic/6/20170903134705HjSfZ.jpeg","realdistance":1031,"img":"http://hb.huidang2105.com:8900/public/uploads/user_pic/6/20170903134705HjSfZ.jpeg","got":2},{"rid":35,"merchant":"爱你哦","money":1,"pCount":10,"remainCount":10,"mer_pics":"uploads/user_pic/4/20170901103833SSncj.jpeg","realdistance":0,"img":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","got":2},{"rid":32,"merchant":"请选择哈哈","money":1,"pCount":10,"remainCount":10,"mer_pics":"uploads/user_pic/4/20170901103833SSncj.jpeg","realdistance":0,"img":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","got":2},{"rid":70,"merchant":"城","money":1,"pCount":10,"remainCount":9,"mer_pics":"uploads/user_pic/6/20170902004426s7eDc.jpeg","realdistance":null,"img":"http://hb.huidang2105.com:8900/public/uploads/user_pic/6/20170902004426s7eDc.jpeg","got":2},{"rid":30,"merchant":"10","money":10,"pCount":10,"remainCount":9,"mer_pics":"uploads/user_pic/4/20170901103833SSncj.jpeg","realdistance":0,"img":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","got":2},{"rid":65,"merchant":"间谍活动基地","money":20,"pCount":20,"remainCount":15,"mer_pics":"uploads/user_pic/3/20170901235839xN3bR.jpeg","realdistance":145,"img":"http://hb.huidang2105.com:8900/public/uploads/user_pic/3/20170901235839xN3bR.jpeg","got":1},{"rid":49,"merchant":"10","money":1,"pCount":10,"remainCount":10,"mer_pics":"uploads/user_pic/4/20170901170631Y1pdz.jpeg","realdistance":null,"img":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","got":1},{"rid":34,"merchant":"你好好","money":1,"pCount":10,"remainCount":10,"mer_pics":"uploads/user_pic/4/20170901103833SSncj.jpeg","realdistance":0,"img":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","got":1},{"rid":33,"merchant":"是我","money":1,"pCount":10,"remainCount":10,"mer_pics":"uploads/user_pic/4/20170901103833SSncj.jpeg","realdistance":0,"img":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","got":1},{"rid":54,"merchant":"22222222222222222222","money":10,"pCount":10,"remainCount":9,"mer_pics":"","realdistance":null,"img":"http://hb.huidang2105.com:8900/public/uploads/default/user.png","got":1}]
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
         * rid : 80
         * merchant : 驾了么红包
         * money : 1
         * pCount : 10
         * remainCount : 10
         * mer_pics : uploads/user_pic/6/20170903134705HjSfZ.jpeg
         * realdistance : 1031
         * img : http://hb.huidang2105.com:8900/public/uploads/user_pic/6/20170903134705HjSfZ.jpeg
         * got : 2
         */

        private int rid;
        private String merchant;
        private int money;
        private int pCount;
        private int remainCount;
        private String mer_pics;
        private String realdistance;
        private String img;
        private int got;

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

        public String getMer_pics() {
            return mer_pics;
        }

        public void setMer_pics(String mer_pics) {
            this.mer_pics = mer_pics;
        }

        public String getRealdistance() {
            return realdistance;
        }

        public void setRealdistance(String realdistance) {
            this.realdistance = realdistance;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getGot() {
            return got;
        }

        public void setGot(int got) {
            this.got = got;
        }

        public DataBean() {
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
            dest.writeInt(this.pCount);
            dest.writeInt(this.remainCount);
            dest.writeString(this.mer_pics);
            dest.writeString(this.realdistance);
            dest.writeString(this.img);
            dest.writeInt(this.got);
        }

        protected DataBean(Parcel in) {
            this.rid = in.readInt();
            this.merchant = in.readString();
            this.money = in.readInt();
            this.pCount = in.readInt();
            this.remainCount = in.readInt();
            this.mer_pics = in.readString();
            this.realdistance = in.readString();
            this.img = in.readString();
            this.got = in.readInt();
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

    public RedPacketListResponse() {
    }

    protected RedPacketListResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<RedPacketListResponse> CREATOR = new Parcelable.Creator<RedPacketListResponse>() {
        @Override
        public RedPacketListResponse createFromParcel(Parcel source) {
            return new RedPacketListResponse(source);
        }

        @Override
        public RedPacketListResponse[] newArray(int size) {
            return new RedPacketListResponse[size];
        }
    };
}
