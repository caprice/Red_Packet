package com.fan.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fan.service.rest.converter.CustomGsonConverterFactory;
import com.fan.service.rest.service.BalanceService;
import com.fan.service.rest.service.CollectService;
import com.fan.service.rest.service.FirendListService;
import com.fan.service.rest.service.MessageService;
import com.fan.service.rest.service.PayService;
import com.fan.service.rest.service.PersonService;
import com.fan.service.rest.service.RedManagerService;
import com.fan.service.rest.service.RedPacketListService;
import com.fan.service.rest.service.SendCodeService;
import com.fan.service.rest.gson.DoubleDefault0Adapter;
import com.fan.service.rest.gson.IntegerDefault0Adapter;
import com.fan.service.rest.gson.LongDefault0Adapter;
import com.fan.service.util.MD5Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;

/**
 * Created by yinjin on 2017年10月27日19:48:45
 */
public class Client {
    //    public static final String BASE_URL = "http://hbapi.huidang2105.com:8900/";
    public static final String BASE_URL = "http://hb.huidang2105.com:8900/";
    public static final String BASE_URL_IMG = "http://taotaohb.oss-cn-beijing.aliyuncs.com/";
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
    private final MessageService messageService;
    private final Retrofit retrofit;
    private Context context;

    public Client(final Context context) {
        this.context = context;
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
        messageService = retrofit.create(MessageService.class);
    }

    public MessageService getMessageService() {
        return messageService;
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
//        @NonNull
//        private OkHttpClient createOkHttpClient(final Context context) {
//            //okhttp
//            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            //本拦截器用于加headers
//                            Request request = chain.request();
//                            Request.Builder requestBuilder = request.newBuilder();
//                            String url = request.url().toString();
//                            if (isNeedCookie(url)) {
//                                StringBuilder stringBuilder = new StringBuilder();
//                                stringBuilder.append("ticket=");
//                                String ticket = Util.nullToDefault(TicketUtil.getTicketToken(context));
//                                stringBuilder.append(ticket);
//                                if (RetrofitApplication.vehicle != null) {
//                                    if (RetrofitApplication.vehicle.isVip()) {
//                                        stringBuilder.append(";vehicle_id=");
//                                    } else {
//                                        stringBuilder.append(";normal_vehicle_id=");
//                                    }
//                                    stringBuilder.append(RetrofitApplication.vehicle.getId());
//                                }
//                                requestBuilder
//                                        .addHeader("cookie", stringBuilder.toString())
//                                        .addHeader("User-Agent", UserAgentUtil.getUserAgent(context));
//                            } else {
//                                requestBuilder
//                                        .addHeader("User-Agent", UserAgentUtil.getUserAgent(context));
//                            }
//                            request = requestBuilder
//                                    .build();
//                            return chain.proceed(request);
//                        }
//                    })
//                /*.addInterceptor(new ForcedUpdateInterceptor(context))*/;
//            if (BuildConfig.DEBUG) {
//                //log
//                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//                builder.addInterceptor(loggingInterceptor);
//            }
//
//            //add fanhl 2017/1/11 超过时间设置
//            builder
//                    .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
//                    .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS);
//            //请求失败后禁止retry
//            builder.retryOnConnectionFailure(false);
//            return builder.build();
//        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        RequestBody requestBody = request.body();
                        Buffer buffer = new Buffer();
                        requestBody.writeTo(buffer);
                        Charset charset = Charset.forName("UTF-8");
                        MediaType contentType = requestBody.contentType();
                        if (contentType != null) {
                            charset = contentType.charset(charset);
                        }
                        //获取上次的上传的参数
                        String paramsStr = buffer.readString(charset);
                        //下面是进行加密
                        long currentTimeMillis = System.currentTimeMillis();
                        paramsStr = paramsStr + "&timestamp=" + currentTimeMillis;
                        paramsStr = "S%12hd_&" + paramsStr;
                        String params = MD5Util.encrypt(paramsStr) + "Hd2071&";

                        RequestBody body = request.body();
                        //收集请求参数，方便调试
                        StringBuilder paramsBuilder = new StringBuilder();

                        if (body != null) {

                            RequestBody newBody = null;

                            if (body instanceof FormBody) {
                                newBody = addParamsToFormBody((FormBody) body, paramsBuilder, MD5Util.encrypt(params), currentTimeMillis);
                            } else if (body instanceof MultipartBody) {
                                newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder, MD5Util.encrypt(params), currentTimeMillis);
                            }

                            if (null != newBody) {
                                //打印参数
                                Log.e(TAG, paramsBuilder.toString());
                                Request newRequest = request.newBuilder()
                                        .url(request.url())
                                        .method(request.method(), newBody)
                                        .build();

                                return chain.proceed(newRequest);
                            }
                        }
//                        request = requestBuilder
//                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(loggingInterceptor)
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

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder, String content, long currentTimeMillis) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }

        builder.addFormDataPart("timestamp", currentTimeMillis + "");
        paramsBuilder.append("timestamp=" + currentTimeMillis);
        builder.addFormDataPart("sign", content);
        paramsBuilder.append("sign=" + content);
        return builder.build();
    }

    /**
     * 为FormBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder, String content, long currentTimeMillis) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));

            paramsBuilder.append(body.name(i));
            paramsBuilder.append("=");
            paramsBuilder.append(body.value(i));
            paramsBuilder.append("&");
        }
        builder.add("timestamp", currentTimeMillis + "");
        paramsBuilder.append("timestamp=" + currentTimeMillis);
        builder.add("sign", content);
        paramsBuilder.append("sign=" + content);
        return builder.build();
    }
}
