package com.manle.saitamall.app;

import android.app.Application;
import android.content.Context;

import com.manle.saitamall.bean.User;

// 全局应用
public class MyAppliction extends Application {
    private static Context mContext;
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyAppliction.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
    }

    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }
}
