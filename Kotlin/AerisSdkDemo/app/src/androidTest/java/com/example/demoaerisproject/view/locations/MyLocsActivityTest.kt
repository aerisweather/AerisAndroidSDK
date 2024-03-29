package com.example.demoaerisproject.view.locations

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.demoaerisproject.di.LocationModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MyLocsActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MyLocsActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun happy_path() {
        composeTestRule.onNodeWithText("No Locations Saved").assertIsDisplayed()
    }
}