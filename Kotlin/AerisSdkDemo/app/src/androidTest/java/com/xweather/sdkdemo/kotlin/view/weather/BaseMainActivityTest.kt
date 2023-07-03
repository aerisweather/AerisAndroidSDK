package com.xweather.sdkdemo.kotlin.view.weather

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.xweather.sdkdemo.kotlin.di.WeatherModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule

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
