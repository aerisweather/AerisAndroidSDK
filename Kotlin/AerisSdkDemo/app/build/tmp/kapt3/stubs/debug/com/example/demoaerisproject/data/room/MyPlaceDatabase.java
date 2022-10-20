package com.example.demoaerisproject.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {com.example.demoaerisproject.data.room.MyPlace.class}, version = 1, exportSchema = false)
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lcom/example/demoaerisproject/data/room/MyPlaceDatabase;", "Landroidx/room/RoomDatabase;", "()V", "myPlaceDao", "Lcom/example/demoaerisproject/data/room/MyPlaceDao;", "app_debug"})
public abstract class MyPlaceDatabase extends androidx.room.RoomDatabase {
    
    public MyPlaceDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.demoaerisproject.data.room.MyPlaceDao myPlaceDao();
}