package com.example.demoaerisproject.util;

import android.content.Context;
import com.aerisweather.aeris.util.WeatherUtil;
import com.example.demoaerisproject.R;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J5\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0016\u0010\t\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u00a2\u0006\u0002\u0010\fJ5\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0016\u0010\u000e\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u00a2\u0006\u0002\u0010\fJ5\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0016\u0010\u0010\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u00a2\u0006\u0002\u0010\fJ5\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0016\u0010\u000e\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u00a2\u0006\u0002\u0010\fJ=\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0013\u001a\u00020\u00042\u0016\u0010\u0014\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u00a2\u0006\u0002\u0010\u0015JU\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0013\u001a\u00020\u00042\u0016\u0010\u0017\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\u0016\u0010\u0014\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/example/demoaerisproject/util/FormatUtil;", "", "()V", "printDegree", "", "context", "Landroid/content/Context;", "isMetric", "", "temp", "Lkotlin/Pair;", "", "(Landroid/content/Context;Ljava/lang/Boolean;Lkotlin/Pair;)Ljava/lang/String;", "printPrecip", "precip", "printPressure", "pressure", "printSnow", "printWindSpeed", "windDir", "max", "(Landroid/content/Context;Ljava/lang/Boolean;Ljava/lang/String;Lkotlin/Pair;)Ljava/lang/String;", "printWindSpeedMinMax", "min", "(Landroid/content/Context;Ljava/lang/Boolean;Ljava/lang/String;Lkotlin/Pair;Lkotlin/Pair;)Ljava/lang/String;", "app_debug"})
public final class FormatUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.util.FormatUtil INSTANCE = null;
    
    private FormatUtil() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String printDegree(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isMetric, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<? extends java.lang.Number, ? extends java.lang.Number> temp) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String printWindSpeed(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isMetric, @org.jetbrains.annotations.NotNull()
    java.lang.String windDir, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<? extends java.lang.Number, ? extends java.lang.Number> max) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String printWindSpeedMinMax(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isMetric, @org.jetbrains.annotations.NotNull()
    java.lang.String windDir, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<? extends java.lang.Number, ? extends java.lang.Number> min, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<? extends java.lang.Number, ? extends java.lang.Number> max) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String printSnow(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isMetric, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<? extends java.lang.Number, ? extends java.lang.Number> precip) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String printPrecip(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isMetric, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<? extends java.lang.Number, ? extends java.lang.Number> precip) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String printPressure(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isMetric, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<? extends java.lang.Number, ? extends java.lang.Number> pressure) {
        return null;
    }
}