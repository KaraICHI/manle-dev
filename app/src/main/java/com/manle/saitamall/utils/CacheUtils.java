package com.manle.saitamall.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 缓存工具类
 */
public class CacheUtils {

    public static final String SP_NAME = "News";
    private static FileOutputStream fos;
    private static ByteArrayOutputStream baos;
    private static FileInputStream fis;

    public static boolean getBoolean(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        return sp.getBoolean(key, false);
    }

    public static void putBoolean(Context context, String key, boolean b) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, b).commit();
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context mContext, String key) {
        String result = "";
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        result = sp.getString(key, "");

        return result;
    }

    public static Long getLong(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, -1);
    }

    public static void putLong(Context mContext, String key, Long value) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }
    public static Float getFloat(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key,0f);
    }

    public static void putFloat(Context mContext, String key, Float value) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putFloat(key, value).commit();
    }

}
