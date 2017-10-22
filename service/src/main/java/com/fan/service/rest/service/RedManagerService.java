package com.fan.service.rest.service;

import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.RedManagerResponse;
import com.fan.service.response.StopOrNotResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface RedManagerService {
    /**
     * 红包管理接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("/public/index.php/red_isent")
    Observable<RedManagerResponse> balanceDetail(@Field("token") String token);

    /**
     * 红包的暂停与开始接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/red_set")
    Observable<StopOrNotResponse> stopOrNot(@Field("token") String token, @Field("rid") int rid, @Field("status_user") boolean status_user);

}
