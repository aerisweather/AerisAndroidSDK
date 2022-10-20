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

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u00a8\u0006\b"}, d2 = {"DrawerItem", "", "item", "Lcom/example/demoaerisproject/view/NavDrawerItem;", "selected", "", "onItemClick", "Lkotlin/Function1;", "app_debug"})
public final class NavDrawerActivityKt {
    
    @androidx.compose.runtime.Composable()
    public static final void DrawerItem(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.view.NavDrawerItem item, boolean selected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.demoaerisproject.view.NavDrawerItem, kotlin.Unit> onItemClick) {
    }
}