package com.example.demoaerisproject.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import com.example.demoaerisproject.data.weather.WeatherRepository;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001e\u0010\u0012\u001a\u00020\u00138\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001d"}, d2 = {"Lcom/example/demoaerisproject/service/NotificationService;", "Landroid/app/job/JobService;", "()V", "myPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "getMyPlaceRepository", "()Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "setMyPlaceRepository", "(Lcom/example/demoaerisproject/data/room/MyPlaceRepository;)V", "prefStore", "Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "getPrefStore", "()Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "setPrefStore", "(Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;)V", "prefStoreRepository", "getPrefStoreRepository", "setPrefStoreRepository", "weatherRepository", "Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "getWeatherRepository", "()Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "setWeatherRepository", "(Lcom/example/demoaerisproject/data/weather/WeatherRepository;)V", "onStartJob", "", "jobParameters", "Landroid/app/job/JobParameters;", "onStopJob", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class NotificationService extends android.app.job.JobService {
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository;
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository;
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.weather.WeatherRepository weatherRepository;
    @javax.inject.Inject()
    public com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStore;
    
    public NotificationService() {
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
    public boolean onStartJob(@org.jetbrains.annotations.NotNull()
    android.app.job.JobParameters jobParameters) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onStopJob(@org.jetbrains.annotations.NotNull()
    android.app.job.JobParameters jobParameters) {
        return false;
    }
}