package com.example.demoaerisproject.view.weather.viewmodel;

import android.content.Context;
import android.location.Geocoder;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.aerisweather.aeris.communication.parameter.PlaceParameter;
import com.aerisweather.aeris.location.LocationHelper;
import com.aerisweather.aeris.model.AerisLocation;
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import com.example.demoaerisproject.data.room.MyPlace;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import com.example.demoaerisproject.data.weather.WeatherRepository;
import com.example.demoaerisproject.view.NavDrawerItem;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent;", "", "()V", "Imperial", "Metrics", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent$Imperial;", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent$Metrics;", "app_debug"})
public abstract class UnitEvent {
    
    private UnitEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent$Imperial;", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent;", "()V", "app_debug"})
    public static final class Imperial extends com.example.demoaerisproject.view.weather.viewmodel.UnitEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.weather.viewmodel.UnitEvent.Imperial INSTANCE = null;
        
        private Imperial() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent$Metrics;", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent;", "()V", "app_debug"})
    public static final class Metrics extends com.example.demoaerisproject.view.weather.viewmodel.UnitEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.weather.viewmodel.UnitEvent.Metrics INSTANCE = null;
        
        private Metrics() {
            super();
        }
    }
}