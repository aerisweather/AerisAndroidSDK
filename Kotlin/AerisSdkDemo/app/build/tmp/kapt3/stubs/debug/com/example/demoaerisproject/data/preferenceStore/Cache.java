package com.example.demoaerisproject.data.preferenceStore;

import java.time.Duration;
import java.time.ZonedDateTime;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0014\u001a\u00020\u0015J\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0017J\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u0007J\u0016\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R(\u0010\u0010\u001a\u001c\u0012\u0004\u0012\u00020\u0007\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/demoaerisproject/data/preferenceStore/Cache;", "", "()V", "EXPIRE_IN_MINUTES", "", "MAX_COUNT", "REQUEST_AIR_QUALITY", "", "REQUEST_DETAILED_OBSERVATION", "REQUEST_EXT_FORECAST", "REQUEST_LAT_LONG", "REQUEST_MY_PLACE", "REQUEST_NEARBY_OBSERVATION", "REQUEST_OVERVIEW", "REQUEST_SUN_MOON", "REQUEST_WEEKEND_FORECAST", "map", "Ljava/util/HashMap;", "Lkotlin/Pair;", "Ljava/time/ZonedDateTime;", "clear", "", "getLastRequestKey", "", "isExpired", "", "request", "(Ljava/lang/String;)Ljava/lang/Boolean;", "remove", "upsert", "obj", "app_debug"})
public final class Cache {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.data.preferenceStore.Cache INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_AIR_QUALITY = "air quality";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_MY_PLACE = "my place";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_LAT_LONG = "lat long";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_OVERVIEW = "overview";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_WEEKEND_FORECAST = "weekend forecast";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_EXT_FORECAST = "ext forecast";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_NEARBY_OBSERVATION = "nearby observation";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_DETAILED_OBSERVATION = "detailed observation";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_SUN_MOON = "sun moon";
    private static java.util.HashMap<java.lang.String, kotlin.Pair<java.time.ZonedDateTime, java.lang.Object>> map;
    private static final int EXPIRE_IN_MINUTES = 10;
    private static final int MAX_COUNT = 10;
    
    private Cache() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean isExpired(@org.jetbrains.annotations.NotNull()
    java.lang.String request) {
        return null;
    }
    
    public final void remove(@org.jetbrains.annotations.NotNull()
    java.lang.String request) {
    }
    
    public final void upsert(@org.jetbrains.annotations.NotNull()
    java.lang.String request, @org.jetbrains.annotations.NotNull()
    java.lang.Object obj) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getLastRequestKey() {
        return null;
    }
    
    public final void clear() {
    }
}