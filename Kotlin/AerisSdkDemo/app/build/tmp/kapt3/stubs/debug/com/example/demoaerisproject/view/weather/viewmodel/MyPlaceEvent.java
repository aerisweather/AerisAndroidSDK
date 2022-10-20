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

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0001\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/MyPlaceEvent;", "", "()V", "Current", "Lcom/example/demoaerisproject/view/weather/viewmodel/MyPlaceEvent$Current;", "app_debug"})
public abstract class MyPlaceEvent {
    
    private MyPlaceEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/MyPlaceEvent$Current;", "Lcom/example/demoaerisproject/view/weather/viewmodel/MyPlaceEvent;", "myPlace", "Lcom/example/demoaerisproject/data/room/MyPlace;", "(Lcom/example/demoaerisproject/data/room/MyPlace;)V", "getMyPlace", "()Lcom/example/demoaerisproject/data/room/MyPlace;", "app_debug"})
    public static final class Current extends com.example.demoaerisproject.view.weather.viewmodel.MyPlaceEvent {
        @org.jetbrains.annotations.Nullable()
        private final com.example.demoaerisproject.data.room.MyPlace myPlace = null;
        
        public Current(@org.jetbrains.annotations.Nullable()
        com.example.demoaerisproject.data.room.MyPlace myPlace) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.example.demoaerisproject.data.room.MyPlace getMyPlace() {
            return null;
        }
    }
}