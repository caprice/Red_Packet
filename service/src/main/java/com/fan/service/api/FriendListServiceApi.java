package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.CollectRedPacketResponse;
import com.fan.service.response.FriendListResponse;
import com.fan.service.response.RedPacketDetailResponse;
import com.fan.service.response.WithDrawResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.18:11
 */

public class FriendListServiceApi {
    public static String TAG = FriendListServiceApi.class.getSimpleName();

    public static void firendList(final Context context, String token, final OnRequestCompletedListener<FriendListResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getFriendListService()
                .firendList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull FriendListResponse response) {
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

  public static void applyWithdraw(final Context context, String token, double money  , String account, final OnRequestCompletedListener<WithDrawResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getFriendListService()
                .applyWithdraw(token,money,1,account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody response) {
                        try {
                            String json = response.string();
                            JSONObject object = new JSONObject(json);
                            int err = object.optInt("err");
                            switch (err) {
                                case 0:
                                    WithDrawResponse detailResponse = new Gson().fromJson(json, WithDrawResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                case -1:
                                    WithDrawResponse response1 = new WithDrawResponse();
                                    response1.setErr(err);
                                    response1.setMsg(object.optString("msg"));
                                    response1.setData(null);
                                    listener.onCompleted(response1, object.optString("msg"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, "提现失败...");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
