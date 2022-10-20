package com.example.demoaerisproject.view.locations;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.demoaerisproject.data.room.MyPlace;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/demoaerisproject/view/locations/MyLocationViewModel;", "Landroidx/lifecycle/ViewModel;", "myPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "(Lcom/example/demoaerisproject/data/room/MyPlaceRepository;)V", "_event", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/demoaerisproject/view/locations/MyLocationEvent;", "get_event", "()Landroidx/lifecycle/MutableLiveData;", "event", "Landroidx/lifecycle/LiveData;", "getEvent", "()Landroidx/lifecycle/LiveData;", "initDbListener", "", "selectAsMyLocation", "myPlace", "Lcom/example/demoaerisproject/data/room/MyPlace;", "app_debug"})
public final class MyLocationViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.locations.MyLocationEvent> _event = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.locations.MyLocationEvent> event = null;
    
    @javax.inject.Inject()
    public MyLocationViewModel(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final androidx.lifecycle.MutableLiveData<com.example.demoaerisproject.view.locations.MyLocationEvent> get_event() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.demoaerisproject.view.locations.MyLocationEvent> getEvent() {
        return null;
    }
    
    public final void initDbListener() {
    }
    
    public final void selectAsMyLocation(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlace myPlace) {
    }
}