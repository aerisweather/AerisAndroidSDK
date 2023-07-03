package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.maps.OnMapReadyCallback
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.view.ComposeSnackbar
import com.xweather.sdkdemo.kotlin.view.ComposeSpinner
import com.xweather.sdkdemo.kotlin.view.NavDrawerActivity
import com.xweather.sdkdemo.kotlin.view.NavDrawerItem
import com.xweather.sdkdemo.kotlin.view.map.MyMapActivity
import com.xweather.sdkdemo.kotlin.view.settings.SettingsActivity
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.BaseWeatherEvent
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.UnitEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.lang.reflect.Type

@AndroidEntryPoint
class MainActivity : NavDrawerActivity() {
    companion object {
        val TAG: Type = MainActivity::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissions()
    }

    override val initView: (callback: OnMapReadyCallback?) -> Unit = {
        if (this.javaClass == TAG) {
            navigateTo(viewModel.route)
        }
    }

    override fun onResume() {
        super.onResume()
        val route = intent.getStringExtra("route")
        viewModel.let {
            route?.apply {
                it.route = route
            }
            CoroutineScope(Dispatchers.Main).launch {
                it.locationEvent.asFlow().collect() {
                    navigateTo(route)
                }
            }

            val combine =
                combine(it.event.asFlow(), it.unitEvent.asFlow()) { observationEvent, unitEvent ->
                    Pair(observationEvent, unitEvent)
                }

            CoroutineScope(Dispatchers.Main).launch {
                combine.collect() {
                    setContent {
                        MainScreen(navigateTo, it.first, it.second)
                    }
                }
            }
        }
    }

    @Composable
    override fun Navigation(
        navController: NavHostController,
        observationEvent: BaseWeatherEvent?,
        unitEvent: UnitEvent?,
        savedInstanceState: Bundle?,
    ) {
        val isMetric = unitEvent is UnitEvent.Metrics
        NavHost(navController, startDestination = viewModel.route) {
            composable(NavDrawerItem.Detailed.route) {
                val screen = DetailedScreen(LocalContext.current, isMetric)
                HandleWeatherEvent(screen, observationEvent)
            }
            composable(NavDrawerItem.ExtendedForecast.route) {
                val screen =
                    ExtendedForecastScreen(LocalContext.current, isMetric)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.NearbyObservation.route) {
                val screen =
                    NearbyObservationScreen(LocalContext.current, isMetric)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.Overview.route) {
                val screen = OverviewScreen(LocalContext.current, isMetric)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.WeekendForecast.route) {
                val screen =
                    WeekendForecastScreen(LocalContext.current, isMetric)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.InteractiveMap.route) {
                /* never happens */
            }

            composable(NavDrawerItem.SunMoon.route) {
                if (observationEvent !is BaseWeatherEvent.Success) {
                    val screen = SunMoonScreen(LocalContext.current, isMetric)
                    HandleWeatherEvent(screen, observationEvent)
                }
            }

            composable(NavDrawerItem.AirQuality.route) {
                val screen = AirQualityScreen(LocalContext.current, isMetric)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.LightningStrike.route) {
                val screen = LightningStrikeScreen(LocalContext.current, isMetric, viewModel)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.LightningThreats.route) {
                val screen = LightningThreatsScreen(LocalContext.current, isMetric, viewModel)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.LightningFlash.route) {
                val screen = LightningFlashScreen(LocalContext.current, isMetric, viewModel)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.LightningSummary.route) {
                val screen = LightningSummaryScreen(LocalContext.current, isMetric, viewModel)
                HandleWeatherEvent(screen, observationEvent)
            }

            composable(NavDrawerItem.Maritime.route) {
                val screen = MaritimeScreen(LocalContext.current, isMetric, viewModel)
                HandleWeatherEvent(screen, observationEvent)
            }
        }
    }

