package com.example.demoaerisproject.di;

import android.content.Context;
import com.example.demoaerisproject.data.location.LocationRepository;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import com.example.demoaerisproject.view.locations.LocationSearchViewModel;
import com.example.demoaerisproject.view.locations.MyLocationViewModel;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@dagger.hilt.InstallIn(value = {dagger.hilt.android.components.ActivityComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/example/demoaerisproject/di/LocationModule;", "", "()V", "provideLocationRepository", "Lcom/example/demoaerisproject/data/location/LocationRepository;", "context", "Landroid/content/Context;", "provideLocationSearchViewModel", "Lcom/example/demoaerisproject/view/locations/LocationSearchViewModel;", "locationRepository", "myPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "provideMyLocationViewModel", "Lcom/example/demoaerisproject/view/locations/MyLocationViewModel;", "app_debug"})
@dagger.Module()
public final class LocationModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.di.LocationModule INSTANCE = null;
    
    private LocationModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.demoaerisproject.data.location.LocationRepository provideLocationRepository(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.demoaerisproject.view.locations.LocationSearchViewModel provideLocationSearchViewModel(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.location.LocationRepository locationRepository, @org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.demoaerisproject.view.locations.MyLocationViewModel provideMyLocationViewModel(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceRepository myPlaceRepository) {
        return null;
    }
}