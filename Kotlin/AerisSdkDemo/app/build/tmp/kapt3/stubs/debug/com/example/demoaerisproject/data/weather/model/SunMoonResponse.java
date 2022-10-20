package com.example.demoaerisproject.data.weather.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/example/demoaerisproject/data/weather/model/SunMoonResponse;", "", "success", "", "error", "Lcom/example/demoaerisproject/data/weather/model/Error;", "response", "", "Lcom/example/demoaerisproject/data/weather/model/SunMoon;", "(ZLcom/example/demoaerisproject/data/weather/model/Error;Ljava/util/List;)V", "getError", "()Lcom/example/demoaerisproject/data/weather/model/Error;", "getResponse", "()Ljava/util/List;", "getSuccess", "()Z", "app_debug"})
public final class SunMoonResponse {
    private final boolean success = false;
    @org.jetbrains.annotations.Nullable()
    private final com.example.demoaerisproject.data.weather.model.Error error = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.demoaerisproject.data.weather.model.SunMoon> response = null;
    
    public SunMoonResponse(boolean success, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.weather.model.Error error, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.demoaerisproject.data.weather.model.SunMoon> response) {
        super();
    }
    
    public final boolean getSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.demoaerisproject.data.weather.model.Error getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.demoaerisproject.data.weather.model.SunMoon> getResponse() {
        return null;
    }
}