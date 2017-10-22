package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/10.22:11
 */

public class BalanceDetailResponse implements Parcelable {

    /**
     * err : 0
     * msg : 正常
     * data : {"records":[{"month":"本月","record":[{"id":578,"type":4,"money":"+5.00","time":"17-09-06 18:03:34","title":"提现"},{"id":576,"type":4,"money":"+5.00","time":"17-09-06 17:47:45","title":"提现"},{"id":575,"type":4,"money":"+5.00","time":"17-09-06 17:46:59","title":"提现"},{"id":574,"type":4,"money":"+5.00","time":"17-09-06 17:42:32","title":"提现"},{"id":573,"type":4,"money":"-4.00","time":"17-09-06 17:26:35","title":"提现"},{"id":572,"type":1,"money":"+100.00","time":"17-09-05 16:23:05","title":"领到发布的一个红包"},{"id":560,"type":1,"money":"+0.14","time":"17-09-05 16:23:05","title":"领到发布的一个红包"},{"id":559,"type":1,"money":"+0.04","time":"17-09-04 21:35:07","title":"领到发布的一个红包"},{"id":558,"type":1,"money":"+0.18","time":"17-09-04 21:28:47","title":"领到发布的一个红包"},{"id":557,"type":1,"money":"+0.13","time":"17-09-04 21:22:24","title":"领到发布的一个红包"},{"id":556,"type":1,"money":"+0.11","time":"17-09-04 21:17:26","title":"领到发布的一个红包"},{"id":555,"type":2,"money":"+0.11","time":"17-09-04 21:10:30","title":"发布红包"},{"id":554,"type":"其他","money":"+0.11","time":"17-09-04 21:10:30"},{"id":553,"type":1,"money":"+0.11","time":"17-09-04 21:10:30","title":"领到发布的一个红包"},{"id":549,"type":1,"money":"+0.19","time":"17-09-04 20:30:56","title":"领到发布的一个红包"},{"id":548,"type":1,"money":"+1.22","time":"17-09-04 20:29:29","title":"领到发布的一个红包"},{"id":546,"type":1,"money":"+0.11","time":"17-09-04 20:16:45","title":"领到发布的一个红包"}]}],"balance":86.01}
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
         * records : [{"month":"本月","record":[{"id":578,"type":4,"money":"+5.00","time":"17-09-06 18:03:34","title":"提现"},{"id":576,"type":4,"money":"+5.00","time":"17-09-06 17:47:45","title":"提现"},{"id":575,"type":4,"money":"+5.00","time":"17-09-06 17:46:59","title":"提现"},{"id":574,"type":4,"money":"+5.00","time":"17-09-06 17:42:32","title":"提现"},{"id":573,"type":4,"money":"-4.00","time":"17-09-06 17:26:35","title":"提现"},{"id":572,"type":1,"money":"+100.00","time":"17-09-05 16:23:05","title":"领到发布的一个红包"},{"id":560,"type":1,"money":"+0.14","time":"17-09-05 16:23:05","title":"领到发布的一个红包"},{"id":559,"type":1,"money":"+0.04","time":"17-09-04 21:35:07","title":"领到发布的一个红包"},{"id":558,"type":1,"money":"+0.18","time":"17-09-04 21:28:47","title":"领到发布的一个红包"},{"id":557,"type":1,"money":"+0.13","time":"17-09-04 21:22:24","title":"领到发布的一个红包"},{"id":556,"type":1,"money":"+0.11","time":"17-09-04 21:17:26","title":"领到发布的一个红包"},{"id":555,"type":2,"money":"+0.11","time":"17-09-04 21:10:30","title":"发布红包"},{"id":554,"type":"其他","money":"+0.11","time":"17-09-04 21:10:30"},{"id":553,"type":1,"money":"+0.11","time":"17-09-04 21:10:30","title":"领到发布的一个红包"},{"id":549,"type":1,"money":"+0.19","time":"17-09-04 20:30:56","title":"领到发布的一个红包"},{"id":548,"type":1,"money":"+1.22","time":"17-09-04 20:29:29","title":"领到发布的一个红包"},{"id":546,"type":1,"money":"+0.11","time":"17-09-04 20:16:45","title":"领到发布的一个红包"}]}]
         * balance : 86.01
         */

