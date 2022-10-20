package com.example.demoaerisproject.view.weather;

import android.content.Intent;
import android.os.Bundle;
import androidx.compose.material.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavHostController;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.view.NavDrawerActivity;
import com.example.demoaerisproject.view.NavDrawerItem;
import com.example.demoaerisproject.view.map.MyMapActivity;
import com.example.demoaerisproject.view.settings.SettingsActivity;
import com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent;
import com.example.demoaerisproject.view.weather.viewmodel.UnitEvent;
import com.google.android.gms.maps.OnMapReadyCallback;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import java.lang.reflect.Type;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0007J.\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0017J=\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2#\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\r\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\t0\u0004H\u0017J\u0012\u0010\"\u001a\u00020\t2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010#\u001a\u00020\tH\u0014R1\u0010\u0003\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0005\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR.\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\r\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b\u00a8\u0006%"}, d2 = {"Lcom/example/demoaerisproject/view/weather/MainActivity;", "Lcom/example/demoaerisproject/view/NavDrawerActivity;", "()V", "initView", "Lkotlin/Function1;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "Lkotlin/ParameterName;", "name", "callback", "", "getInitView", "()Lkotlin/jvm/functions/Function1;", "navigateTo", "", "route", "getNavigateTo", "HandleWeatherEvent", "screen", "Lcom/example/demoaerisproject/view/weather/IScreen;", "event", "Lcom/example/demoaerisproject/view/weather/viewmodel/ObservationEvent;", "Navigation", "navController", "Landroidx/navigation/NavHostController;", "observationEvent", "unitEvent", "Lcom/example/demoaerisproject/view/weather/viewmodel/UnitEvent;", "savedInstanceState", "Landroid/os/Bundle;", "TopBar", "scope", "Lkotlinx/coroutines/CoroutineScope;", "scaffoldState", "Landroidx/compose/material/ScaffoldState;", "onCreate", "onResume", "Companion", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class MainActivity extends com.example.demoaerisproject.view.NavDrawerActivity {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.view.weather.MainActivity.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.reflect.Type TAG = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.google.android.gms.maps.OnMapReadyCallback, kotlin.Unit> initView = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> navigateTo = null;
    
    public MainActivity() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> getNavigateTo() {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public final void HandleWeatherEvent(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.view.weather.IScreen screen, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.view.weather.viewmodel.ObservationEvent event) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/weather/MainActivity$Companion;", "", "()V", "TAG", "Ljava/lang/reflect/Type;", "getTAG", "()Ljava/lang/reflect/Type;", "app_debug"})
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