package com.example.demoaerisproject.view.locations;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.window.DialogProperties;
import androidx.lifecycle.Observer;
import com.aerisweather.aeris.response.PlacesResponse;
import com.example.demoaerisproject.view.BaseActivity;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.data.room.MyPlace;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0007H\u0003J\u0014\u0010\u001a\u001a\u00020\u000b2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0003J\u0012\u0010\u001d\u001a\u00020\u000b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\u0012\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u0010\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010\'\u001a\u00020\u000bH\u0014J\u0010\u0010(\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020)H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R7\u0010\u0005\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0007\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006*"}, d2 = {"Lcom/example/demoaerisproject/view/locations/LocationSearchActivity;", "Lcom/example/demoaerisproject/view/BaseActivity;", "()V", "DEBOUNCE_LIMIT", "", "onDlgConfirm", "Lkotlin/Function1;", "Lcom/aerisweather/aeris/response/PlacesResponse;", "Lkotlin/ParameterName;", "name", "myPlace", "", "getOnDlgConfirm", "()Lkotlin/jvm/functions/Function1;", "setOnDlgConfirm", "(Lkotlin/jvm/functions/Function1;)V", "outlineTextValue", "", "viewModel", "Lcom/example/demoaerisproject/view/locations/LocationSearchViewModel;", "getViewModel", "()Lcom/example/demoaerisproject/view/locations/LocationSearchViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "ComposeListPlace", "place", "Render", "event", "Lcom/example/demoaerisproject/view/locations/SearchEvent$Success;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onResume", "onViewModelEvent", "Lcom/example/demoaerisproject/view/locations/SearchEvent;", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class LocationSearchActivity extends com.example.demoaerisproject.view.BaseActivity {
    private final kotlin.Lazy viewModel$delegate = null;
    private final long DEBOUNCE_LIMIT = 1500L;
    private java.lang.String outlineTextValue = "";
    @org.jetbrains.annotations.NotNull()
    private kotlin.jvm.functions.Function1<? super com.aerisweather.aeris.response.PlacesResponse, kotlin.Unit> onDlgConfirm;
    
    public LocationSearchActivity() {
        super();
    }
    
    private final com.example.demoaerisproject.view.locations.LocationSearchViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void onViewModelEvent(com.example.demoaerisproject.view.locations.SearchEvent event) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void Render(com.example.demoaerisproject.view.locations.SearchEvent.Success event) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeListPlace(com.aerisweather.aeris.response.PlacesResponse place) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function1<com.aerisweather.aeris.response.PlacesResponse, kotlin.Unit> getOnDlgConfirm() {
        return null;
    }
    
    public final void setOnDlgConfirm(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.aerisweather.aeris.response.PlacesResponse, kotlin.Unit> p0) {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.Nullable()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
}