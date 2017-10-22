package com.fan.service.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/14.22:42
 */

public class WxPayResponse implements Parcelable {


    /**
     * name : service="mobile.securitypay.pay"&partner="2088621558884290"&_input_charset="UTF-8"&out_trade_no="20171008103552-399"&subject="掏掏红包"&payment_type="1"&seller_id="haoxiong2017@163.com"&total_fee="1"&body="掏掏红包"&it_b_pay="1d"&notify_url="http://hb.huidang2105.com:8900/public/index.php/alipayrp"&sign="ub2W6dOZkI1DnXeZCh1ggO9w61MgNuNx3HJ3UaEccBdrWA1HoBN9kLIL2OvYHH0hQEuFGb0m6eWGIK%2BOLd3kS8opPAC4msXPGkk6rkXWeMJeRc9ASVVXzenh5u64pUjPwQOvLGJ%2BGHJlVlXkCFU%2FUNYIHGw72PiFAt4Xw5APpsw%3D"&sign_type="RSA"
     */

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public WxPayResponse() {
    }

    protected WxPayResponse(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<WxPayResponse> CREATOR = new Parcelable.Creator<WxPayResponse>() {
        @Override
        public WxPayResponse createFromParcel(Parcel source) {
            return new WxPayResponse(source);
        }

        @Override
        public WxPayResponse[] newArray(int size) {
            return new WxPayResponse[size];
        }
    };
}
