package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.PayResponse;
import com.fan.service.response.WxPayResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.18:11
 */

public class PayServiceApi {
    public static String TAG = PayServiceApi.class.getSimpleName();

    public static void pay(final Context context, String token, int money
            , int rid
           , final OnRequestCompletedListener<PayResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getPayService()
                .pay(token,money,rid,"wallet")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("...", "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull PayResponse response) {
                        Log.e("...", response.toString());
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, "发布失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("...", "onComplete");
                    }
                });
    }

    public static void wxpay(final Context context, String token, int money
            , int rid
            , final OnRequestCompletedListener<WxPayResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getPayService()
                .wxpay(token,money,rid,"wxpay")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WxPayResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("...", "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull WxPayResponse response) {
                        Log.e("...", response.toString());
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("...", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("...", "onComplete");
                    }
                });
    }

    public static void alipay(final Context context, String token, int money
            , int rid
            , final OnRequestCompletedListener<WxPayResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getPayService()
                .alipay(token,money,rid,"alipay")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WxPayResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("...", "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull WxPayResponse response) {
                        Log.e("...", response.toString());
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("...", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("...", "onComplete");
                    }
                });
    }
}
