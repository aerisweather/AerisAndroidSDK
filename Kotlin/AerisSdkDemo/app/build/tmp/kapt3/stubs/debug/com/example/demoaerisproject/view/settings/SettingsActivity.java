package com.example.demoaerisproject.view.settings;

import android.content.Intent;
import android.os.Bundle;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.Observer;
import com.example.demoaerisproject.view.BaseActivity;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.view.map.MyMapActivity;
import com.example.demoaerisproject.view.weather.MainActivity;
import dagger.hilt.android.AndroidEntryPoint;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0003J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0005H\u0003J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\u0012\u0010\u0013\u001a\u00020\u000e2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\u0017\u0010\u0016\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0002\u00a2\u0006\u0002\u0010\u0017J\u0017\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0002\u00a2\u0006\u0002\u0010\u0017J\b\u0010\u0019\u001a\u00020\u000eH\u0014J\b\u0010\u001a\u001a\u00020\u000eH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001b"}, d2 = {"Lcom/example/demoaerisproject/view/settings/SettingsActivity;", "Lcom/example/demoaerisproject/view/BaseActivity;", "()V", "isEnabledNotification", "Landroidx/compose/runtime/MutableState;", "", "isMetric", "viewModel", "Lcom/example/demoaerisproject/view/settings/PrefViewModel;", "getViewModel", "()Lcom/example/demoaerisproject/view/settings/PrefViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "ComposeMetricImperial", "", "isMetrics", "ComposePrefNotification", "isEnabled", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMetricEvent", "(Ljava/lang/Boolean;)V", "onNotificationEvent", "onResume", "render", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class SettingsActivity extends com.example.demoaerisproject.view.BaseActivity {
    private final kotlin.Lazy viewModel$delegate = null;
    private final androidx.compose.runtime.MutableState<java.lang.Boolean> isEnabledNotification = null;
    private final androidx.compose.runtime.MutableState<java.lang.Boolean> isMetric = null;
    
    public SettingsActivity() {
        super();
    }
    
    private final com.example.demoaerisproject.view.settings.PrefViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    private final void onNotificationEvent(java.lang.Boolean isEnabled) {
    }
    
    private final void onMetricEvent(java.lang.Boolean isEnabled) {
    }
    
    private final void render() {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeMetricImperial(boolean isMetrics) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposePrefNotification(boolean isEnabled) {
    }
}