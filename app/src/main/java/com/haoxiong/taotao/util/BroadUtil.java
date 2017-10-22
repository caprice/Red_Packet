package com.haoxiong.taotao.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/9/9.23:51
 */

public class BroadUtil {
    public static void showBroad(EditText editText, Context context) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setFocusable(true);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(editText.getWindowToken(),0);
    }
}
