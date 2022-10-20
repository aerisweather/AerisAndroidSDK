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

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/data/location/PlaceResponseEvent;", "", "()V", "Error", "Success", "Lcom/example/demoaerisproject/data/location/PlaceResponseEvent$Error;", "Lcom/example/demoaerisproject/data/location/PlaceResponseEvent$Success;", "app_debug"})
public abstract class PlaceResponseEvent {
    
    private PlaceResponseEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/demoaerisproject/data/location/PlaceResponseEvent$Success;", "Lcom/example/demoaerisproject/data/location/PlaceResponseEvent;", "response", "", "Lcom/aerisweather/aeris/response/PlacesResponse;", "(Ljava/util/List;)V", "getResponse", "()Ljava/util/List;", "app_debug"})
    public static final class Success extends com.example.demoaerisproject.data.location.PlaceResponseEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.aerisweather.aeris.response.PlacesResponse> response = null;
        
        public Success(@org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.aerisweather.aeris.response.PlacesResponse> response) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.aerisweather.aeris.response.PlacesResponse> getResponse() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/data/location/PlaceResponseEvent$Error;", "Lcom/example/demoaerisproject/data/location/PlaceResponseEvent;", "msg", "", "(Ljava/lang/String;)V", "getMsg", "()Ljava/lang/String;", "app_debug"})
    public static final class Error extends com.example.demoaerisproject.data.location.PlaceResponseEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String msg = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String msg) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMsg() {
            return null;
        }
    }
}