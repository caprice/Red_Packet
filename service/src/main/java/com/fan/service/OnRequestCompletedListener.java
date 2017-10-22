package com.fan.service;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/8/20.17:19
 */

public interface OnRequestCompletedListener<T> {
    void onCompleted(T response, String msg);
}
