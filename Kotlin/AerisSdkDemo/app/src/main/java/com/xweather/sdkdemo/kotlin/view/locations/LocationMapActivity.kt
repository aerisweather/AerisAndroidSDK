package com.xweather.sdkdemo.kotlin.view.locations

// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/*
 * Code from Google Demo
 * https://github.com/googlemaps/android-maps-compose
 */

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.aerisweather.aeris.location.LocationHelper
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.room.MyPlace
import com.xweather.sdkdemo.kotlin.view.ComposeSnackbar
import com.xweather.sdkdemo.kotlin.view.ComposeSpinner
import com.xweather.sdkdemo.kotlin.view.weather.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationMapActivity : ComponentActivity() {
    companion object {
        val TAG = "LocationMapActivity"
    }

    private val viewModel: LocationMapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                Modifier
                    .padding(0.dp, 30.dp, 0.dp, 0.dp)
                    .fillMaxSize()
                    .background(Color.Black)
                    .testTag("init_black_box"),
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.event.observe(this, Observer(::onViewModelEvent))
    }

    private fun onViewModelEvent(event: MapEvent) {
        /**
         * TODO topbar ?
         */
        setContent {
            when (event) {
                is MapEvent.Success -> {
                    setLocation(event)
                    Render()
                }
                is MapEvent.InProgress -> ComposeSpinner()
                is MapEvent.Confirm -> Render(true)
                is MapEvent.Error -> ComposeSnackbar(event.msg)
            }
        }
    }

    @Composable
    private fun Render(isConfirmed: Boolean? = false) {
        Scaffold(
            topBar = {
                ComposeTopBar(viewModel, isConfirmed)
            },
            content = {
                it
                ComposeMap()
            },
        )
    }

    private fun setLocation(event: MapEvent.Success? = null) {
        // user specified location
        event?.myPlace?.let {
            viewModel.updateMyPlace(it)
        } ?: run {
            // current phone location
            findMyLocation()?.let {
                viewModel.updateMyPlace(getPlace(false, LatLng(it.latitude, it.longitude)))
            } ?: run {
                // use hardcoded values
                viewModel.myPlace.apply {
                    viewModel.updateMyPlace(getPlace(false, getLatLong()))
                }
            }
        }
    }

    private fun findMyLocation(): LatLng? {
        LocationHelper(this).currentLocation?.let {
            return LatLng(it.latitude, it.longitude)
        }
        return null
    }

    private fun getPlace(isMyLocation: Boolean, latLng: LatLng): MyPlace {
        val geocoder = Geocoder(this)
        val location = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        location?.let {
            if (it.size > 0) {
                return it[0].let {
                    val name = it.locality ?: it.featureName

                    MyPlace(
                        false,
                        latLng.latitude,
                        latLng.longitude,
                        name,
                        it.adminArea,
                        it.countryCode,
                    )
                }
            }
        }
        /*
         * TODO query for ocean detail
         */
        return MyPlace(isMyLocation, latLng.latitude, latLng.longitude)
    }

    @Composable
    fun ComposeTopBar(viewModel: LocationMapViewModel, isConfirmed: Boolean? = false) {
        TopAppBar(
            title = {
                IconButton(
                    onClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.text_back),
                        tint = Color.White,
                    )
                }
                Text(
                    stringResource(id = R.string.activity_map_search),
                    fontSize = 18.sp,
                    color = Color.White,
                )
            },
            backgroundColor = colorResource(id = R.color.colorPrimary),
            contentColor = Color.White,
            actions = {
                IconButton(
                    onClick = {
                        viewModel.selectAsMyLocation()
                    },
                ) {
                    Icon(
                        painterResource(
                            if (isConfirmed == true) {
                                R.drawable.ic_check_circle
                            } else {
                                R.drawable.ic_save
                            },
                        ),
                        stringResource(id = R.string.text_map),
                        modifier = Modifier.size(35.dp),
                    )
                }
                Text(text = "    ")

                IconButton(
                    onClick = {
                        viewModel.openMenu.value = true
                    },
                ) {
                    Icon(
                        painterResource(R.drawable.ic_more_vert),
                        stringResource(id = R.string.text_menu),
                        modifier = Modifier.size(35.dp),
                    )
                }

                DropdownMenu(
                    expanded = viewModel.openMenu.value,
                    onDismissRequest = { viewModel.openMenu.value = false },
                ) {
                    DropdownMenuItem(
                        onClick = {
                            viewModel.apply {
                                openMenu.value = false
                                findMyLocation()?.let {
                                    viewModel.updateMyPlace(
                                        getPlace(
                                            false,
                                            LatLng(it.latitude, it.longitude),
                                        ),
                                    )
                                }
                            }
                        },
                    ) {
                        Text(stringResource(id = R.string.locate_me))
                    }
                    DropdownMenuItem(
                        onClick = {
                            viewModel.apply {
                                openMenu.value = false
                                startActivity(
                                    Intent(
                                        this@LocationMapActivity,
                                        MyLocsActivity::class.java,
                                    ),
                                )
                            }
                        },
                    ) {
                        Text(stringResource(id = R.string.activity_my_locs))
                    }
                }
            },
        )
    }

    /**
     * Update marker location
     */
    private val onMapLongClick: (LatLng) -> Unit = { it ->
        viewModel.apply {
            val place = getPlace(false, it)
            invalidate(place)
        }
    }

    @Composable
    private fun ComposeMap() {
        var isMapLoaded by remember { mutableStateOf(false) }

        Box(Modifier.fillMaxSize()) {
            GoogleMapView(
                modifier = Modifier.matchParentSize(),
                onMapLoaded = {
                    isMapLoaded = true
                },
                onMapLongClick = onMapLongClick,
            )
            if (!isMapLoaded) {
                AnimatedVisibility(
                    modifier = Modifier
                        .matchParentSize(),
                    visible = !isMapLoaded,
                    enter = EnterTransition.None,
                    exit = fadeOut(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .wrapContentSize(),
                    )
                }
            }
        }
    }

    @Composable
    fun GoogleMapView(
        modifier: Modifier = Modifier,
        onMapLoaded: () -> Unit = {},
        onMapLongClick: (LatLng) -> Unit = {},
        content: @Composable () -> Unit = {},
    ) {
        if (viewModel.mapVisible.value) {
            GoogleMap(
                modifier = modifier,
                cameraPositionState = viewModel.cameraPositionStateValue,
                properties = viewModel.mapProperties.value,
                uiSettings = viewModel.uiSettings.value,
                onMapLoaded = onMapLoaded,
                onMapClick = onMapLongClick,
                onPOIClick = {
                    Log.d(TAG, "POI clicked: ${it.name}")
                },
            ) {
                // Drawing on the map is accomplished with a child-based API
                val markerClick: (Marker) -> Boolean = {
                    Log.d(TAG, "${it.title} was clicked")
                    viewModel.cameraPositionStateValue.projection?.let { projection ->
                        Log.d(TAG, "The current projection is: $projection")
                    }
                    false
                }
                MarkerInfoWindowContent(
                    state = viewModel.markerStateValue,
                    title = viewModel.myPlace.getLatLong()
                        .let { "Latitude:${it.latitude} Longitude:${it.longitude}" },
                    onClick = markerClick,
                    draggable = true,
                ) {
                    Text(it.title ?: "Title", color = Color.Red)
                }
                content()
            }
        }
    }
}
