package com.haoxiong.taotao.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zengzhigang on 15-8-7.
 */
public class SharePreferenceUtil {
    private static String DEFAULT_PREF_FILE_NAME = "cache";

    private static SharedPreferences sharedPreferences;

    /**
     * Set a String value in the preferences.
     */
    public static void put(Context context, String key, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value)
                .apply();
    }

    /**
     * Set a String value in the preferences.
     */
    public static void put(Context context, String key, boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void put(Context context,String key,int value)
    {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static void put(Context context,String key,long value)
    {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putLong(key, value).apply();
    }

    /**
     * Retrieve a String value from the preferences.The default value is a empty string.
     */
    public static String get(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, "");
    }
    public static String getnull(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, null);
    }
    /**
     * Retrieve a String value from the preferences.The default value is a empty string.
     */
    public static Boolean getBoolean(Context context, String key,boolean defaultValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * Retrieve a String value from the preferences.The default value is a empty string.
     */
    public static int getInt(Context context, String key,int defaultValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static long getLong(Context context, String key,long defaultValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * Remove the preference value.
     */
    public static void remove(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().remove(key).apply();
    }
}
