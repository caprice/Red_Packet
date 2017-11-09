package com.fan.service.rest.service;

import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.ChangePersonDetaResponse;
import com.fan.service.response.ChangePersonImgResponse;
import com.fan.service.response.PersonDateResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:28
 */

public interface BalanceService {
    /**
     * 余额明细
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("public/index.php/myyelist")
    Observable<BalanceDetailResponse> balanceDetail(@Field("token") String token
    );

}
