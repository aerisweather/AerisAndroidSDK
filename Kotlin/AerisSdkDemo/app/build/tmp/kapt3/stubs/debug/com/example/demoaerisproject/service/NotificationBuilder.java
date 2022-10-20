package com.example.demoaerisproject.service;

import android.content.Context;
import android.os.SystemClock;
import com.aerisweather.aeris.communication.parameter.PlaceParameter;
import com.aerisweather.aeris.logging.Logger;
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import com.example.demoaerisproject.data.weather.AerisBatchResponseEvent;
import com.example.demoaerisproject.data.weather.WeatherRepository;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0006\u0010\u0019\u001a\u00020\u0016J\u0012\u0010\u001a\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001e"}, d2 = {"Lcom/example/demoaerisproject/service/NotificationBuilder;", "", "context", "Landroid/content/Context;", "myPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "prefStoreRepository", "Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "weatherRepository", "Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "prefStore", "(Landroid/content/Context;Lcom/example/demoaerisproject/data/room/MyPlaceRepository;Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;Lcom/example/demoaerisproject/data/weather/WeatherRepository;Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;)V", "getContext", "()Landroid/content/Context;", "getMyPlaceRepository", "()Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "getPrefStore", "()Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "getPrefStoreRepository", "getWeatherRepository", "()Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "initEventListener", "", "isMetricEnabled", "", "request", "send", "response", "Lcom/aerisweather/aeris/model/AerisBatchResponse;", "Companion", "app_debug"})
public final class NotificationBuilder {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.demoaerisproject.data.weather.WeatherRepository weatherRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStore = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.service.NotificationBuilder.Companion Companion = null;
    private static final java.lang.String TAG = null;
    
    public NotificationBuilder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.weather.WeatherRepository weatherRepository, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.room.MyPlaceRepository getMyPlaceRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository getPrefStoreRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.weather.WeatherRepository getWeatherRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository getPrefStore() {
        return null;
    }
    
    public final void request() {
    }
    
    private final void initEventListener() {
    }
    
    private final void send(com.aerisweather.aeris.model.AerisBatchResponse response) {
    }
    
    private final boolean isMetricEnabled() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/demoaerisproject/service/NotificationBuilder$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}