package com.example.demoaerisproject.data.weather;

import android.content.Context;
import com.aerisweather.aeris.communication.*;
import com.aerisweather.aeris.communication.fields.Fields;
import com.aerisweather.aeris.communication.fields.ForecastsFields;
import com.aerisweather.aeris.communication.fields.ObservationFields;
import com.aerisweather.aeris.communication.loaders.ObservationsTask;
import com.aerisweather.aeris.communication.loaders.ObservationsTaskCallback;
import com.aerisweather.aeris.communication.parameter.*;
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.model.AerisError;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.example.demoaerisproject.data.room.MyPlace;
import com.example.demoaerisproject.data.weather.model.SunMoonResponse;
import com.google.gson.Gson;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\u0012\u0010\u0017\u001a\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0018\u0010\u001a\u001a\u00020\u00142\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cH\u0016J\u001c\u0010\u001e\u001a\u00020\u00142\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010\u0015\u001a\u0004\u0018\u00010!H\u0016J\u001c\u0010\u001e\u001a\u00020\u00142\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010\u0015\u001a\u0004\u0018\u00010#H\u0016J\u000e\u0010$\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&J\u0016\u0010\'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)J\u000e\u0010+\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&J\u000e\u0010,\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&J\u0012\u0010-\u001a\u00020\u00142\n\b\u0002\u0010.\u001a\u0004\u0018\u00010&J\u000e\u0010/\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&J\u000e\u00100\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&J\u0010\u00101\u001a\u00020\u00142\b\u00102\u001a\u0004\u0018\u000103J\u000e\u00104\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&R\u000e\u0010\b\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u00065"}, d2 = {"Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "Lcom/aerisweather/aeris/communication/BatchCallback;", "Lcom/aerisweather/aeris/communication/AerisCallback;", "Lcom/aerisweather/aeris/communication/CustomCallback;", "Lcom/aerisweather/aeris/communication/loaders/ObservationsTaskCallback;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "NUMBER_OF_DAYS", "", "_batchEvent", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/demoaerisproject/data/weather/AerisBatchResponseEvent;", "batchEvent", "Lkotlinx/coroutines/flow/StateFlow;", "getBatchEvent", "()Lkotlinx/coroutines/flow/StateFlow;", "getContext", "()Landroid/content/Context;", "onBatchResponse", "", "response", "Lcom/aerisweather/aeris/model/AerisBatchResponse;", "onObservationsFailed", "error", "Lcom/aerisweather/aeris/model/AerisError;", "onObservationsLoaded", "responses", "", "Lcom/aerisweather/aeris/response/ObservationResponse;", "onResult", "endpoint", "Lcom/aerisweather/aeris/communication/EndpointType;", "Lcom/aerisweather/aeris/model/AerisResponse;", "custom", "", "requestAirQuality", "placeParam", "Lcom/aerisweather/aeris/communication/parameter/PlaceParameter;", "requestByMapLatLong", "lattitude", "", "longitude", "requestDetailedObservation", "requestExtForecast", "requestForecast4Notification", "place", "requestNearbyObservation", "requestOverview", "requestSunMoon", "myPlace", "Lcom/example/demoaerisproject/data/room/MyPlace;", "requestWeekendForecast", "app_debug"})
public final class WeatherRepository implements com.aerisweather.aeris.communication.BatchCallback, com.aerisweather.aeris.communication.AerisCallback, com.aerisweather.aeris.communication.CustomCallback, com.aerisweather.aeris.communication.loaders.ObservationsTaskCallback {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.demoaerisproject.data.weather.AerisBatchResponseEvent> _batchEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.demoaerisproject.data.weather.AerisBatchResponseEvent> batchEvent = null;
    private final int NUMBER_OF_DAYS = 7;
    
    @javax.inject.Inject()
    public WeatherRepository(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.demoaerisproject.data.weather.AerisBatchResponseEvent> getBatchEvent() {
        return null;
    }
    
    public final void requestAirQuality(@org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.communication.parameter.PlaceParameter placeParam) {
    }
    
    public final void requestExtForecast(@org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.communication.parameter.PlaceParameter placeParam) {
    }
    
    public final void requestNearbyObservation(@org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.communication.parameter.PlaceParameter placeParam) {
    }
    
    public final void requestWeekendForecast(@org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.communication.parameter.PlaceParameter placeParam) {
    }
    
    public final void requestByMapLatLong(double lattitude, double longitude) {
    }
    
    @java.lang.Override()
    public void onObservationsLoaded(@org.jetbrains.annotations.Nullable()
    java.util.List<com.aerisweather.aeris.response.ObservationResponse> responses) {
    }
    
    @java.lang.Override()
    public void onObservationsFailed(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.AerisError error) {
    }
    
    public final void requestOverview(@org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.communication.parameter.PlaceParameter placeParam) {
    }
    
    public final void requestForecast4Notification(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.communication.parameter.PlaceParameter place) {
    }
    
    public final void requestSunMoon(@org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.room.MyPlace myPlace) {
    }
    
    @java.lang.Override()
    public void onResult(@org.jetbrains.annotations.Nullable()
    java.lang.String custom, @org.jetbrains.annotations.Nullable()
    java.lang.String response) {
    }
    
    @java.lang.Override()
    public void onResult(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.communication.EndpointType endpoint, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.AerisResponse response) {
    }
    
    public final void requestDetailedObservation(@org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.communication.parameter.PlaceParameter placeParam) {
    }
    
    @java.lang.Override()
    public void onBatchResponse(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.AerisBatchResponse response) {
    }
}