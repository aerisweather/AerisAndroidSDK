package com.xweather.sdkdemo.kotlin.di

import android.content.Context
import com.xweather.sdkdemo.kotlin.data.preferenceStore.PrefStoreRepository
import com.xweather.sdkdemo.kotlin.view.settings.PrefViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    fun providePreferenceStoreRepository(@ApplicationContext context: Context): PrefStoreRepository =
        PrefStoreRepository(context)

    @Provides
    fun providePrefViewModel(prefStoreRepository: PrefStoreRepository): PrefViewModel =
        PrefViewModel(prefStoreRepository)
}
