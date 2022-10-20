package com.example.demoaerisproject.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.aerisweather.aeris.logging.Logger;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.ObservationResponse;
import com.aerisweather.aeris.util.FileUtil;
import com.example.demoaerisproject.BaseApplication;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.util.FormatUtil;
import com.example.demoaerisproject.view.weather.MainActivity;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0017\u001a\u00020\f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0002J&\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0007*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R8\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\f0\u000b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\f0\u000b8F@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/demoaerisproject/service/AerisNotification;", "", "()V", "CHANNEL_ID", "", "CHANNEL_NAME", "TAG", "kotlin.jvm.PlatformType", "builder", "Landroidx/core/app/NotificationCompat$Builder;", "<set-?>", "Ljava/util/HashMap;", "", "map", "getMap", "()Ljava/util/HashMap;", "remoteViews", "Landroid/widget/RemoteViews;", "cancel", "", "context", "Landroid/content/Context;", "createNotifChannel", "getDrawableByName", "name", "setCustom", "isMetrics", "", "obResponse", "Lcom/aerisweather/aeris/response/ObservationResponse;", "fResponse", "Lcom/aerisweather/aeris/response/ForecastsResponse;", "app_debug"})
public final class AerisNotification {
    private androidx.core.app.NotificationCompat.Builder builder;
    private android.widget.RemoteViews remoteViews;
    @org.jetbrains.annotations.NotNull()
    private java.util.HashMap<java.lang.String, java.lang.Integer> map;
    private final java.lang.String TAG = null;
    
    /**
     * Sets the notification for the observation
     */
    private final java.lang.String CHANNEL_ID = "channelID";
    private final java.lang.String CHANNEL_NAME = "channelName";
    
    public AerisNotification() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.HashMap<java.lang.String, java.lang.Integer> getMap() {
        return null;
    }
    
    public final void setCustom(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean isMetrics, @org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.response.ObservationResponse obResponse, @org.jetbrains.annotations.NotNull()
    com.aerisweather.aeris.response.ForecastsResponse fResponse) {
    }
    
    private final void createNotifChannel(android.content.Context context) {
    }
    
    public final void cancel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * Gets the drawable by the string name. This is used in conjunction with
     * the icon field in many of the forecast, observation. Accesses the name
     * from the drawable folder.
     *
     * @param name name of the drawable
     * @return the int key of the drawable.
     */
    private final int getDrawableByName(java.lang.String name) {
        return 0;
    }
}