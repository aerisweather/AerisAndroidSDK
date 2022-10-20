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

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u00020\u0011H\u0004J\u0006\u0010\u0015\u001a\u00020\u0011J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0018R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/demoaerisproject/view/locations/LocationSearchViewModel;", "Landroidx/lifecycle/ViewModel;", "locationRepository", "Lcom/example/demoaerisproject/data/location/LocationRepository;", "myPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "(Lcom/example/demoaerisproject/data/location/LocationRepository;Lcom/example/demoaerisproject/data/room/MyPlaceRepository;)V", "_event", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/demoaerisproject/view/locations/SearchEvent;", "get_event", "()Landroidx/lifecycle/MutableLiveData;", "event", "Landroidx/lifecycle/LiveData;", "getEvent", "()Landroidx/lifecycle/LiveData;", "addLocation", "", "myPlace", "Lcom/example/demoaerisproject/data/room/MyPlace;", "initEventListener", "locateMe", "search", "text", "", "app_debug"})
public final class LocationSearchViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.demoaerisproject.data.location.LocationRepository locationRepository = null;
    private final com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.locations.SearchEvent> _event = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.locations.SearchEvent> event = null;
    
    @javax.inject.Inject()
    public LocationSearchViewModel(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.location.LocationRepository locationRepository, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.locations.SearchEvent> get_event() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.locations.SearchEvent> getEvent() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.FlowPreview.class})
    protected final void initEventListener() {
    }
    
    public final void locateMe() {
    }
    
    public final void search(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void addLocation(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlace myPlace) {
    }
}