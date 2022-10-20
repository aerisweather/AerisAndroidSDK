package com.example.demoaerisproject.data.location;

import android.content.Context;
import com.aerisweather.aeris.communication.loaders.PlacesTask;
import com.aerisweather.aeris.communication.loaders.PlacesTaskCallback;
import com.aerisweather.aeris.communication.parameter.ParameterBuilder;
import com.aerisweather.aeris.communication.parameter.PlaceParameter;
import com.aerisweather.aeris.communication.parameter.QueryParameter;
import com.aerisweather.aeris.model.AerisError;
import com.aerisweather.aeris.response.PlacesResponse;
import com.aerisweather.aeris.util.ValidationUtil;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.util.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0015\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aH\u0016J\u0006\u0010\u001c\u001a\u00020\u000fJ\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001e"}, d2 = {"Lcom/example/demoaerisproject/data/location/LocationRepository;", "Lcom/aerisweather/aeris/communication/loaders/PlacesTaskCallback;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_event", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/demoaerisproject/data/location/PlaceResponseEvent;", "getContext", "()Landroid/content/Context;", "event", "Lkotlinx/coroutines/flow/StateFlow;", "getEvent", "()Lkotlinx/coroutines/flow/StateFlow;", "execTaskNearest", "", "text", "", "task", "Lcom/aerisweather/aeris/communication/loaders/PlacesTask;", "execTaskSearch", "onPlacesFailed", "error", "Lcom/aerisweather/aeris/model/AerisError;", "onPlacesLoaded", "responses", "", "Lcom/aerisweather/aeris/response/PlacesResponse;", "requestMyLocation", "requestNearest", "app_debug"})
public final class LocationRepository implements com.aerisweather.aeris.communication.loaders.PlacesTaskCallback {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.demoaerisproject.data.location.PlaceResponseEvent> _event = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.demoaerisproject.data.location.PlaceResponseEvent> event = null;
    
    @javax.inject.Inject()
    public LocationRepository(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.demoaerisproject.data.location.PlaceResponseEvent> getEvent() {
        return null;
    }
    
    public final void requestNearest(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void requestMyLocation() {
    }
    
    @java.lang.Override()
    public void onPlacesLoaded(@org.jetbrains.annotations.Nullable()
    java.util.List<com.aerisweather.aeris.response.PlacesResponse> responses) {
    }
    
    @java.lang.Override()
    public void onPlacesFailed(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.AerisError error) {
    }
    
    private final void execTaskNearest(java.lang.String text, com.aerisweather.aeris.communication.loaders.PlacesTask task) {
    }
    
    private final void execTaskSearch(java.lang.String text, com.aerisweather.aeris.communication.loaders.PlacesTask task) {
    }
}