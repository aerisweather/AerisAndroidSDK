package com.example.demoaerisproject.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.aerisweather.aeris.logging.Logger;
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import com.example.demoaerisproject.data.weather.WeatherRepository;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000  2\u00020\u0001:\u0001 B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001e\u0010\u0012\u001a\u00020\u00138\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006!"}, d2 = {"Lcom/example/demoaerisproject/service/NotificationJobService;", "Landroid/app/job/JobService;", "()V", "myPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "getMyPlaceRepository", "()Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "setMyPlaceRepository", "(Lcom/example/demoaerisproject/data/room/MyPlaceRepository;)V", "prefStore", "Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "getPrefStore", "()Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "setPrefStore", "(Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;)V", "prefStoreRepository", "getPrefStoreRepository", "setPrefStoreRepository", "weatherRepository", "Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "getWeatherRepository", "()Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "setWeatherRepository", "(Lcom/example/demoaerisproject/data/weather/WeatherRepository;)V", "collectNotificationEnabled", "", "onCreate", "", "onStartJob", "jobParameters", "Landroid/app/job/JobParameters;", "onStopJob", "Companion", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class NotificationJobService extends android.app.job.JobService {
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository;
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository;
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.weather.WeatherRepository weatherRepository;
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStore;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.service.NotificationJobService.Companion Companion = null;
    private static final java.lang.String TAG = null;
    
    public NotificationJobService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.room.MyPlaceRepository getMyPlaceRepository() {
        return null;
    }
    
    public final void setMyPlaceRepository(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository getPrefStoreRepository() {
        return null;
    }
    
    public final void setPrefStoreRepository(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.weather.WeatherRepository getWeatherRepository() {
        return null;
    }
    
    public final void setWeatherRepository(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.weather.WeatherRepository p0) {
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
    
    /**
     * onStartJob()
     * @return Always return true so that the job will be rescheduled to run again
     */
    private final boolean collectNotificationEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean onStartJob(@org.jetbrains.annotations.NotNull()
    android.app.job.JobParameters jobParameters) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onStopJob(@org.jetbrains.annotations.NotNull()
    android.app.job.JobParameters jobParameters) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/demoaerisproject/service/NotificationJobService$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}