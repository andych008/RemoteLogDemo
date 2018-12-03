package wang.catuncle.remotelogdemo;

import android.app.Application;

import wang.catunclue.wslog.WSLog;

public class MyApp extends Application {

    // TODO: 请修改成你自己的ws服务地址
    private static final String wsUrl = "ws://wslog.leanapp.cn/ws";

    @Override
    public void onCreate() {
        super.onCreate();
        WSLog.getInstance().init(wsUrl);

        if (BuildConfig.DEBUG) {
            WSLog.getInstance().setEnable(true);
        }
    }
}
