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
import com.xweather.sdkdemo.kotlin.view.NavDrawerItem
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
class ExtendedForecastTest : BaseMainActivityTest() {
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
        ): WeatherViewModel {
            val model = TestWeatherViewModel(
                context,
                weatherRepository,
                myPlaceRepository,
                prefStoreRepository,
            )
            model.route = NavDrawerItem.ExtendedForecast.route
            return model
        }
    }

    // Display Snackbar with error msg
    @Test
    fun error_path() {
        composeTestRule.onNodeWithText("Extended Forecast").assertIsDisplayed()
        composeTestRule.onNodeWithTag("snack_error").assertIsDisplayed()
    }
}
