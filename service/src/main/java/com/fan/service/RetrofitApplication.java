package com.fan.service;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.fan.service.moudle.LoginState;



public class RetrofitApplication extends MultiDexApplication {

    protected Client client;

    /** 用户登录状态 */
    public static LoginState loginState;


    @Override public void onCreate() {
        super.onCreate();
        client = new Client(this);
    }

    public Client getClient() {
        return client;
    }
}
