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

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0010\u001a\u00020\u0011H\u0004R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/BaseWeatherViewModel;", "Landroidx/lifecycle/ViewModel;", "weatherRepository", "Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "(Lcom/example/demoaerisproject/data/weather/WeatherRepository;)V", "_event", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "get_event", "()Landroidx/lifecycle/MutableLiveData;", "event", "Landroidx/lifecycle/LiveData;", "getEvent", "()Landroidx/lifecycle/LiveData;", "getWeatherRepository", "()Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "initEventListener", "", "app_debug"})
public class BaseWeatherViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.demoaerisproject.data.weather.WeatherRepository weatherRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent> _event = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent> event = null;
    
    public BaseWeatherViewModel(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.weather.WeatherRepository weatherRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.weather.WeatherRepository getWeatherRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent> get_event() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent> getEvent() {
        return null;
    }
    
    protected final void initEventListener() {
    }
}