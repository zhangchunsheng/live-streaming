package com.luomor.liteav.demo;

import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.rtmp.TXLiveBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;


public class DemoApplication extends MultiDexApplication {

//    private RefWatcher mRefWatcher;
    private static DemoApplication instance;

    // 如何获取License? 请参考官网指引 https://cloud.tencent.com/document/product/454/34750
    String licenceUrl = "http://license.vod2.myqcloud.com/license/v1/471f41a59f3d23e7786581f9b4a4855b/TXLiveSDK.licence";
    String licenseKey = "153e84d06e21489816f7876c3c1b72b4";

    @Override
    public void onCreate() {

        super.onCreate();

        instance = this;

        TXLiveBase.setConsoleEnabled(true);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(TXLiveBase.getSDKVersionStr());
        CrashReport.initCrashReport(getApplicationContext(),strategy);

        TXLiveBase.getInstance().setLicence(instance, licenceUrl, licenseKey);

        closeAndroidPDialog();
//        File file = getFilesDir();
//        Log.w("DemoApplication", "load:" + file.getAbsolutePath());
//        TXLiveBase.setLibraryPath(file.getAbsolutePath());
        //测试代码
//        TCHttpEngine.getInstance().initContext(getApplicationContext());
//        mRefWatcher = LeakCanary.install(this);
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        DemoApplication application = (DemoApplication) context.getApplicationContext();
//        return application.mRefWatcher;
//    }

    public static DemoApplication getApplication() {
        return instance;
    }

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
