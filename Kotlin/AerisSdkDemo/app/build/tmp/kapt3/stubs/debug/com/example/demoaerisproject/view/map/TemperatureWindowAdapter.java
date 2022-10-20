package com.example.demoaerisproject.view.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.aerisweather.aeris.maps.AerisMarkerWindow;
import com.aerisweather.aeris.util.FileUtil;
import com.aerisweather.aeris.util.WeatherUtil;
import com.example.demoaerisproject.R;
import com.google.android.gms.maps.model.Marker;

/**
 * Adapter that applies a new layer of tiles over the map.
 *
 * @author Ben Collins
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\nH\u0016J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/example/demoaerisproject/view/map/TemperatureWindowAdapter;", "Lcom/aerisweather/aeris/maps/AerisMarkerWindow;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "inflater", "Landroid/view/LayoutInflater;", "fillView", "", "view", "Landroid/view/View;", "marker", "Lcom/google/android/gms/maps/model/Marker;", "getView", "onInfoWindowPressed", "app_debug"})
public final class TemperatureWindowAdapter extends com.aerisweather.aeris.maps.AerisMarkerWindow {
    
    /**
     * Used to inflate the view.
     */
    private final android.view.LayoutInflater inflater = null;
    
    /**
     * Context necessary for inflating and finding resources.
     */
    private final android.content.Context context = null;
    
    public TemperatureWindowAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.view.View getView() {
        return null;
    }
    
    @java.lang.Override()
    public void fillView(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.Marker marker) {
    }
    
    @java.lang.Override()
    public void onInfoWindowPressed(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.Marker marker) {
    }
}