package com.fan.service.rest.service;

import com.fan.service.request.ChangeUserHeadImgRequest;
import com.fan.service.response.ChangePersonDetaResponse;
import com.fan.service.response.ChangePersonImgResponse;
import com.fan.service.response.LoginResponse;
import com.fan.service.response.PersonDateResponse;
import com.fan.service.response.SendMessageResponse;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface PersonService {
    /**
     * 获取个人信息接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/profile")
    Observable<PersonDateResponse> getUserInfor(@Field("token") String token);

    /**
     * 修改个人信息接口
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/editprofile")
    Observable<ChangePersonDetaResponse> changeUserInfor(@Field("token") String token, @Field("username") String username, @Field("birthday") String birthday, @Field("gender") String gender);

    /**
     * 上传用户头像
     * mobile
     * xh checked 20170424
     */
    @Multipart
    @POST("/public/index.php/uploadUserPic")
    Observable<ChangePersonImgResponse> changeUserHeadImg(@Part("token")  RequestBody token,
                                                          @Part MultipartBody.Part part);

    /**
     * 上传商家图片
     * mobile
     * xh checked 20170424
     */
    @Multipart
    @POST("/public/index.php/uploadMerchantPic")
    Observable<ChangePersonImgResponse> uploadMerchantPic(@Part("token")  RequestBody token,
                                                          @Part MultipartBody.Part part);


}
