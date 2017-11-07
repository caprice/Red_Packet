package com.haoxiong.taotao.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.haoxiong.taotao.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * desc: 软键盘
 * author: xw
 * time: 2017/1/2
 */
public class KeyboardUtil {
    public static final String TAG = KeyboardUtil.class.getSimpleName();

    /**
     * 打开软键盘
     *
     * @param mEditText
     *         输入框
     * @param mContext
     *         上下文
     */
    public static void showKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
//        imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
//        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        //        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 显示键盘——延迟
     *
     * @param context
     * @param mEditText
     */
    public static void showKeyBoardDelay(final Context context, final EditText mEditText) {
        //延迟弹出软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               KeyboardUtil.showKeybord(mEditText, context);

                           }
                       },
                200);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     *         输入框
     * @param mContext
     *         上下文
     */
    public static void hideKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromInputMethod(mEditText.getApplicationWindowToken(), 0);
        //        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void hideKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
