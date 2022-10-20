package com.example.demoaerisproject.view.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.Toast;
import androidx.compose.material.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavHostController;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.location.LocationHelper;
import com.aerisweather.aeris.maps.AerisMapContainerView;
import com.aerisweather.aeris.maps.AerisMapOptions;
import com.aerisweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.aerisweather.aeris.maps.interfaces.OnAerisMarkerInfoWindowClickListener;
import com.aerisweather.aeris.maps.markers.AerisMarker;
import com.aerisweather.aeris.model.AerisPermissions;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.response.*;
import com.aerisweather.aeris.tiles.*;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.data.room.MyPlace;
import com.example.demoaerisproject.util.FormatUtil;
import com.example.demoaerisproject.view.NavDrawerActivity;
import com.example.demoaerisproject.view.NavDrawerItem;
import com.example.demoaerisproject.view.settings.SettingsActivity;
import com.example.demoaerisproject.view.weather.MainActivity;
import com.example.demoaerisproject.view.weather.viewmodel.MyPlaceEvent;
import com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent;
import com.example.demoaerisproject.view.weather.viewmodel.UnitEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import dagger.hilt.android.AndroidEntryPoint;
import java.lang.reflect.Type;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00da\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 X2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0002XYB\u0005\u00a2\u0006\u0002\u0010\u0005J\u0012\u0010%\u001a\u00020\u00132\b\u0010&\u001a\u0004\u0018\u00010\'H\u0007J.\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010-\u001a\u0004\u0018\u00010.2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0017J=\u0010/\u001a\u00020\u00132\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032#\u0010!\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\"\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u00130\u000fH\u0017J\u001c\u00104\u001a\u00020\u00132\b\u00105\u001a\u0004\u0018\u0001062\b\u0010\u001d\u001a\u0004\u0018\u000107H\u0016J\b\u00108\u001a\u00020\u0013H\u0002J\u0010\u00109\u001a\u00020\u00132\u0006\u0010:\u001a\u00020;H\u0002J\u0012\u0010<\u001a\u00020\u00132\b\u0010&\u001a\u0004\u0018\u00010\'H\u0014J\b\u0010=\u001a\u00020\u0013H\u0014J\u0010\u0010>\u001a\u00020\u00132\u0006\u0010:\u001a\u00020?H\u0002J\b\u0010@\u001a\u00020\u0013H\u0016J\u0018\u0010A\u001a\u00020\u00132\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020CH\u0016J\u0010\u0010E\u001a\u00020\u00132\u0006\u0010F\u001a\u00020\u000bH\u0016J\u0010\u0010G\u001a\u00020\u00132\u0006\u0010:\u001a\u00020,H\u0002J\b\u0010H\u001a\u00020\u0013H\u0014J\u0016\u0010I\u001a\u00020\u00132\u0006\u0010J\u001a\u00020K2\u0006\u00105\u001a\u00020LJ\b\u0010M\u001a\u00020\u0013H\u0014J\u0010\u0010N\u001a\u00020\u00132\u0006\u0010O\u001a\u00020.H\u0002J\u001c\u0010P\u001a\u00020\u00132\b\u00105\u001a\u0004\u0018\u00010Q2\b\u0010\u001d\u001a\u0004\u0018\u000107H\u0016J\u001c\u0010R\u001a\u00020\u00132\b\u00105\u001a\u0004\u0018\u00010S2\b\u0010\u001d\u001a\u0004\u0018\u000107H\u0016J\u001c\u0010T\u001a\u00020\u00132\b\u00105\u001a\u0004\u0018\u00010U2\b\u0010\u001d\u001a\u0004\u0018\u000107H\u0016J\u001c\u0010V\u001a\u00020\u00132\b\u00105\u001a\u0004\u0018\u00010W2\b\u0010\u001d\u001a\u0004\u0018\u000107H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R1\u0010\u000e\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0004\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00130\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010!\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\"\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u00130\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015\u00a8\u0006Z"}, d2 = {"Lcom/example/demoaerisproject/view/map/MyMapActivity;", "Lcom/example/demoaerisproject/view/NavDrawerActivity;", "Lcom/aerisweather/aeris/maps/interfaces/OnAerisMapLongClickListener;", "Lcom/aerisweather/aeris/maps/interfaces/OnAerisMarkerInfoWindowClickListener;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "aerisAmp", "Lcom/aerisweather/aeris/tiles/AerisAmp;", "attributeSet", "Landroid/util/AttributeSet;", "googleMap", "Lcom/google/android/gms/maps/GoogleMap;", "infoAdapter", "Lcom/example/demoaerisproject/view/map/TemperatureWindowAdapter;", "initView", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "callback", "", "getInitView", "()Lkotlin/jvm/functions/Function1;", "isAmpReady", "", "isMapReady", "isMetrics", "Ljava/lang/Boolean;", "mapOptions", "Lcom/aerisweather/aeris/maps/AerisMapOptions;", "marker", "Lcom/google/android/gms/maps/model/Marker;", "myPlace", "Lcom/example/demoaerisproject/data/room/MyPlace;", "navigateTo", "", "route", "getNavigateTo", "ComposeMap", "savedInstanceState", "Landroid/os/Bundle;", "Navigation", "navController", "Landroidx/navigation/NavHostController;", "observationEvent", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "unitEvent", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent;", "TopBar", "scope", "Lkotlinx/coroutines/CoroutineScope;", "scaffoldState", "Landroidx/compose/material/ScaffoldState;", "earthquakeWindowPressed", "response", "Lcom/aerisweather/aeris/response/EarthquakesResponse;", "Lcom/aerisweather/aeris/maps/markers/AerisMarker;", "initMap", "loadObservation", "event", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent$Map;", "onCreate", "onDestroy", "onLocationEvent", "Lcom/example/demoaerisproject/view/weather/viewmodel/MyPlaceEvent;", "onLowMemory", "onMapLongClick", "lat", "", "longitude", "onMapReady", "map", "onObservationEvent", "onPause", "onResult", "type", "Lcom/aerisweather/aeris/communication/EndpointType;", "Lcom/aerisweather/aeris/model/AerisResponse;", "onResume", "onUnitEvent", "unit", "recordsWindowPressed", "Lcom/aerisweather/aeris/response/RecordsResponse;", "stormCellsWindowPressed", "Lcom/aerisweather/aeris/response/StormCellResponse;", "stormReportsWindowPressed", "Lcom/aerisweather/aeris/response/StormReportsResponse;", "wildfireWindowPressed", "Lcom/aerisweather/aeris/response/FiresResponse;", "Companion", "GetLayersTaskCallback", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class MyMapActivity extends com.example.demoaerisproject.view.NavDrawerActivity implements com.aerisweather.aeris.maps.interfaces.OnAerisMapLongClickListener, com.aerisweather.aeris.maps.interfaces.OnAerisMarkerInfoWindowClickListener, com.google.android.gms.maps.OnMapReadyCallback {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.view.map.MyMapActivity.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.reflect.Type TAG = null;
    private com.google.android.gms.maps.model.Marker marker;
    private com.aerisweather.aeris.tiles.AerisAmp aerisAmp;
    private boolean isMapReady = false;
    private boolean isAmpReady = false;
    private com.aerisweather.aeris.maps.AerisMapOptions mapOptions;
    private com.google.android.gms.maps.GoogleMap googleMap;
    private com.example.demoaerisproject.view.map.TemperatureWindowAdapter infoAdapter;
    private com.example.demoaerisproject.data.room.MyPlace myPlace;
    private java.lang.Boolean isMetrics;
    private android.util.AttributeSet attributeSet;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.google.android.gms.maps.OnMapReadyCallback, kotlin.Unit> initView = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> navigateTo = null;
    
    public MyMapActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlin.jvm.functions.Function1<com.google.android.gms.maps.OnMapReadyCallback, kotlin.Unit> getInitView() {
        return null;
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @androidx.compose.runtime.Composable()
    @java.lang.Override()
    public void Navigation(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent observationEvent, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.view.weather.viewmodel.UnitEvent unitEvent, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @androidx.compose.runtime.Composable()
    @java.lang.Override()
    public void TopBar(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope, @org.jetbrains.annotations.NotNull()
    androidx.compose.material.ScaffoldState scaffoldState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigateTo) {
    }
    
    @androidx.compose.runtime.Composable()
    public final void ComposeMap(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> getNavigateTo() {
        return null;
    }
    
    private final void onUnitEvent(com.example.demoaerisproject.view.weather.viewmodel.UnitEvent unit) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map) {
    }
    
    /**
     * Initializes the map with specific settings
     */
    private final void initMap() {
    }
    
    private final void onLocationEvent(com.example.demoaerisproject.view.weather.viewmodel.MyPlaceEvent event) {
    }
    
    private final void onObservationEvent(com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent event) {
    }
    
    private final void loadObservation(com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent.Map event) {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    public void onLowMemory() {
    }
    
    @java.lang.Override()
    public void onMapLongClick(double lat, double longitude) {
    }
    
    public final void onResult(@org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.communication.EndpointType type, @org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.model.AerisResponse response) {
    }
    
    @java.lang.Override()
    public void earthquakeWindowPressed(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.response.EarthquakesResponse response, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.maps.markers.AerisMarker marker) {
    }
    
    @java.lang.Override()
    public void stormReportsWindowPressed(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.response.StormReportsResponse response, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.maps.markers.AerisMarker marker) {
    }
    
    @java.lang.Override()
    public void stormCellsWindowPressed(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.response.StormCellResponse response, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.maps.markers.AerisMarker marker) {
    }
    
    @java.lang.Override()
    public void wildfireWindowPressed(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.response.FiresResponse response, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.maps.markers.AerisMarker marker) {
    }
    
    @java.lang.Override()
    public void recordsWindowPressed(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.response.RecordsResponse response, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.maps.markers.AerisMarker marker) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\u000b"}, d2 = {"Lcom/example/demoaerisproject/view/map/MyMapActivity$GetLayersTaskCallback;", "Lcom/aerisweather/aeris/tiles/AerisAmpOnGetLayersTaskCompleted;", "(Lcom/example/demoaerisproject/view/map/MyMapActivity;)V", "onAerisAmpGetLayersTaskCompleted", "", "permissibleLayers", "Ljava/util/ArrayList;", "Lcom/aerisweather/aeris/tiles/AerisAmpLayer;", "Lkotlin/collections/ArrayList;", "permissions", "Lcom/aerisweather/aeris/model/AerisPermissions;", "app_debug"})
    public final class GetLayersTaskCallback implements com.aerisweather.aeris.tiles.AerisAmpOnGetLayersTaskCompleted {
        
        public GetLayersTaskCallback() {
            super();
        }
        
        @java.lang.Override()
        public void onAerisAmpGetLayersTaskCompleted(@org.jetbrains.annotations.NotNull()
        java.util.ArrayList<com.aerisweather.aeris.tiles.AerisAmpLayer> permissibleLayers, @org.jetbrains.annotations.NotNull()
        com.aerisweather.aeris.model.AerisPermissions permissions) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/map/MyMapActivity$Companion;", "", "()V", "TAG", "Ljava/lang/reflect/Type;", "getTAG", "()Ljava/lang/reflect/Type;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.reflect.Type getTAG() {
            return null;
        }
    }
}