package com.example.demoaerisproject.view.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007J\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007R\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\bR\u0019\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/demoaerisproject/view/settings/PrefViewModel;", "Landroidx/lifecycle/ViewModel;", "prefStoreRepository", "Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;", "(Lcom/example/demoaerisproject/data/preferenceStore/PrefStoreRepository;)V", "isMetric", "Landroidx/lifecycle/LiveData;", "", "()Landroidx/lifecycle/LiveData;", "isNotificationEnabled", "setMetric", "", "isEnabled", "setNotificationEnabled", "app_debug"})
public final class PrefViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isNotificationEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isMetric = null;
    
    @javax.inject.Inject()
    public PrefViewModel(@org.jetbrains.annotations.NotNull()
    com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository prefStoreRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isNotificationEnabled() {
        return null;
    }
    
    public final void setNotificationEnabled(boolean isEnabled) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isMetric() {
        return null;
    }
    
    public final void setMetric(boolean isEnabled) {
    }
}