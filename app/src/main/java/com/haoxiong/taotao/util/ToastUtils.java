package com.haoxiong.taotao.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/7/10.22:19
 */

public class ToastUtils {
    public static void toTosat(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void toLongTosat(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
