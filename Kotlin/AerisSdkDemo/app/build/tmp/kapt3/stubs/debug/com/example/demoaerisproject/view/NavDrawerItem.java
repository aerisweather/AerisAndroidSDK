package com.example.demoaerisproject.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import com.aerisweather.aeris.maps.AerisMapView;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.view.locations.LocationSearchActivity;
import com.example.demoaerisproject.view.locations.MyLocsActivity;
import com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent;
import com.example.demoaerisproject.view.weather.viewmodel.UnitEvent;
import com.example.demoaerisproject.view.weather.viewmodel.WeatherViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\n\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t\u0082\u0001\n\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u00a8\u0006 "}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem;", "", "route", "", "title", "(Ljava/lang/String;Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "setRoute", "(Ljava/lang/String;)V", "getTitle", "setTitle", "AirQuality", "Detailed", "ExtendedForecast", "InteractiveMap", "MyLoc", "NearbyObservation", "Overview", "Search", "SunMoon", "WeekendForecast", "Lcom/example/demoaerisproject/view/NavDrawerItem$AirQuality;", "Lcom/example/demoaerisproject/view/NavDrawerItem$Detailed;", "Lcom/example/demoaerisproject/view/NavDrawerItem$ExtendedForecast;", "Lcom/example/demoaerisproject/view/NavDrawerItem$InteractiveMap;", "Lcom/example/demoaerisproject/view/NavDrawerItem$MyLoc;", "Lcom/example/demoaerisproject/view/NavDrawerItem$NearbyObservation;", "Lcom/example/demoaerisproject/view/NavDrawerItem$Overview;", "Lcom/example/demoaerisproject/view/NavDrawerItem$Search;", "Lcom/example/demoaerisproject/view/NavDrawerItem$SunMoon;", "Lcom/example/demoaerisproject/view/NavDrawerItem$WeekendForecast;", "app_debug"})
public abstract class NavDrawerItem {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String route;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String title;
    
    private NavDrawerItem(java.lang.String route, java.lang.String title) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    public final void setRoute(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    public final void setTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$Search;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class Search extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.Search INSTANCE = null;
        
        private Search() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$MyLoc;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class MyLoc extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.MyLoc INSTANCE = null;
        
        private MyLoc() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$Detailed;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class Detailed extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.Detailed INSTANCE = null;
        
        private Detailed() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$ExtendedForecast;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class ExtendedForecast extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.ExtendedForecast INSTANCE = null;
        
        private ExtendedForecast() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$NearbyObservation;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class NearbyObservation extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.NearbyObservation INSTANCE = null;
        
        private NearbyObservation() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$Overview;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class Overview extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.Overview INSTANCE = null;
        
        private Overview() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$WeekendForecast;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class WeekendForecast extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.WeekendForecast INSTANCE = null;
        
        private WeekendForecast() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$InteractiveMap;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class InteractiveMap extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.InteractiveMap INSTANCE = null;
        
        private InteractiveMap() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$SunMoon;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class SunMoon extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.SunMoon INSTANCE = null;
        
        private SunMoon() {
            super(null, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerItem$AirQuality;", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "()V", "app_debug"})
    public static final class AirQuality extends com.example.demoaerisproject.view.NavDrawerItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.NavDrawerItem.AirQuality INSTANCE = null;
        
        private AirQuality() {
            super(null, null);
        }
    }
}