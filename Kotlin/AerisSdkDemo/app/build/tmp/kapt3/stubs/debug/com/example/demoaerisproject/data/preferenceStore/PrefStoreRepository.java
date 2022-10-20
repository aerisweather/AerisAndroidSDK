package com.example.demoaerisproject.data.preferenceStore;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u0000 *2\u00020\u0001:\u0001*B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J&\u0010\r\u001a\u0002H\u000e\"\u0006\b\u0000\u0010\u000e\u0018\u00012\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u0002H\u000eH\u0086\b\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u0006J\u0016\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u00132\u0006\u0010\u0015\u001a\u00020\u0006J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00132\u0006\u0010\u0015\u001a\u00020\u0006J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u00132\u0006\u0010\u0015\u001a\u00020\u0006J&\u0010\u001b\u001a\u00020\u0014\"\u0006\b\u0000\u0010\u000e\u0018\u00012\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u0002H\u000eH\u0086\b\u00a2\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u0014H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J!\u0010\"\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$J!\u0010%\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0019H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J!\u0010\'\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010)R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2 = {"Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "PREFS_NAME", "", "STORE_NAME", "getContext", "()Landroid/content/Context;", "prefStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "get", "T", "key", "defaultValue", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "getBoolean", "Lkotlinx/coroutines/flow/Flow;", "", "flag", "getInt", "", "getLong", "", "getString", "set", "value", "(Ljava/lang/String;Ljava/lang/Object;)Z", "setBoolean", "", "isTrue", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setInt", "num", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setLong", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setString", "str", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class PrefStoreRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final java.lang.String STORE_NAME = "aeris_data_store";
    private final java.lang.String PREFS_NAME = "aeris_preference";
    private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> prefStore = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICATION_ENABLED_KEY = "notification_enabled_key";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String UNIT_METRIC_ENABLED_KEY = "unit_metric_enabled_key";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NTF_TIMESTAMP_KEY = "ntf_timestamp_key";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LAST_FRAGMENT_KEY = "last_fragment_key";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String STRING_FLAG = "string_flag";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LONG_FLAG = "long_flag";
    
    @javax.inject.Inject()
    public PrefStoreRepository(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String flag) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String flag, boolean isTrue, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getString(@org.jetbrains.annotations.NotNull()
    java.lang.String flag) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setString(@org.jetbrains.annotations.NotNull()
    java.lang.String flag, @org.jetbrains.annotations.NotNull()
    java.lang.String str, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Long> getLong(@org.jetbrains.annotations.NotNull()
    java.lang.String flag) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setLong(@org.jetbrains.annotations.NotNull()
    java.lang.String flag, long num, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getInt(@org.jetbrains.annotations.NotNull()
    java.lang.String flag) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setInt(@org.jetbrains.annotations.NotNull()
    java.lang.String flag, int num, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository$Companion;", "", "()V", "LAST_FRAGMENT_KEY", "", "LONG_FLAG", "NOTIFICATION_ENABLED_KEY", "NTF_TIMESTAMP_KEY", "STRING_FLAG", "UNIT_METRIC_ENABLED_KEY", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}