package com.fan.service.api;

import android.content.Context;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.response.MessageResponse;
import com.fan.service.response.MessageSendResponse;
import com.fan.service.response.ReadMessageResponse;
import com.fan.service.response.RefreshTimeResponse;
import com.fan.service.response.UnReadMessageResponse;
import com.fan.service.response.UnreadResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/12/6.21:42
 */

public class MessageApi {
    public static String TAG = PayServiceApi.class.getSimpleName();

    public static void refreshTime(final Context context, final OnRequestCompletedListener<RefreshTimeResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getMessageService()
                .refreshTime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RefreshTimeResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull RefreshTimeResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, "发布失败");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
 public static void unReadNum(final Context context, String token, final OnRequestCompletedListener<UnreadResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getMessageService()
                .unReadNum(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UnreadResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull UnreadResponse response) {
                        listener.onCompleted(response, "成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(null, "发布失败");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void messageList(final Context context, String token, final OnRequestCompletedListener<MessageResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getMessageService()
                .messageList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MessageResponse response) {
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

    public static void sendMessage(final Context context, String token, String rid,String ltid ,String content, final OnRequestCompletedListener<MessageSendResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getMessageService()
                .sendMessage( token, rid,ltid, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageSendResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MessageSendResponse response) {
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

    public static void unReadMessageList(final Context context, String token, String rid,String ltid, final OnRequestCompletedListener<UnReadMessageResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getMessageService()
                .unReadMessageList(token, rid,ltid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UnReadMessageResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull UnReadMessageResponse response) {
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

    public static void readMessageList(final Context context, String token, String rid,String ltid,int page, final OnRequestCompletedListener<ReadMessageResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getMessageService()
                .readMessageList(token, rid,ltid,page,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReadMessageResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ReadMessageResponse response) {
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
