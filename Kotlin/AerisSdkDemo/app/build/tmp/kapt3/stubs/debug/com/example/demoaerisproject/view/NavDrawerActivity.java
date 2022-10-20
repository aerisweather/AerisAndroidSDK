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

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JE\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2#\u0010%\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0015\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u000f0\nH\u0007JQ\u0010\'\u001a\u00020\u000f2#\u0010%\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0015\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u000f0\n2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010+2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-H\u0007J2\u0010.\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020/2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0017J=\u00100\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2#\u0010%\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0015\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u000f0\nH\u0017J\u0006\u00101\u001a\u00020\u000fR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR1\u0010\t\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0018\u001a\u00020\u00198FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001a\u0010\u001b\u00a8\u00062"}, d2 = {"Lcom/example/demoaerisproject/view/NavDrawerActivity;", "Landroidx/activity/ComponentActivity;", "()V", "_aerisMapView", "Lcom/aerisweather/aeris/maps/AerisMapView;", "get_aerisMapView", "()Lcom/aerisweather/aeris/maps/AerisMapView;", "set_aerisMapView", "(Lcom/aerisweather/aeris/maps/AerisMapView;)V", "initView", "Lkotlin/Function1;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "Lkotlin/ParameterName;", "name", "callback", "", "getInitView", "()Lkotlin/jvm/functions/Function1;", "requestMultiplePermissions", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "getRequestMultiplePermissions", "()Landroidx/activity/result/ActivityResultLauncher;", "viewModel", "Lcom/example/demoaerisproject/view/weather/viewmodel/WeatherViewModel;", "getViewModel", "()Lcom/example/demoaerisproject/view/weather/viewmodel/WeatherViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "Drawer", "scope", "Lkotlinx/coroutines/CoroutineScope;", "scaffoldState", "Landroidx/compose/material/ScaffoldState;", "navController", "Landroidx/navigation/NavController;", "navigateTo", "route", "MainScreen", "observationEvent", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "unitEvent", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent;", "savedInstanceState", "Landroid/os/Bundle;", "Navigation", "Landroidx/navigation/NavHostController;", "TopBar", "getPermissions", "app_debug"})
public class NavDrawerActivity extends androidx.activity.ComponentActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private com.aerisweather.aeris.maps.AerisMapView _aerisMapView;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.google.android.gms.maps.OnMapReadyCallback, kotlin.Unit> initView = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestMultiplePermissions = null;
    
    public NavDrawerActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.view.weather.viewmodel.WeatherViewModel getViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aerisweather.aeris.maps.AerisMapView get_aerisMapView() {
        return null;
    }
    
    public final void set_aerisMapView(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.maps.AerisMapView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlin.jvm.functions.Function1<com.google.android.gms.maps.OnMapReadyCallback, kotlin.Unit> getInitView() {
        return null;
    }
    
    public final void getPermissions() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> getRequestMultiplePermissions() {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public final void MainScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigateTo, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent observationEvent, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.view.weather.viewmodel.UnitEvent unitEvent, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @androidx.compose.runtime.Composable()
    public void Navigation(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent observationEvent, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.view.weather.viewmodel.UnitEvent unitEvent, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @androidx.compose.runtime.Composable()
    public void TopBar(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope, @org.jetbrains.annotations.NotNull()
    androidx.compose.material.ScaffoldState scaffoldState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigateTo) {
    }
    
    @androidx.compose.runtime.Composable()
    public final void Drawer(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope, @org.jetbrains.annotations.NotNull()
    androidx.compose.material.ScaffoldState scaffoldState, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigateTo) {
    }
}