package com.example.demoaerisproject.view.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.demoaerisproject.di.SettingsModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SettingsActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<SettingsActivity>()

    @Test
    fun happy_path() {
        composeTestRule.onNodeWithText("Notification").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enabled Notification").assertIsDisplayed()
        composeTestRule.onNodeWithText("Unit of Measure").assertIsDisplayed()
    }
}