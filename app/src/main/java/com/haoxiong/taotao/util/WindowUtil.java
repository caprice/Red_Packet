package com.haoxiong.taotao.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/3.21:39
 */

public class WindowUtil {
    public static int getWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }
    public static int getHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getHeight();
    }
}
