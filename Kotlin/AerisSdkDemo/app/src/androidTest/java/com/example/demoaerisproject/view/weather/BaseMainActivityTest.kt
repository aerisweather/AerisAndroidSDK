package com.example.demoaerisproject.view.weather

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.data.weather.TestWeatherRepository
import com.example.demoaerisproject.data.weather.WeatherRepository
import com.example.demoaerisproject.di.WeatherModule
import com.example.demoaerisproject.view.NavDrawerItem
import com.example.demoaerisproject.view.viewmodel.TestWeatherViewModel
import com.example.demoaerisproject.view.weather.viewmodel.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(WeatherModule::class)
@HiltAndroidTest
open class BaseMainActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }
}