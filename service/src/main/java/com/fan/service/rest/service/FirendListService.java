package com.fan.service.rest.service;

import com.fan.service.response.CollectRedPacketResponse;
import com.fan.service.response.FriendListResponse;
import com.fan.service.response.WithDrawResponse;

import java.math.BigDecimal;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface FirendListService {
    /**
     * 好友
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/inviterList")
    Observable<FriendListResponse> firendList(@Field("token") String token);
    /**
     * 提现申请接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/applyWithdraw")
    Observable<ResponseBody> applyWithdraw(@Field("token") String token, @Field("money") double money , @Field("type") int type , @Field("account") String account );
}
