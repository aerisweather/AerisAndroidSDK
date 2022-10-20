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

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\t\b\u0017\u0018\u00002\u00020\u0001B)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0012\u0010+\u001a\u00020 2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J\b\u0010,\u001a\u00020\u0014H\u0002J\b\u0010-\u001a\u00020\u0014H\u0002J\u0006\u0010.\u001a\u00020\u0014J\u0016\u0010/\u001a\u00020\u00142\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000201J\u0006\u00103\u001a\u00020\u0014J\u0006\u00104\u001a\u00020\u0014J\u0006\u00105\u001a\u00020\u0014J\u0006\u00106\u001a\u00020\u0014J\u0006\u00107\u001a\u00020\u0014J\u0006\u00108\u001a\u00020\u0014J\u0006\u00109\u001a\u00020\u0014R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u001c8F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001f\u001a\u00020 8F\u00a2\u0006\u0006\u001a\u0004\b!\u0010\"R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010#\u001a\u00020$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001a\u00a8\u0006:"}, d2 = {"Lcom/example/demoaerisproject/view/weather/viewmodel/WeatherViewModel;", "Lcom/example/demoaerisproject/view/weather/viewmodel/BaseWeatherViewModel;", "context", "Landroid/content/Context;", "weatherRepository", "Lcom/example/demoaerisproject/data/weather/WeatherRepository;", "myPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "prefStoreRepository", "Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "(Landroid/content/Context;Lcom/example/demoaerisproject/data/weather/WeatherRepository;Lcom/example/demoaerisproject/data/room/MyPlaceRepository;Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;)V", "_locationEvent", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/demoaerisproject/view/weather/viewmodel/MyPlaceEvent;", "_unitEvent", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent;", "getContext", "()Landroid/content/Context;", "execLocationHelper", "Lkotlin/Function0;", "", "getExecLocationHelper", "()Lkotlin/jvm/functions/Function0;", "locationEvent", "Landroidx/lifecycle/LiveData;", "getLocationEvent", "()Landroidx/lifecycle/LiveData;", "myPlace", "Lcom/example/demoaerisproject/data/room/MyPlace;", "getMyPlace", "()Lcom/example/demoaerisproject/data/room/MyPlace;", "placeParam", "Lcom/aerisweather/aeris/communication/parameter/PlaceParameter;", "getPlaceParam", "()Lcom/aerisweather/aeris/communication/parameter/PlaceParameter;", "route", "", "getRoute", "()Ljava/lang/String;", "setRoute", "(Ljava/lang/String;)V", "unitEvent", "getUnitEvent", "getPlaceParameter", "initDbListener", "initUnitListener", "requestAirQuality", "requestByMapLatLong", "lat", "", "longitude", "requestDetailedObservation", "requestExtForecast", "requestMyPlace", "requestNearbyObservation", "requestOverview", "requestSunMoon", "requestWeekendForecast", "app_debug"})
public class WeatherViewModel extends com.example.demoaerisproject.view.weather.viewmodel.BaseWeatherViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository = null;
    private final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository = null;
    private final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.weather.viewmodel.MyPlaceEvent> _locationEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.weather.viewmodel.MyPlaceEvent> locationEvent = null;
    private final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.weather.viewmodel.UnitEvent> _unitEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.weather.viewmodel.UnitEvent> unitEvent = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String route;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> execLocationHelper = null;
    
    @javax.inject.Inject()
    public WeatherViewModel(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.weather.WeatherRepository weatherRepository, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.weather.viewmodel.MyPlaceEvent> getLocationEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.weather.viewmodel.UnitEvent> getUnitEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    public final void setRoute(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.demoaerisproject.data.room.MyPlace getMyPlace() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.aerisweather.aeris.communication.parameter.PlaceParameter getPlaceParam() {
        return null;
    }
    
    public final void requestMyPlace() {
    }
    
    public final void requestByMapLatLong(double lat, double longitude) {
    }
    
    public final void requestOverview() {
    }
    
    public final void requestWeekendForecast() {
    }
    
    public final void requestAirQuality() {
    }
    
    public final void requestExtForecast() {
    }
    
    public final void requestNearbyObservation() {
    }
    
    public final void requestDetailedObservation() {
    }
    
    public final void requestSunMoon() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function0<kotlin.Unit> getExecLocationHelper() {
        return null;
    }
    
    private final void initUnitListener() {
    }
    
    private final com.aerisweather.aeris.communication.parameter.PlaceParameter getPlaceParameter(com.example.demoaerisproject.data.room.MyPlace myPlace) {
        return null;
    }
    
    private final void initDbListener() {
    }
}