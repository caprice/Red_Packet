package com.haoxiong.taotao;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;


import com.amap.api.location.AMapLocation;
import com.fan.service.RetrofitApplication;

import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.util.StatusBarUtil;
import com.igexin.sdk.PushManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.whoislcj.eventbus.MyEventBusIndex;

import org.greenrobot.eventbus.EventBus;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/29.20:41
 */

public class MyApp extends RetrofitApplication {
    public static AMapLocation location;
    public Vibrator mVibrator;
    public  com.fan.service.response.LoginResponse user;
    /**
     * 0 未登录 1 登陆
     */
    public static int login_state = 0;
    public static boolean local = false;
    /**
     * 1 发红包 2 抢红包 3 收藏 4 红包管理 5一抢过
     */
    public static String clientid;
    public static String token;
    public static int TYPE;
    private static MyApp myApp;
    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e("app", b + "");
            }
        };
        QbSdk.initX5Environment(this, cb);
        CrashReport.initCrashReport(getApplicationContext(), "424587a86d", true);
        myApp = this;
        UMShareAPI.get(this);
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        PushManager.getInstance().initialize(this, null);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.haoxiong.taotao.service.GeTuiIntentService.class);
        PlatformConfig.setWeixin("wxc74f296cef01342f", "0cad16491caf49b664ade5ef7c67a336");
        PlatformConfig.setSinaWeibo("2438395451", "1fc6d098934c85b997e19d4ca349b40a", "https://api.weibo.com/oauth2/default.html");
        PlatformConfig.setQQZone("1106322882", "dNGt8adKf39Domuc");
    }
    public static MyApp getInstance() {
        if (myApp == null) {
            myApp = new MyApp();
            return myApp;
        }
        return myApp;
    }


}
