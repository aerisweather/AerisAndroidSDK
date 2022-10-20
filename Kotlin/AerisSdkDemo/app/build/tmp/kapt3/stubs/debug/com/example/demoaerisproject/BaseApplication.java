package com.example.demoaerisproject;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import com.aerisweather.aeris.communication.AerisEngine;
import com.aerisweather.aeris.logging.Logger;
import com.aerisweather.aeris.maps.AerisMapsEngine;
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import com.example.demoaerisproject.service.NotificationJobService;
import dagger.hilt.android.HiltAndroidApp;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\f"}, d2 = {"Lcom/example/demoaerisproject/BaseApplication;", "Landroid/app/Application;", "()V", "prefStore", "Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "getPrefStore", "()Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "setPrefStore", "(Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;)V", "onCreate", "", "Companion", "app_debug"})
@dagger.hilt.android.HiltAndroidApp()
public final class BaseApplication extends android.app.Application {
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStore;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.BaseApplication.Companion Companion = null;
    private static final int NOTIFICATION_JOB_ID = 2001;
    public static final int PRIMARY_FOREGROUND_NOTIF_SERVICE_ID = 1001;
    private static final int ONE_MIN = 60000;
    private static final java.lang.String TAG = null;
    
    public BaseApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository getPrefStore() {
        return null;
    }
    
    public final void setPrefStore(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/demoaerisproject/BaseApplication$Companion;", "", "()V", "NOTIFICATION_JOB_ID", "", "ONE_MIN", "PRIMARY_FOREGROUND_NOTIF_SERVICE_ID", "TAG", "", "kotlin.jvm.PlatformType", "enableNotificationService", "", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void enableNotificationService(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
}