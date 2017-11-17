package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.ActiveResponse;
import com.fan.service.response.AlipayResponse;
import com.fan.service.response.GetRedPacketResponse;
import com.fan.service.response.RedPacketDetailResponse;
import com.fan.service.response.RedPacketListResponse;
import com.fan.service.response.SaveResponse;
import com.fan.service.response.SendMessageResponse;
import com.fan.service.response.SendRedPacketResponse;
import com.fan.service.response.WalletResponse;
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
 * 创建时间：2017/9/9.22:07
 */

public class RedPacketListApi {
    public static void redPacketListLogined(final Context context, String token,
                                            String latitude,
                                            String longitude,
                                            final int num,
                                            int page,
                                            int sort,
                                            final OnRequestCompletedListener<RedPacketListResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .redPacketListLogin(token, latitude, longitude, num, page, sort, 3)
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
                                    RedPacketListResponse detailResponse = new Gson().fromJson(json, RedPacketListResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                default:
                                    RedPacketListResponse response1 = new RedPacketListResponse();
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


    public static void redPacketListUnlogin(final Context context,
                                            String latitude,
                                            String longitude,
                                            int num,
                                            int page,
                                            int sort,
                                            final OnRequestCompletedListener<RedPacketListResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .redPacketListUnLogin(latitude, longitude, num, page, sort, 3)
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
                                    RedPacketListResponse detailResponse = new Gson().fromJson(json, RedPacketListResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                case -1:
                                    RedPacketListResponse response1 = new RedPacketListResponse();
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

    public static void redPacketDetailUnLogin(final Context context,
                                              int rid,
                                              final OnRequestCompletedListener<RedPacketDetailResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .redPacketDetailUnLogin(rid)
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
                                    RedPacketDetailResponse detailResponse = new Gson().fromJson(json, RedPacketDetailResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                case -1:
                                    RedPacketDetailResponse response1 = new RedPacketDetailResponse();
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

    public static void redPacketDetailLogin(final Context context, String token,
                                            int rid,
                                            final OnRequestCompletedListener<RedPacketDetailResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .redPacketDetailLogin(token, rid)
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
                                    RedPacketDetailResponse detailResponse = new Gson().fromJson(json, RedPacketDetailResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                default:
                                    RedPacketDetailResponse response1 = new RedPacketDetailResponse();
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

    public static void saveOrNot(final Context context, String token,
                                 int rid, boolean action,
                                 final OnRequestCompletedListener<SaveResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .saveOrNot(token, rid, action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SaveResponse response) {
                        listener.onCompleted(response, "成功");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, "服务器异常...");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void getRedPacket(final Context context, String token,
                                    int rid, int answer,
                                    final OnRequestCompletedListener<GetRedPacketResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .getRedPacket(token, rid, answer)
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
                                    GetRedPacketResponse detailResponse = new Gson().fromJson(json, GetRedPacketResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                                default:
                                    GetRedPacketResponse response1 = new GetRedPacketResponse();
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
                        listener.onCompleted(null, "服务器错误");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void sendLocalRedPacket(final Context context, String token,
                                          int money,
                                          int pcount,
                                          int type,
                                          int distance,
                                          String merchant,
                                          String merchant_des,
                                          String question,
                                          String first_answer,
                                          String second_answer,
                                          String third_answer,
                                          String address,
                                          String tel,
                                          String lng,
                                          String lat,
                                          String pic1_filecode,
                                          final OnRequestCompletedListener<SendRedPacketResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .sendLocalRedPacket(token, money, pcount, type, distance, merchant, merchant_des, question, first_answer, second_answer, third_answer, address, tel, lng, lat, pic1_filecode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendRedPacketResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SendRedPacketResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void sendRedPacket(final Context context, String token,
                                     int money,
                                     int pcount,
                                     int type,
                                     String merchant,
                                     String merchant_des,
                                     String question,
                                     String first_answer,
                                     String second_answer,
                                     String third_answer,
                                     String address,
                                     String tel,
                                     String pic1_filecode,
                                     final OnRequestCompletedListener<SendRedPacketResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .sendRedPacket(token, money, pcount, type, merchant, merchant_des, question, first_answer, second_answer, third_answer, address, tel, pic1_filecode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendRedPacketResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SendRedPacketResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public static void alipayrp(final Context context, String token,
                                     final OnRequestCompletedListener<Object> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .alipayrp(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Object response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public static void get_pay_param(final Context context, String token,int money,int rid,
                                     final OnRequestCompletedListener<Object> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .get_pay_param(token,"wxpay",money,rid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Object response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public static void get_alipay_param(final Context context, String token,
                                     final OnRequestCompletedListener<AlipayResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .get_alipay_param(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AlipayResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull AlipayResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void get_my_wallet(final Context context, String token,
                                     final OnRequestCompletedListener<WalletResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .get_my_wallet(token)
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
                                    WalletResponse detailResponse = new Gson().fromJson(json, WalletResponse.class);
                                    listener.onCompleted(detailResponse, "成功");
                                    break;
                               default:
                                    WalletResponse response1 = new WalletResponse();
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

    public static void update_user_location(final Context context, String token,String latitude,String longitude,
                                     final OnRequestCompletedListener<WalletResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .update_user_location(token,latitude,longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object response) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public static void update_device(final Context context, String token,String device,
                                     final OnRequestCompletedListener<WalletResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .update_device(token,device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object response) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public static void getActive(final Context context, String token,
                                     final OnRequestCompletedListener<ActiveResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getRedPacketListService()
                .getActive(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ActiveResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ActiveResponse response) {
                        listener.onCompleted(response,"成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null,"服务器数据异常");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
