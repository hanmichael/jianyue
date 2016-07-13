package com.yiming.jianyue;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yiming.jianyue.old.model.config.FrescoConfig;

import android.app.Application;
import android.content.Context;

/**
 * Created by wengyiming on 2015/12/14.
 */
public class AppClient extends Application {
    private static AppClient sInstance;

    public static AppClient getInstance() {
        return sInstance;
    }

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        AppClient application = (AppClient) context.getApplicationContext();

        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        PgyCrashManager.register(sInstance);
        refWatcher = LeakCanary.install(sInstance);
        Fresco.initialize(sInstance, FrescoConfig.getImagePipelineConfig(sInstance));
    }
}
