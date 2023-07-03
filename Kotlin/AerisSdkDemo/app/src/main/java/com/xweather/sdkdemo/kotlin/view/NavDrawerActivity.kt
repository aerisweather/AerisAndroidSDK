package com.xweather.sdkdemo.kotlin.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aerisweather.aeris.maps.AerisMapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.view.locations.LocationSearchActivity
import com.xweather.sdkdemo.kotlin.view.locations.MyLocsActivity
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.BaseWeatherEvent
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.UnitEvent
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class NavDrawerActivity : ComponentActivity() {
    val viewModel: WeatherViewModel by viewModels()
    var _aerisMapView: AerisMapView? = null

    open val initView: (callback: OnMapReadyCallback?) -> Unit = {
        /* virtual */
    }

    fun getPermissions() {
        // check for permissions
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
            )
        } else {
            initView(this as? OnMapReadyCallback)
        }
    }

    val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.forEach { actionMap ->
                when (actionMap.key) {
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        if (actionMap.value) {
                            initView(this as? OnMapReadyCallback)
                        } else {
                            Toast.makeText(
                                this,
                                R.string.permissions_verbiage,
                                Toast.LENGTH_LONG,
                            ).show()
                        }
                    }
                    else -> {}
                }
            }
        }

    @Composable
    fun MainScreen(
        navigateTo: (route: String?) -> Unit,
        observationEvent: BaseWeatherEvent? = null,
        unitEvent: UnitEvent? = null,
        savedInstanceState: Bundle? = null,
    ) {
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val scope = rememberCoroutineScope()
        val navController = rememberNavController()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopBar(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navigateTo = navigateTo,
                )
            },
            drawerBackgroundColor = colorResource(id = R.color.colorPrimary),
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    navigateTo,
                )
            },
            backgroundColor = colorResource(id = R.color.colorPrimaryDark),
            /*
             * TODO Drawer - drag issue : upgrade Compose v1.20 when possible
             *  https://issuetracker.google.com/issues/202569585
             */
            drawerGesturesEnabled = false,
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(
                    navController = navController,
                    observationEvent,
                    unitEvent,
                    savedInstanceState,
                )
            }
        }
    }

    @Composable
    open fun Navigation(
        navController: NavHostController,
        observationEvent: BaseWeatherEvent? = null,
        unitEvent: UnitEvent? = null,
        savedInstanceState: Bundle?,
    ) {
    }

    @Composable
    open fun TopBar(
        scope: CoroutineScope,
        scaffoldState: ScaffoldState,
        navigateTo: (route: String?) -> Unit,
    ) {
    }

    @Composable
    fun Drawer(
        scope: CoroutineScope,
        scaffoldState: ScaffoldState,
        navController: NavController,
        navigateTo: (route: String?) -> Unit,
    ) {
        val items = listOf(
            NavDrawerItem.Detailed,
            NavDrawerItem.ExtendedForecast,
            NavDrawerItem.NearbyObservation,
            NavDrawerItem.Overview,
            NavDrawerItem.WeekendForecast,
            NavDrawerItem.InteractiveMap,
            NavDrawerItem.AirQuality,
            NavDrawerItem.LightningStrike,
            NavDrawerItem.LightningThreats,
            NavDrawerItem.LightningFlash,
            NavDrawerItem.LightningSummary,
            NavDrawerItem.Maritime,
            NavDrawerItem.SunMoon,
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            val context = LocalContext.current
            DrawerItem(item = NavDrawerItem.Search, selected = false) {
                context.startActivity(Intent(context, LocationSearchActivity::class.java))
            }

            DrawerItem(item = NavDrawerItem.MyLoc, selected = false) {
                context.startActivity(Intent(context, MyLocsActivity::class.java))
            }

            Image(
                painter = painterResource(id = R.drawable.home_feature_aerisapi),
                contentDescription = R.drawable.aerislogo.toString(),
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp),
            )
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                    navigateTo(item.route)
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
            }
        }
    }
}

@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
    val background = if (selected) R.color.colorPrimaryDark else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onItemClick(item)
                },
            )
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp),
    ) {
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 18.sp,
            color = Color.White,
        )
    }
}

sealed class NavDrawerItem(var route: String, var title: String) {
    object Search : NavDrawerItem("Find Location", "Find location")
    object MyLoc : NavDrawerItem("My Location", "My location")
    object Detailed : NavDrawerItem("Detailed Weather", "Detailed weather")
    object ExtendedForecast : NavDrawerItem("Extended Forecast", "Extended forecast")
    object NearbyObservation : NavDrawerItem("Nearby Observation", "Nearby observation")
    object Overview : NavDrawerItem("Overview Weather", "Overview weather")
    object WeekendForecast : NavDrawerItem("Weekend Forecast", "Weekend forecast")
    object InteractiveMap : NavDrawerItem("Interactive Map", "Interactive map")
    object AirQuality : NavDrawerItem("Air Quality", "Air quality")
    object LightningStrike : NavDrawerItem("Lightning Strike", "Lightning strike")
    object LightningThreats : NavDrawerItem("Lightning Threats", "Lightning threats")
    object LightningFlash : NavDrawerItem("Lightning Flash", "Lightning flash")
    object LightningSummary : NavDrawerItem("Lightning Summary", "Lightning summary")
    object Maritime : NavDrawerItem("Maritime", "Maritime")
    object SunMoon : NavDrawerItem("Sun Moon", "Sun moon")
}
