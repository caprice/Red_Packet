package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.CollectRedPacketResponse;

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

public class CollectServiceApi {
    public static String TAG = CollectServiceApi.class.getSimpleName();

    public static void collectRedPacket(final Context context, String token, final OnRequestCompletedListener<CollectRedPacketResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getCollectService()
                .collectRedPacket(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectRedPacketResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull CollectRedPacketResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, "");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