    @Composable
    override fun TopBar(
        scope: CoroutineScope,
        scaffoldState: ScaffoldState,
        navigateTo: (route: String?) -> Unit,
    ) {
        var showMenu by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val title = viewModel.route

        TopAppBar(
            title = { Text(text = title, fontSize = 18.sp) },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, "")
                }
            },
            backgroundColor = colorResource(id = R.color.colorPrimary),
            contentColor = Color.White,
            actions = {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painterResource(R.drawable.ic_more_vert),
                        stringResource(id = R.string.menu),
                        modifier = Modifier.size(35.dp),
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(onClick = {
                        showMenu = false
                        navigateTo(null)
                    }) {
                        Text(stringResource(id = R.string.action_refresh))
                    }

                    DropdownMenuItem(
                        onClick = {
                            showMenu = false
                            val intent = Intent(context, SettingsActivity::class.java)
                            intent.putExtra("TAG", TAG.toString())
                            intent.putExtra("route", viewModel.route)
                            context.startActivity(intent)
                        },
                    ) {
                        Text(stringResource(id = R.string.action_settings))
                    }
                }
            },
        )
    }

    val navigateTo: (route: String?) -> Unit = {
        it?.let {
            viewModel.route = it
            when (it) {
                NavDrawerItem.Detailed.route -> viewModel.requestDetailedObservation()
                NavDrawerItem.ExtendedForecast.route -> viewModel.requestExtForecast()
                NavDrawerItem.NearbyObservation.route -> viewModel.requestNearbyObservation()
                NavDrawerItem.Overview.route -> viewModel.requestOverview()
                NavDrawerItem.WeekendForecast.route -> viewModel.requestWeekendForecast()
                NavDrawerItem.InteractiveMap.route -> {
                    startActivity(Intent(this, MyMapActivity::class.java))
                }
                NavDrawerItem.SunMoon.route -> viewModel.requestSunMoon()
                NavDrawerItem.AirQuality.route -> viewModel.requestAirQuality()
                NavDrawerItem.LightningStrike.route -> viewModel.requestLightning()
                NavDrawerItem.LightningThreats.route -> viewModel.requestLightningThreats()
                NavDrawerItem.LightningFlash.route -> viewModel.requestLightningFlash()
                NavDrawerItem.LightningSummary.route -> viewModel.requestLightningSummary()
                NavDrawerItem.Maritime.route -> viewModel.requestMaritime()
            }
        }
    }

    @Composable
    fun HandleWeatherEvent(screen: IScreen, event: BaseWeatherEvent?) {
        when (event) {
            is BaseWeatherEvent.SunMoon -> {
                val list = event.response?.response
                if (list?.isNotEmpty() == true) {
                    screen.Render(list = list)
                } else {
                    ComposeSnackbar(stringResource(id = R.string.error_no_data))
                }
            }
            is BaseWeatherEvent.Success -> {
                val list = event.response?.responses
                if (list?.isNotEmpty() == true) {
                    screen.Render(list = list)
                } else {
                    ComposeSnackbar(stringResource(id = R.string.error_no_data))
                }
            }
            is BaseWeatherEvent.InProgress -> {
                ComposeSpinner()
            }
            is BaseWeatherEvent.Error -> {
                ComposeSnackbar(event.msg)
            }
            is BaseWeatherEvent.Lightning -> {
                val desc = event.response
                screen.Render(list = desc)
            }
            is BaseWeatherEvent.LightningThreats -> {
                val desc = event.response
                screen.Render(list = desc)
            }
            is BaseWeatherEvent.LightningFlash -> {
                val desc = event.response
                screen.Render(list = desc)
            }
            is BaseWeatherEvent.LightningSummary -> {
                val desc = event.response
                screen.Render(list = desc)
            }
            is BaseWeatherEvent.Maritime -> {
                val desc = event.response
                screen.Render(list = desc)
            }
            else -> {
                ComposeSnackbar(stringResource(R.string.error_unknown))
            }
        }
    }
}
