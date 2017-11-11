package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.LoginResponse;
import com.fan.service.response.RedPacketListResponse;
import com.fan.service.response.RegisterResponse;
import com.fan.service.response.SendMessageResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class LoginServiceApi {
    public static String TAG = LoginServiceApi.class.getSimpleName();

    public static void sendCode(final Context context, String mobile, final OnRequestCompletedListener<SendMessageResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getSendCodeService()
                .sendRegisterCode(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendMessageResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SendMessageResponse response) {
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

    public static void login(final Context context, String mobile, String vecode, String device, final OnRequestCompletedListener<LoginResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getSendCodeService()
                .login(mobile, vecode, device)
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
                                    LoginResponse detailResponse = new Gson().fromJson(json, LoginResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                default:
                                    LoginResponse response1 = new LoginResponse();
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
                        listener.onCompleted(null, "网络连接失败");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void register(final Context context, String mobile, String inviter, final OnRequestCompletedListener<RegisterResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getSendCodeService()
                .register(mobile, inviter)
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
                                case 333:
                                    RegisterResponse detailResponse = new Gson().fromJson(json, RegisterResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                default:
                                    RegisterResponse response1 = new RegisterResponse();
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
                        listener.onCompleted(null, "网络连接失败");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void setInviteCode(final Context context, String token, String inviteCode, final OnRequestCompletedListener<LoginResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getSendCodeService()
                .setInviteCode(token, inviteCode)
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
                                    LoginResponse detailResponse = new Gson().fromJson(json, LoginResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                default:
                                    LoginResponse response1 = new LoginResponse();
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
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
