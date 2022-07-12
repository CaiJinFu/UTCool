package com.caijinfu.utcool;

import android.app.Application;

/**
 * App
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
public class App extends Application {

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static App getApp() {
        return mApp;
    }
}
