package com.haoxiong.taotao.base;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;


import com.haoxiong.taotao.base.broadcastreceiver.NetConnectChangeReceiver;


/**
 * 新版本的Activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    // 判断当前登录帐号是否是测试帐号
    private ProgressDialog progressDialog;
    private NetConnectChangeReceiver netConnectChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置为竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 添加监听
     */
    private void registerReceiver() {
        //网络监听
        if (netConnectChangeReceiver == null) {
            netConnectChangeReceiver = new NetConnectChangeReceiver();
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netConnectChangeReceiver, filter);
    }

    /**
     * 取消监听
     */
    private void unregisterReceiver() {
        unregisterReceiver(netConnectChangeReceiver);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * show a progress dialog with the given message.
     */
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    /**
     * show a progress dialog with the given message.
     */
    public void showProgressDialog(String msg, boolean cancelable) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(cancelable);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        if (!progressDialog.isShowing()) {
            if (!isFinishing()) {
                progressDialog.show();
                progressDialog.setMessage(msg);
            }
        }
    }

    /**
     * close a progress if there is one.
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        progressDialog = null;
    }
}
