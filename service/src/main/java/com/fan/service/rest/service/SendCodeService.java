package com.fan.service.rest.service;

import com.fan.service.response.LoginResponse;
import com.fan.service.response.RegisterResponse;
import com.fan.service.response.SendMessageResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface SendCodeService {
    /**
     * 发送注册验证码
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/send_login_code")
    Observable<SendMessageResponse> sendRegisterCode(@Field("mobile") String mobile);

    /**
     * 登录接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/login")
    Observable<ResponseBody> login(@Field("mobile") String mobile, @Field("vecode") String vecode, @Field("device") String device);

    /**
     * 注册接口
     * mobile
     * xh checked 20170424
     * http://hb.huidang2105.com:8900/public/index.php/register?mobile=13699440001&vecode=554929&device=hlasxce
     *
     */
    @FormUrlEncoded
    @POST("public/index.php/register")
    Observable<ResponseBody> register(@Field("mobile") String mobile, @Field("inviter") String inviter);

    /**
     * 设置邀请码
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/setInviteCode")
    Observable<ResponseBody> setInviteCode(@Field("token ") String token, @Field("inviteCode") String inviteCode);
}
