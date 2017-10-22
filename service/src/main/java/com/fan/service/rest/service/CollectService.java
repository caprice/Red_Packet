package com.fan.service.rest.service;

import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.CollectRedPacketResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface CollectService {
    /**
     * 我的收藏列表
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/myCollectList")
    Observable<CollectRedPacketResponse> collectRedPacket(@Field("token") String token);

}
