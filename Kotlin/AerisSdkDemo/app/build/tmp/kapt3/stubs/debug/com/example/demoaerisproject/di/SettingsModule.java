package com.example.demoaerisproject.di;

import android.content.Context;
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import com.example.demoaerisproject.view.settings.PrefViewModel;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@dagger.hilt.InstallIn(value = {dagger.hilt.android.components.ActivityComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\tH\u0007\u00a8\u0006\n"}, d2 = {"Lcom/example/demoaerisproject/di/SettingsModule;", "", "()V", "providePrefViewModel", "Lcom/example/demoaerisproject/view/settings/PrefViewModel;", "prefStoreRepository", "Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "providePreferenceStoreRepository", "context", "Landroid/content/Context;", "app_debug"})
@dagger.Module()
public final class SettingsModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.demoaerisproject.di.SettingsModule INSTANCE = null;
    
    private SettingsModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository providePreferenceStoreRepository(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.demoaerisproject.view.settings.PrefViewModel providePrefViewModel(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository) {
        return null;
    }
}