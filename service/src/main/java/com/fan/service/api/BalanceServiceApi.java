package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.BalanceDetailResponse;
import com.fan.service.response.ChangePersonDetaResponse;
import com.fan.service.response.ChangePersonImgResponse;
import com.fan.service.response.PersonDateResponse;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.18:11
 */

public class BalanceServiceApi {
    public static String TAG = BalanceServiceApi.class.getSimpleName();

    public static void balanceDetail(final Context context, String token,int page,int num, final OnRequestCompletedListener<BalanceDetailResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getBalanceService()
                .balanceDetail(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BalanceDetailResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("...", "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull BalanceDetailResponse response) {
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
