package com.example.demoaerisproject.view.locations

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.demoaerisproject.data.location.LocationRepository
import com.example.demoaerisproject.data.room.MyPlaceRepository
import com.example.demoaerisproject.di.LocationModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(LocationModule::class)
@HiltAndroidTest
class LocationSearchActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<LocationSearchActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestLocationModule {
        @Provides
        fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
            LocationRepository(context)

        @Provides
        fun provideLocationSearchViewModel(
            locationRepository: LocationRepository,
            myPlaceRepository: MyPlaceRepository
        ): LocationSearchViewModel =
            TestLocationSearchViewModel(locationRepository, myPlaceRepository)

        @Provides
        fun provideMyLocationViewModel(
            myPlaceRepository: MyPlaceRepository
        ): MyLocationViewModel =
            MyLocationViewModel(myPlaceRepository)
    }
    /*
     * Display Snackbar with error msg
     */
    @Test
    fun error_path() {
        composeTestRule.onNodeWithTag("search_text").assertExists()
        composeTestRule.onNodeWithTag("snack_error").assertExists()
    }
}