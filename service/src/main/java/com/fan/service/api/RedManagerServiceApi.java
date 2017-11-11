package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.RedManagerResponse;
import com.fan.service.response.StopOrNotResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.18:11
 */

public class RedManagerServiceApi {
    public static String TAG = RedManagerServiceApi.class.getSimpleName();

    public static void redManager(final Context context, String token, final OnRequestCompletedListener<RedManagerResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedManagerService()
                .balanceDetail(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RedManagerResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull RedManagerResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public static void stopOrNot(final Context context, String token, int rid, boolean status_user, final OnRequestCompletedListener<StopOrNotResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedManagerService()
                .stopOrNot(token,rid,status_user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StopOrNotResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull StopOrNotResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
