package com.example.demoaerisproject.view.weather;

import android.content.Context;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.model.ConditionPeriod;
import com.aerisweather.aeris.model.ForecastPeriod;
import com.aerisweather.aeris.response.ConditionsResponse;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.PlacesResponse;
import com.aerisweather.aeris.util.FileUtil;
import com.aerisweather.aeris.util.WeatherUtil;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.data.weather.model.DayNightPeriod;
import com.example.demoaerisproject.util.FormatUtil;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0003J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0003J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0003J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0014H\u0003J(\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u0017H\u0003J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0003J\u0010\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020 H\u0003J\u0012\u0010!\u001a\u00020\f2\b\u0010\u001c\u001a\u0004\u0018\u00010\"H\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u0004\u0010\t\u00a8\u0006#"}, d2 = {"Lcom/example/demoaerisproject/view/weather/DetailedScreen;", "Lcom/example/demoaerisproject/view/weather/IScreen;", "context", "Landroid/content/Context;", "isMetrics", "", "(Landroid/content/Context;Ljava/lang/Boolean;)V", "getContext", "()Landroid/content/Context;", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "ComposeCard", "", "period", "Lcom/aerisweather/aeris/model/ForecastPeriod;", "ComposeCondition", "cond", "Lcom/aerisweather/aeris/model/ConditionPeriod;", "ComposeDayOrNight", "ComposeFullDay", "Lcom/example/demoaerisproject/data/weather/model/DayNightPeriod;", "ComposeGrid", "c1", "", "c2", "c3", "c4", "ComposeListCard", "list", "Lcom/aerisweather/aeris/response/ForecastsResponse;", "ComposePlace", "place", "Lcom/aerisweather/aeris/response/PlacesResponse;", "Render", "", "app_debug"})
public final class DetailedScreen implements com.example.demoaerisproject.view.weather.IScreen {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean isMetrics = null;
    
    public DetailedScreen(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isMetrics) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean isMetrics() {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    @java.lang.Override()
    public void Render(@org.jetbrains.annotations.Nullable()
    java.lang.Object list) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeFullDay(com.example.demoaerisproject.data.weather.model.DayNightPeriod period) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeDayOrNight(com.aerisweather.aeris.model.ForecastPeriod period) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeGrid(java.lang.String c1, java.lang.String c2, java.lang.String c3, java.lang.String c4) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposePlace(com.aerisweather.aeris.response.PlacesResponse place) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeCondition(com.aerisweather.aeris.model.ConditionPeriod cond) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeListCard(com.aerisweather.aeris.response.ForecastsResponse list) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeCard(com.aerisweather.aeris.model.ForecastPeriod period) {
    }
}