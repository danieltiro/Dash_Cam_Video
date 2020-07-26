package com.rodimisas.dash_cam_video.App;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;

public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(1000);
    }
    public static Context getAppContext() {
        return MyApp.context;
    }
}
