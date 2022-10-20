package com.example.demoaerisproject.di;

import android.content.Context;
import androidx.room.Room;
import com.example.demoaerisproject.data.room.MyPlaceDao;
import com.example.demoaerisproject.data.room.MyPlaceDatabase;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0012\u0010\n\u001a\u00020\u00062\b\b\u0001\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\r"}, d2 = {"Lcom/example/demoaerisproject/di/RoomModule;", "", "()V", "provideMyPlaceDao", "Lcom/example/demoaerisproject/data/room/MyPlaceDao;", "myPlaceDatabase", "Lcom/example/demoaerisproject/data/room/MyPlaceDatabase;", "provideMyPlaceRepository", "Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "myPlaceDao", "providesMyPlaceDatabase", "appContext", "Landroid/content/Context;", "app_debug"})
@dagger.Module()
public final class RoomModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.di.RoomModule INSTANCE = null;
    
    private RoomModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.demoaerisproject.data.room.MyPlaceRepository provideMyPlaceRepository(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceDao myPlaceDao) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.demoaerisproject.data.room.MyPlaceDao provideMyPlaceDao(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceDatabase myPlaceDatabase) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.example.demoaerisproject.data.room.MyPlaceDatabase providesMyPlaceDatabase(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context appContext) {
        return null;
    }
}