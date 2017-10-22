package com.haoxiong.taotao.base.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.haoxiong.taotao.util.ToastUtils;

/**
 * desc: 网络变化的监听
 * author: xiao
 * time: 2016/11/29
 */
public class NetConnectChangeReceiver extends BroadcastReceiver {
    public static String TAG = NetConnectChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null) {
                    //网络连接上了
                    if (info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED) {
                        //                        ToastUtil.showShortToast(context, "网络已连接");
                        Log.i(TAG, "onReceive: 网络已连接 getExtraInfo()=" + info.getExtraInfo());
                        /*if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                            Log.i(TAG, "onReceive: 网络类型：TYPE_WIFI");
                        }
                        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            Log.i(TAG, "onReceive: 网络类型：TYPE_MOBILE");
                        }*/
                        //                        ToastUtil.showShortToast(context, "网络已连接");


                    } else {
                        //网络断开了
                        ToastUtils.toTosat(context, "网络已断开");
                        //                        Log.i(TAG, "onReceive: 网络断开");
                    }
                } else {
                    ToastUtils.toTosat(context, "网络已断开");
                    //                    Log.i(TAG, "onReceive: NetworkInfo null");
                }
            } else {
                ToastUtils.toTosat(context, "网络异常");
                //                Log.i(TAG, "onReceive: ConnectivityManager null");
            }
        }
    }
}
