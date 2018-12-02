package wang.catuncle.remotelogdemo;

import android.app.Application;

import wang.catunclue.wslog.WSLog;

public class MyApp extends Application {

    private static final String wsUrl = "ws://192.168.1.112:8999";

    @Override
    public void onCreate() {
        super.onCreate();
        WSLog.getInstance().init(wsUrl);

        if (BuildConfig.DEBUG) {
            WSLog.getInstance().setEnable(true);
        }
    }
}
