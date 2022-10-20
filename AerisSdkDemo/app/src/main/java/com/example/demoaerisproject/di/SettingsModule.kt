package com.example.demoaerisproject.di

import android.content.Context
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.view.settings.PrefViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object SettingsModule {

    @Provides
    fun providePreferenceStoreRepository(@ApplicationContext context: Context): PrefStoreRepository =
        PrefStoreRepository(context)

    @Provides
    fun providePrefViewModel(prefStoreRepository: PrefStoreRepository): PrefViewModel =
        PrefViewModel(prefStoreRepository)
}
