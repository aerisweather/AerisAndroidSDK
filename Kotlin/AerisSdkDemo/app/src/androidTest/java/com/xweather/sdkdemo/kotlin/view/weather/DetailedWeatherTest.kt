package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.xweather.sdkdemo.kotlin.data.preferenceStore.PrefStoreRepository
import com.xweather.sdkdemo.kotlin.data.room.MyPlaceRepository
import com.xweather.sdkdemo.kotlin.data.weather.TestWeatherRepository
import com.xweather.sdkdemo.kotlin.data.weather.WeatherRepository
import com.xweather.sdkdemo.kotlin.di.WeatherModule
import com.xweather.sdkdemo.kotlin.view.viewmodel.TestWeatherViewModel
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Test

@UninstallModules(WeatherModule::class)
@HiltAndroidTest
class DetailedWeatherTest : BaseMainActivityTest() {
    @Module
    @InstallIn(SingletonComponent::class)
    object TestWeatherModule {
        @Provides
        fun provideWeatherRepository(@ApplicationContext context: Context): TestWeatherRepository =
            TestWeatherRepository(context)

        @Provides
        fun provideWeatherViewModel(
            @ApplicationContext context: Context,
            weatherRepository: WeatherRepository,
            myPlaceRepository: MyPlaceRepository,
            prefStoreRepository: PrefStoreRepository,
        ): WeatherViewModel = TestWeatherViewModel(
            context,
            weatherRepository,
            myPlaceRepository,
            prefStoreRepository,
        )
    }

    // Display Snackbar with error msg
    @Test
    fun error_path() {
        composeTestRule.onNodeWithText("Detailed Weather").assertIsDisplayed()
        composeTestRule.onNodeWithTag("snack_error").assertIsDisplayed()
    }
}
