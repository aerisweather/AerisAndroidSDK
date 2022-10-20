package com.example.demoaerisproject.view.locations;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.aerisweather.aeris.response.PlacesResponse;
import com.example.demoaerisproject.data.location.LocationRepository;
import com.example.demoaerisproject.data.location.PlaceResponseEvent;
import com.example.demoaerisproject.data.room.MyPlace;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.FlowPreview;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/example/demoaerisproject/view/locations/SearchEvent;", "", "()V", "Error", "InProgress", "Success", "Lcom/example/demoaerisproject/view/locations/SearchEvent$Error;", "Lcom/example/demoaerisproject/view/locations/SearchEvent$InProgress;", "Lcom/example/demoaerisproject/view/locations/SearchEvent$Success;", "app_debug"})
public abstract class SearchEvent {
    
    private SearchEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/demoaerisproject/view/locations/SearchEvent$InProgress;", "Lcom/example/demoaerisproject/view/locations/SearchEvent;", "()V", "app_debug"})
    public static final class InProgress extends com.example.demoaerisproject.view.locations.SearchEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.demoaerisproject.view.locations.SearchEvent.InProgress INSTANCE = null;
        
        private InProgress() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/demoaerisproject/view/locations/SearchEvent$Success;", "Lcom/example/demoaerisproject/view/locations/SearchEvent;", "response", "", "Lcom/aerisweather/aeris/response/PlacesResponse;", "(Ljava/util/List;)V", "getResponse", "()Ljava/util/List;", "app_debug"})
    public static final class Success extends com.example.demoaerisproject.view.locations.SearchEvent {
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
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/demoaerisproject/view/locations/SearchEvent$Error;", "Lcom/example/demoaerisproject/view/locations/SearchEvent;", "msg", "", "(Ljava/lang/String;)V", "getMsg", "()Ljava/lang/String;", "app_debug"})
    public static final class Error extends com.example.demoaerisproject.view.locations.SearchEvent {
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