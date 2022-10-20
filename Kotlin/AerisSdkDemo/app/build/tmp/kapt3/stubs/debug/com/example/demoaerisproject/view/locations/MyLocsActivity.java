package com.example.demoaerisproject.view.locations;

import android.os.Bundle;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.Observer;
import com.example.demoaerisproject.view.BaseActivity;
import com.example.demoaerisproject.R;
import com.example.demoaerisproject.data.room.MyPlace;
import dagger.hilt.android.AndroidEntryPoint;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\tH\u0003J\u0010\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u0005H\u0003J\u0014\u0010\u0017\u001a\u00020\t2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0003J\u0012\u0010\u001a\u001a\u00020\t2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\tH\u0014J\u0010\u0010\u001e\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u001fH\u0002R5\u0010\u0003\u001a\u001d\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006 "}, d2 = {"Lcom/example/demoaerisproject/view/locations/MyLocsActivity;", "Lcom/example/demoaerisproject/view/BaseActivity;", "()V", "onClickPlaceCard", "Lkotlin/Function1;", "Lcom/example/demoaerisproject/data/room/MyPlace;", "Lkotlin/ParameterName;", "name", "place", "", "getOnClickPlaceCard", "()Lkotlin/jvm/functions/Function1;", "setOnClickPlaceCard", "(Lkotlin/jvm/functions/Function1;)V", "viewModel", "Lcom/example/demoaerisproject/view/locations/MyLocationViewModel;", "getViewModel", "()Lcom/example/demoaerisproject/view/locations/MyLocationViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "ComposeDefault", "ComposeMyLocation", "myPlace", "Render", "event", "Lcom/example/demoaerisproject/view/locations/MyLocationEvent$Success;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onViewModelEvent", "Lcom/example/demoaerisproject/view/locations/MyLocationEvent;", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class MyLocsActivity extends com.example.demoaerisproject.view.BaseActivity {
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private kotlin.jvm.functions.Function1<? super com.example.demoaerisproject.data.room.MyPlace, kotlin.Unit> onClickPlaceCard;
    
    public MyLocsActivity() {
        super();
    }
    
    private final com.example.demoaerisproject.view.locations.MyLocationViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void onViewModelEvent(com.example.demoaerisproject.view.locations.MyLocationEvent event) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void Render(com.example.demoaerisproject.view.locations.MyLocationEvent.Success event) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeDefault() {
    }
    
    @androidx.compose.runtime.Composable()
    private final void ComposeMyLocation(com.example.demoaerisproject.data.room.MyPlace myPlace) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function1<com.example.demoaerisproject.data.room.MyPlace, kotlin.Unit> getOnClickPlaceCard() {
        return null;
    }
    
    public final void setOnClickPlaceCard(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.demoaerisproject.data.room.MyPlace, kotlin.Unit> p0) {
    }
}