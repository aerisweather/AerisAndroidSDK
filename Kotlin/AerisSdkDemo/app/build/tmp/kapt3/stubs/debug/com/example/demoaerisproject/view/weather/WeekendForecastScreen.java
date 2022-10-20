package com.example.demoaerisproject.view.weather;

import android.content.Context;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.aerisweather.aeris.model.AerisResponse;
import com.aerisweather.aeris.model.ForecastPeriod;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.util.FileUtil;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.data.weather.model.DayNightPeriod;
import com.example.demoaerisproject.util.FormatUtil;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0003\u00a2\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0011H\u0003J\u0012\u0010\u0012\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0017J\u001c\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u0004\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lcom/example/demoaerisproject/view/weather/WeekendForecastScreen;", "Lcom/example/demoaerisproject/view/weather/IScreen;", "context", "Landroid/content/Context;", "isMetrics", "", "(Landroid/content/Context;Ljava/lang/Boolean;)V", "getContext", "()Landroid/content/Context;", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "ComposeDayOrNight", "", "period", "Lcom/aerisweather/aeris/model/ForecastPeriod;", "(Lcom/aerisweather/aeris/model/ForecastPeriod;Ljava/lang/Boolean;)V", "ComposeListCard", "Lcom/example/demoaerisproject/data/weather/model/DayNightPeriod;", "Render", "list", "", "parseDate", "Lkotlin/Pair;", "", "app_debug"})
public final class WeekendForecastScreen implements com.example.demoaerisproject.view.weather.IScreen {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean isMetrics = null;
    
    public WeekendForecastScreen(@org.jetbrains.annotations.NotNull()
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
    private final void ComposeListCard(com.example.demoaerisproject.data.weather.model.DayNightPeriod period) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeDayOrNight(com.aerisweather.aeris.model.ForecastPeriod period, java.lang.Boolean isMetrics) {
    }
    
    private final kotlin.Pair<java.lang.String, java.lang.String> parseDate(com.aerisweather.aeris.model.ForecastPeriod period) {
        return null;
    }
}