        private double balance;
        private List<RecordsBean> records;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean implements Parcelable {
            /**
             * month : 本月
             * record : [{"id":578,"type":4,"money":"+5.00","time":"17-09-06 18:03:34","title":"提现"},{"id":576,"type":4,"money":"+5.00","time":"17-09-06 17:47:45","title":"提现"},{"id":575,"type":4,"money":"+5.00","time":"17-09-06 17:46:59","title":"提现"},{"id":574,"type":4,"money":"+5.00","time":"17-09-06 17:42:32","title":"提现"},{"id":573,"type":4,"money":"-4.00","time":"17-09-06 17:26:35","title":"提现"},{"id":572,"type":1,"money":"+100.00","time":"17-09-05 16:23:05","title":"领到发布的一个红包"},{"id":560,"type":1,"money":"+0.14","time":"17-09-05 16:23:05","title":"领到发布的一个红包"},{"id":559,"type":1,"money":"+0.04","time":"17-09-04 21:35:07","title":"领到发布的一个红包"},{"id":558,"type":1,"money":"+0.18","time":"17-09-04 21:28:47","title":"领到发布的一个红包"},{"id":557,"type":1,"money":"+0.13","time":"17-09-04 21:22:24","title":"领到发布的一个红包"},{"id":556,"type":1,"money":"+0.11","time":"17-09-04 21:17:26","title":"领到发布的一个红包"},{"id":555,"type":2,"money":"+0.11","time":"17-09-04 21:10:30","title":"发布红包"},{"id":554,"type":"其他","money":"+0.11","time":"17-09-04 21:10:30"},{"id":553,"type":1,"money":"+0.11","time":"17-09-04 21:10:30","title":"领到发布的一个红包"},{"id":549,"type":1,"money":"+0.19","time":"17-09-04 20:30:56","title":"领到发布的一个红包"},{"id":548,"type":1,"money":"+1.22","time":"17-09-04 20:29:29","title":"领到发布的一个红包"},{"id":546,"type":1,"money":"+0.11","time":"17-09-04 20:16:45","title":"领到发布的一个红包"}]
             */

            private String month;
            private List<RecordBean> record;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public List<RecordBean> getRecord() {
                return record;
            }

            public void setRecord(List<RecordBean> record) {
                this.record = record;
            }

            public static class RecordBean implements Parcelable {
                /**
                 * id : 578
                 * type : 4
                 * money : +5.00
                 * time : 17-09-06 18:03:34
                 * title : 提现
                 */

                private int id;
                private String type;
                private String money;
                private String time;
                private String title;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public RecordBean() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.id);
                    dest.writeString(this.type);
                    dest.writeString(this.money);
                    dest.writeString(this.time);
                    dest.writeString(this.title);
                }

                protected RecordBean(Parcel in) {
                    this.id = in.readInt();
                    this.type = in.readString();
                    this.money = in.readString();
                    this.time = in.readString();
                    this.title = in.readString();
                }

                public static final Creator<RecordBean> CREATOR = new Creator<RecordBean>() {
                    @Override
                    public RecordBean createFromParcel(Parcel source) {
                        return new RecordBean(source);
                    }

                    @Override
                    public RecordBean[] newArray(int size) {
                        return new RecordBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.month);
                dest.writeTypedList(this.record);
            }

            public RecordsBean() {
            }

            protected RecordsBean(Parcel in) {
                this.month = in.readString();
                this.record = in.createTypedArrayList(RecordBean.CREATOR);
            }

            public static final Parcelable.Creator<RecordsBean> CREATOR = new Parcelable.Creator<RecordsBean>() {
                @Override
                public RecordsBean createFromParcel(Parcel source) {
                    return new RecordsBean(source);
                }

                @Override
                public RecordsBean[] newArray(int size) {
                    return new RecordsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.balance);
            dest.writeTypedList(this.records);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.balance = in.readDouble();
            this.records = in.createTypedArrayList(RecordsBean.CREATOR);
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
        dest.writeParcelable(this.data, flags);
    }

    public BalanceDetailResponse() {
    }

    protected BalanceDetailResponse(Parcel in) {
        this.err = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<BalanceDetailResponse> CREATOR = new Parcelable.Creator<BalanceDetailResponse>() {
        @Override
        public BalanceDetailResponse createFromParcel(Parcel source) {
            return new BalanceDetailResponse(source);
        }

        @Override
        public BalanceDetailResponse[] newArray(int size) {
            return new BalanceDetailResponse[size];
        }
    };
}
