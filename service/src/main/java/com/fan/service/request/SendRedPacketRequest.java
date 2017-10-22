package com.fan.service.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/7.20:59
 */

public class SendRedPacketRequest implements Parcelable {
    private String token;
    private int money;
    private int pcount;
    private int type;
    private int distance;
    private String merchant;
    private String merchant_des;
    private String question;
    private String first_answer;
    private String second_answer;
    private String third_answer;
    private String tel;
    private String lng;
    private String lat;
    private String pic1_filecode;
    private String address;
    private String filecode;
    private int rid;

    public SendRedPacketRequest() {
    }

    public SendRedPacketRequest(String token, int money, int pcount, int type, int distance, String merchant, String merchant_des, String question, String first_answer, String second_answer, String third_answer, String tel, String lng, String lat, String pic1_filecode, String address, int rid) {
        this.token = token;
        this.money = money;
        this.pcount = pcount;
        this.type = type;
        this.distance = distance;
        this.merchant = merchant;
        this.merchant_des = merchant_des;
        this.question = question;
        this.first_answer = first_answer;
        this.second_answer = second_answer;
        this.third_answer = third_answer;
        this.tel = tel;
        this.lng = lng;
        this.lat = lat;
        this.pic1_filecode = pic1_filecode;
        this.address = address;
        this.rid = rid;
    }

    public String getFilecode() {
        return filecode;
    }

    public void setFilecode(String filecode) {
        this.filecode = filecode;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPcount() {
        return pcount;
    }

    public void setPcount(int pcount) {
        this.pcount = pcount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirst_answer() {
        return first_answer;
    }

    public void setFirst_answer(String first_answer) {
        this.first_answer = first_answer;
    }

    public String getSecond_answer() {
        return second_answer;
    }

    public void setSecond_answer(String second_answer) {
        this.second_answer = second_answer;
    }

    public String getThird_answer() {
        return third_answer;
    }

    public void setThird_answer(String third_answer) {
        this.third_answer = third_answer;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPic1_filecode() {
        return pic1_filecode;
    }

    public void setPic1_filecode(String pic1_filecode) {
        this.pic1_filecode = pic1_filecode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeInt(this.money);
        dest.writeInt(this.pcount);
        dest.writeInt(this.type);
        dest.writeInt(this.distance);
        dest.writeString(this.merchant);
        dest.writeString(this.merchant_des);
        dest.writeString(this.question);
        dest.writeString(this.first_answer);
        dest.writeString(this.second_answer);
        dest.writeString(this.third_answer);
        dest.writeString(this.tel);
        dest.writeString(this.lng);
        dest.writeString(this.lat);
        dest.writeString(this.pic1_filecode);
        dest.writeString(this.address);
        dest.writeString(this.filecode);
        dest.writeInt(this.rid);
    }

    protected SendRedPacketRequest(Parcel in) {
        this.token = in.readString();
        this.money = in.readInt();
        this.pcount = in.readInt();
        this.type = in.readInt();
        this.distance = in.readInt();
        this.merchant = in.readString();
        this.merchant_des = in.readString();
        this.question = in.readString();
        this.first_answer = in.readString();
        this.second_answer = in.readString();
        this.third_answer = in.readString();
        this.tel = in.readString();
        this.lng = in.readString();
        this.lat = in.readString();
        this.pic1_filecode = in.readString();
        this.address = in.readString();
        this.filecode = in.readString();
        this.rid = in.readInt();
    }

    public static final Creator<SendRedPacketRequest> CREATOR = new Creator<SendRedPacketRequest>() {
        @Override
        public SendRedPacketRequest createFromParcel(Parcel source) {
            return new SendRedPacketRequest(source);
        }

        @Override
        public SendRedPacketRequest[] newArray(int size) {
            return new SendRedPacketRequest[size];
        }
    };
}
