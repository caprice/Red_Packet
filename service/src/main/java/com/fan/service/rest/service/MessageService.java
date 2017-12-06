package com.fan.service.rest.service;

import com.fan.service.response.MessageResponse;
import com.fan.service.response.MessageSendResponse;
import com.fan.service.response.ReadMessageResponse;
import com.fan.service.response.UnReadMessageResponse;
import com.fan.service.response.UnreadResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/6.21:38
 */

public interface MessageService {
    /**
     * 获取用户聊天未读的消息
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("http://hb.huidang2105.com:89/")
    Observable<UnreadResponse> unReadNum(@Field("service") String service, @Field("token") String token);

    /**
     * 获取用户聊天未读的消息
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("http://hb.huidang2105.com:89/")
    Observable<MessageResponse> messageList(@Field("service") String service, @Field("token") String token);

    /**
     * 消息发送
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("http://hb.huidang2105.com:89/")
    Observable<MessageSendResponse> sendMessage(@Field("service") String service, @Field("token") String token, @Field("rid") String rid, @Field("fsxx") String fsxx);

    /**
     * 获取未读聊天详情信息
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("http://hb.huidang2105.com:89/")
    Observable<UnReadMessageResponse> unReadMessageList(@Field("service") String service, @Field("token") String token, @Field("rid") String rid);  /**
     * 获取聊天记录信息
     * mobile
     * xh checked 20170424
     */
    @FormUrlEncoded
    @POST("http://hb.huidang2105.com:89/")
    Observable<ReadMessageResponse> readMessageList(@Field("service") String service, @Field("token") String token, @Field("rid") String rid);
}
