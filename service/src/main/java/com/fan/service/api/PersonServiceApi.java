package com.fan.service.api;

import android.content.Context;
import android.util.Log;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.RetrofitApplication;
import com.fan.service.request.ChangeUserHeadImgRequest;
import com.fan.service.response.ChangePersonDetaResponse;
import com.fan.service.response.ChangePersonImgResponse;
import com.fan.service.response.LoginResponse;
import com.fan.service.response.PersonDateResponse;
import com.fan.service.response.SendMessageResponse;

import java.io.File;
import java.util.List;

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

public class PersonServiceApi {
    public static String TAG = PersonServiceApi.class.getSimpleName();

    public static void getUserInfor(final Context context, String token, final OnRequestCompletedListener<PersonDateResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getPersonService()
                .getUserInfor(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonDateResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull PersonDateResponse response) {
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

    public static void changeUserInfor(final Context context, String token, String username, String birthday, String gender, final OnRequestCompletedListener<ChangePersonDetaResponse> listener) {
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getPersonService()
                .changeUserInfor(token, username, birthday, gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChangePersonDetaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ChangePersonDetaResponse response) {
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

    public static void changeUserHeadImg(final Context context, String token, File file, final OnRequestCompletedListener<ChangePersonImgResponse> listener) {
        RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), token);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), photoRequestBody);
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getPersonService()
                .changeUserHeadImg(tokenBody,part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChangePersonImgResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ChangePersonImgResponse response) {
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
    public static void uploadMerchantPic(final Context context, String token, File file, final OnRequestCompletedListener<ChangePersonImgResponse> listener) {
        RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), token);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), photoRequestBody);
        ((RetrofitApplication) context.getApplicationContext()).getClient()
                .getPersonService()
                .uploadMerchantPic(tokenBody,part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChangePersonImgResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ChangePersonImgResponse response) {
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
