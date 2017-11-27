package com.fan.service.rest.service;

import com.fan.service.response.ActiveResponse;
import com.fan.service.response.AlipayResponse;
import com.fan.service.response.Bonus1Response;
import com.fan.service.response.BonusResponse;
import com.fan.service.response.GetRedPacketResponse;
import com.fan.service.response.LoginResponse;
import com.fan.service.response.RedOwerResponse;
import com.fan.service.response.RedPacketDetailResponse;
import com.fan.service.response.RedPacketListResponse;
import com.fan.service.response.SaveResponse;
import com.fan.service.response.SendMessageResponse;
import com.fan.service.response.SendRedPacketResponse;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface RedPacketListService {
    /**
     * 红包列表页
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/red_list")
    Observable<ResponseBody> redPacketListLogin(@Field("token") String token,
                                                @Field("latitude") String latitude,
                                                @Field("longitude") String longitude,
                                                @Field("num") int num,
                                                @Field("page") int page,
                                                @Field("sort") int sort,
                                                @Field("filtrate") int filtrate
    );

    /**
     * 红包列表页
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/red_list_guest")
    Observable<ResponseBody> redPacketListUnLogin(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("num") int num,
            @Field("page") int page,
            @Field("sort") int sort,
            @Field("filtrate") int filtrate
    );

    /**
     * 红包列表页
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/red_detail_guest")
    Observable<ResponseBody> redPacketDetailUnLogin(
            @Field("rid") int rid
    );

    /**
     * 红包列表页
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/red_detail")
    Observable<ResponseBody> redPacketDetailLogin(
            @Field("token") String token,
            @Field("rid") int rid
    );

    /**
     * 收藏与取消收藏接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/collectRed")
    Observable<SaveResponse> saveOrNot(
            @Field("token") String token,
            @Field("rid") int rid,
            @Field("action") boolean action
    );

    /**
     * 抢红包接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/get_red")
    Observable<ResponseBody> getRedPacket(
            @Field("token") String token,
            @Field("rid") int rid,
            @Field("answer") int answer
    );

    /**
     * 发布红包接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/send_redpac")
    Observable<SendRedPacketResponse> sendLocalRedPacket(
            @Field("token") String token,
            @Field("money") int money,
            @Field("pcount") int pcount,
            @Field("type") int type,
            @Field("distance") int distance,
            @Field("merchant") String merchant,
            @Field("merchant_des") String merchant_des,
            @Field("question") String question,
            @Field("first_answer") String first_answer,
            @Field("second_answer") String second_answer,
            @Field("third_answer") String third_answer,
            @Field("address") String address,
            @Field("tel") String tel,
            @Field("lng") String lng,
            @Field("lat") String lat,
            @Field("pic1_filecode") String pic1_filecode
    );

    /**
     * 发布红包接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/send_redpac")
    Observable<SendRedPacketResponse> sendRedPacket(
            @Field("token") String token,
            @Field("money") int money,
            @Field("pcount") int pcount,
            @Field("type") int type,
            @Field("merchant") String merchant,
            @Field("merchant_des") String merchant_des,
            @Field("question") String question,
            @Field("first_answer") String first_answer,
            @Field("second_answer") String second_answer,
            @Field("third_answer") String third_answer,
            @Field("address") String address,
            @Field("tel") String tel,
            @Field("pic1_filecode") String pic1_filecode
    );

    /**
     * 支付宝支付
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/alipayrp")
    Observable<Object> alipayrp(
            @Field("token") String token

    );

    /**
     * 支付成功回调接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/get_pay_param")
    Observable<Object> get_pay_param(
            @Field("token") String token,
            @Field("chargetype") String chargetype,
            @Field("money") int money,
            @Field("rid") int rid

    );

    /**
     * 支付宝支付成功回调接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/alipayrp")
    Observable<AlipayResponse> get_alipay_param(
            @Field("token") String token
    );

    /**
     * 支付宝支付成功回调接口
     * mobile
     * xh checked 20170424
     * http://hb.huidang2105.com:8900/?token=hi0eNjpffQd9GgasfHeCbPFx3EILUyV7ACSR9eWc
     */
    @FormUrlEncoded
    @POST("public/index.php//myWallet")
    Observable<ResponseBody> get_my_wallet(
            @Field("token") String token
    );

    /**
     * 支付宝支付成功回调接口
     * mobile
     * xh checked 20170424
     * http://hb.huidang2105.com:8900/public/index.php/update_user_location?token=wwk0mT0MlSDssTcmrPkQYue9aBMArlDzeAsFd5Sa&latitude=554929&longitude=562
     */
    @FormUrlEncoded
    @POST("public/index.php/update_user_location")
    Observable<Object> update_user_location(
            @Field("token") String token,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    /**
     * 支付宝支付成功回调接口
     * mobile
     * xh checked 20170424
     * http://hb.huidang2105.com:8900/public/index.php/update_device?token=wwk0mT0MlSDssTcmrPkQYue9aBMArlDzeAsFd5Sa&device=554929
     */
    @FormUrlEncoded
    @POST("public/index.php/update_device")
    Observable<Object> update_device(
            @Field("token") String token,
            @Field("device") String device
    );

    /**
     * 支付宝支付成功回调接口
     * mobile
     * xh checked 20170424
     * http://hb.huidang2105.com:8900/public/index.php/update_device?token=wwk0mT0MlSDssTcmrPkQYue9aBMArlDzeAsFd5Sa&device=554929
     */
    @FormUrlEncoded
    @POST("public/index.php/get_activity_lb")
    Observable<ActiveResponse> getActive(
            @Field("token") String token
    );

    /**
     * 红包得主
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/redpostterList")
    Observable<RedOwerResponse> redpostterList(
            @Field("page") int page,
            @Field("num") int num,
            @Field("rid") int rid
    );

    /**
     * 用户可提现总金额和提现状态
     */
    @GET("http://hb.huidang2105.com:89/")
    Observable<BonusResponse> redRecord(
            @Query("service") String service,
            @Query("token") String token
    );

    /**
     * 用户点击提现成功后的返回状态
     */
    @FormUrlEncoded
    @POST("http://hb.huidang2105.com:89/")
    Observable<Bonus1Response> getMoney(
            @Field("service") String service,
            @Field("token") String token,
            @Field("account") String account
    );
}
