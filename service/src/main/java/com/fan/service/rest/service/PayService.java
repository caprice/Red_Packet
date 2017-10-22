package com.fan.service.rest.service;

import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.PayResponse;
import com.fan.service.response.WxPayResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface PayService {
    /**
     * 支付接口(钱包支付)
     * wxpay微信alipay支付宝wallet钱包支付
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/walletCharge")
    Observable<PayResponse> pay(@Field("token") String token
            , @Field("money") int money
            , @Field("rid") int rid
            , @Field("chargetype") String chargetype

    );
    /**
     * 支付接口(钱包支付)
     * wxpay微信alipay支付宝wallet钱包支付
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/walletCharge")
    Observable<WxPayResponse> wxpay(@Field("token") String token
            , @Field("money") int money
            , @Field("rid") int rid
            , @Field("chargetype") String chargetype

    );
    /**
     * 支付接口(钱包支付)
     * wxpay微信alipay支付宝wallet钱包支付
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/walletCharge")
    Observable<WxPayResponse> alipay(@Field("token") String token
            , @Field("money") int money
            , @Field("rid") int rid
            , @Field("chargetype") String chargetype

    );
}
