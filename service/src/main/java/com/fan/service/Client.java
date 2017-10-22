package com.fan.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fan.service.rest.converter.CustomGsonConverterFactory;
import com.fan.service.rest.service.BalanceService;
import com.fan.service.rest.service.CollectService;
import com.fan.service.rest.service.FirendListService;
import com.fan.service.rest.service.PayService;
import com.fan.service.rest.service.PersonService;
import com.fan.service.rest.service.RedManagerService;
import com.fan.service.rest.service.RedPacketListService;
import com.fan.service.rest.service.SendCodeService;
import com.fan.service.rest.gson.DoubleDefault0Adapter;
import com.fan.service.rest.gson.IntegerDefault0Adapter;
import com.fan.service.rest.gson.LongDefault0Adapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fanhl on 2016/3/22.
 */
public class Client {
    public static final String BASE_URL = "http://hb.huidang2105.com:8900/";
    public static final String TAG = Client.class.getSimpleName();
    /**
     * http请求的超时时间
     */
    public static final int TIMEOUT_TIME = 10;
    private final SendCodeService sendCodeService;
    private final RedPacketListService redPacketListService;
    private final PersonService personService;
    private final BalanceService balanceService;
    private final RedManagerService redManagerService;
    private final CollectService collectService;
    private final FirendListService friendListService;
    private final PayService payService;
    private final Retrofit retrofit;

    public Client(final Context context) {
        OkHttpClient client = createOkHttpClient();
        Gson gson = createGson();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(CustomGsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        sendCodeService = retrofit.create(SendCodeService.class);
        redPacketListService = retrofit.create(RedPacketListService.class);
        personService = retrofit.create(PersonService.class);
        balanceService = retrofit.create(BalanceService.class);
        redManagerService = retrofit.create(RedManagerService.class);
        collectService = retrofit.create(CollectService.class);
        friendListService = retrofit.create(FirendListService.class);
        payService = retrofit.create(PayService.class);
    }

    public PayService getPayService() {
        return payService;
    }

    public FirendListService getFriendListService() {
        return friendListService;
    }

    public CollectService getCollectService() {
        return collectService;
    }

    public RedManagerService getRedManagerService() {
        return redManagerService;
    }

    public BalanceService getBalanceService() {
        return balanceService;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public RedPacketListService getRedPacketListService() {
        return redPacketListService;
    }

    public SendCodeService getSendCodeService() {
        return sendCodeService;
    }

    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(false)
                .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                .build();
        return client;
    }

    @NonNull
    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .create();
    }
}
