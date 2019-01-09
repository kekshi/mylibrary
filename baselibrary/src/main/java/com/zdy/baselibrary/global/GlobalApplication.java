package com.zdy.baselibrary.global;

import android.app.Application;
import android.content.Context;

public class GlobalApplication extends Application {
    protected static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }
}
