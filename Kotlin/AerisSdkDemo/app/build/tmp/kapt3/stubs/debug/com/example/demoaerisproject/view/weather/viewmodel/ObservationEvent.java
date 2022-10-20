package com.example.demoaerisproject.view.weather.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.example.demoaerisproject.data.weather.AerisBatchResponseEvent;
import com.example.demoaerisproject.data.weather.WeatherRepository;
import com.example.demoaerisproject.data.weather.model.SunMoonResponse;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "", "()V", "Error", "InProgress", "Map", "Success", "SunMoon", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$Error;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$InProgress;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$Map;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$Success;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$SunMoon;", "app_debug"})
public abstract class ObservationEvent {
    
    private ObservationEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$InProgress;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "()V", "app_debug"})
    public static final class InProgress extends com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent.InProgress INSTANCE = null;
        
        private InProgress() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$Success;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "response", "Lcom/aerisweather/aeris/model/AerisBatchResponse;", "(Lcom/aerisweather/aeris/model/AerisBatchResponse;)V", "getResponse", "()Lcom/aerisweather/aeris/model/AerisBatchResponse;", "app_debug"})
    public static final class Success extends com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent {
        @org.jetbrains.annotations.Nullable()
        private final com.aerisweather.aeris.model.AerisBatchResponse response = null;
        
        public Success(@org.jetbrains.annotations.Nullable()
        com.aerisweather.aeris.model.AerisBatchResponse response) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.aerisweather.aeris.model.AerisBatchResponse getResponse() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$SunMoon;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "response", "Lcom/example/demoaerisproject/data/weather/model/SunMoonResponse;", "(Lcom/example/demoaerisproject/data/weather/model/SunMoonResponse;)V", "getResponse", "()Lcom/example/demoaerisproject/data/weather/model/SunMoonResponse;", "app_debug"})
    public static final class SunMoon extends com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent {
        @org.jetbrains.annotations.Nullable()
        private final com.example.demoaerisproject.data.weather.model.SunMoonResponse response = null;
        
        public SunMoon(@org.jetbrains.annotations.Nullable()
        com.example.demoaerisproject.data.weather.model.SunMoonResponse response) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.example.demoaerisproject.data.weather.model.SunMoonResponse getResponse() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$Map;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "response", "Lcom/aerisweather/aeris/response/ObservationResponse;", "(Lcom/aerisweather/aeris/response/ObservationResponse;)V", "getResponse", "()Lcom/aerisweather/aeris/response/ObservationResponse;", "app_debug"})
    public static final class Map extends com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent {
        @org.jetbrains.annotations.Nullable()
        private final com.aerisweather.aeris.response.ObservationResponse response = null;
        
        public Map(@org.jetbrains.annotations.Nullable()
        com.aerisweather.aeris.response.ObservationResponse response) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.aerisweather.aeris.response.ObservationResponse getResponse() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$Error;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "msg", "", "(Ljava/lang/String;)V", "getMsg", "()Ljava/lang/String;", "app_debug"})
    public static final class Error extends com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String msg = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String msg) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMsg() {
            return null;
        }
    }
}