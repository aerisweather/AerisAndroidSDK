package com.example.demoaerisproject.data.room;

import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u000b\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u0019\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u0011\u0010\u0016\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"Lcom/example/demoaerisproject/data/room/MyPlaceRepository;", "", "myPlaceDao", "Lcom/example/demoaerisproject/data/room/MyPlaceDao;", "(Lcom/example/demoaerisproject/data/room/MyPlaceDao;)V", "allPlaces", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/demoaerisproject/data/room/MyPlace;", "getAllPlaces", "()Lkotlinx/coroutines/flow/Flow;", "clear", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByName", "selected", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "insert", "myPlace", "(Lcom/example/demoaerisproject/data/room/MyPlace;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetMyLocFalse", "update", "app_debug"})
public final class MyPlaceRepository {
    private final com.example.demoaerisproject.data.room.MyPlaceDao myPlaceDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.example.demoaerisproject.data.room.MyPlace>> allPlaces = null;
    
    @javax.inject.Inject()
    public MyPlaceRepository(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlaceDao myPlaceDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.demoaerisproject.data.room.MyPlace>> getAllPlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.demoaerisproject.data.room.MyPlace> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlace myPlace, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clear(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteByName(@org.jetbrains.annotations.NotNull()
    java.lang.String selected, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.room.MyPlace myPlace, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object resetMyLocFalse(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}