package com.xweather.sdkdemo.kotlin.view.map

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.AttributeSet
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aerisweather.aeris.communication.EndpointType
import com.aerisweather.aeris.location.LocationHelper
import com.aerisweather.aeris.maps.AerisMapContainerView
import com.aerisweather.aeris.maps.AerisMapOptions
import com.aerisweather.aeris.maps.interfaces.OnAerisMapLongClickListener
import com.aerisweather.aeris.maps.interfaces.OnAerisMarkerInfoWindowClickListener
import com.aerisweather.aeris.maps.markers.AerisMarker
import com.aerisweather.aeris.model.AerisPermissions
import com.aerisweather.aeris.model.AerisResponse
import com.aerisweather.aeris.response.EarthquakesResponse
import com.aerisweather.aeris.response.FiresResponse
import com.aerisweather.aeris.response.ObservationResponse
import com.aerisweather.aeris.response.RecordsResponse
import com.aerisweather.aeris.response.StormCellResponse
import com.aerisweather.aeris.response.StormReportsResponse
import com.aerisweather.aeris.tiles.AerisAmp
import com.aerisweather.aeris.tiles.AerisAmpGetLayersTask
import com.aerisweather.aeris.tiles.AerisAmpLayer
import com.aerisweather.aeris.tiles.AerisAmpOnGetLayersTaskCompleted
import com.aerisweather.aeris.tiles.AerisPointData
import com.aerisweather.aeris.tiles.AerisPolygonData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.room.MyPlace
import com.xweather.sdkdemo.kotlin.util.FormatUtil
import com.xweather.sdkdemo.kotlin.view.ComposeSnackbar
import com.xweather.sdkdemo.kotlin.view.NavDrawerActivity
import com.xweather.sdkdemo.kotlin.view.NavDrawerItem
import com.xweather.sdkdemo.kotlin.view.settings.SettingsActivity
import com.xweather.sdkdemo.kotlin.view.weather.MainActivity
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.BaseWeatherEvent
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.MyPlaceEvent
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.UnitEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.reflect.Type

@AndroidEntryPoint
class MyMapActivity :
    NavDrawerActivity(),
    OnAerisMapLongClickListener,
    OnAerisMarkerInfoWindowClickListener,
    OnMapReadyCallback {
    companion object {
        val TAG: Type = MyMapActivity::class.java
    }

    private var marker: Marker? = null
    private var aerisAmp: AerisAmp? = null
    private var isMapReady = false
    private var isAmpReady = false
    private var mapOptions: AerisMapOptions? = null
    private var googleMap: GoogleMap? = null
    private var infoAdapter: TemperatureWindowAdapter? = null
    private var myPlace: MyPlace? = null
    private var isMetrics: Boolean? = null
    private var attributeSet: AttributeSet? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attributeSet = this.resources.getXml(R.xml.map_view)
        setContent {
            MainScreen(navigateTo, null, null, savedInstanceState)
        }
    }

    override val initView: (callback: OnMapReadyCallback?) -> Unit = {
        it?.let {
            _aerisMapView?.getMapAsync(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.let {
            it.unitEvent.observe(this, ::onUnitEvent)
            it.locationEvent.observe(this, ::onLocationEvent)
            it.event.observe(this, ::onBaseWeatherEvent)
            if (myPlace == null) {
                it.requestMyPlace()
            }
        }

        // we are resuming the map view, so check for updated options
        _aerisMapView?.apply {
            mapOptions?.let {
                it.getMapPreferences(context)
                map.mapType = it.mapType
                addLayer(it.aerisAMP)
                addLayer(it.pointData)
                addLayer(it.polygonData)
            }
            // tell the map to redraw itself
            onResume()
        }
    }

    @Composable
    override fun Navigation(
        navController: NavHostController,
        observationEvent: BaseWeatherEvent?,
        unitEvent: UnitEvent?,
        savedInstanceState: Bundle?,
    ) {
        NavHost(navController, startDestination = NavDrawerItem.InteractiveMap.route) {
            composable(NavDrawerItem.InteractiveMap.route) {
                ComposeMap(savedInstanceState = savedInstanceState)
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
        val title = NavDrawerItem.InteractiveMap.route

        TopAppBar(
            title = { Text(text = title, fontSize = 18.sp) },
            navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                ) {
                    Icon(Icons.Filled.Menu, "")
                }
            },
            backgroundColor = colorResource(id = R.color.colorPrimary),
            contentColor = Color.White,
            actions = {
                IconButton(
                    onClick = { showMenu = true },
                ) {
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
                    DropdownMenuItem(
                        onClick = {
                            showMenu = false
                            onResume()
                        },
                    ) {
                        Text(stringResource(id = R.string.action_refresh))
                    }
                    DropdownMenuItem(
                        onClick = {
                            showMenu = false
                            startActivity(
                                Intent(
                                    baseContext,
                                    MapOptionsLocalActivity::class.java,
                                ),
                            )
                        },
                    ) {
                        Text(stringResource(id = R.string.action_layers))
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

    @Composable
    fun ComposeMap(savedInstanceState: Bundle?) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                AerisMapContainerView(context, attributeSet).apply {
                    _aerisMapView = aerisMapView
                    _aerisMapView?.onCreate(savedInstanceState)
                    aerisAmp = AerisAmp(
                        getString(R.string.aerisapi_client_id),
                        getString(R.string.aerisapi_client_secret),
                    )

                    aerisAmp?.apply {
                        // get all the possible layers, then get permissions from the API and generate a list of permissible layers
                        AerisAmpGetLayersTask(
                            GetLayersTaskCallback(),
                            this,
                        ).execute()
                        //      .get()
                    }
                    getPermissions()
                    onResume()
                }
            },
        )
    }

    val navigateTo: (route: String?) -> Unit = {
        it?.let {
            viewModel.route = it
        }

        if (it.equals(NavDrawerItem.InteractiveMap.route)) {
            /* Haco Nada */
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("route", it)
            startActivity(intent)
        }
    }

    private fun onUnitEvent(unit: UnitEvent) {
        isMetrics = when (unit) {
            is UnitEvent.Imperial -> false
            is UnitEvent.Metrics -> true
        }
    }

    override fun onMapReady(map: GoogleMap) {
        isMapReady = true
        googleMap = map
        _aerisMapView?.init(googleMap)
        if (isAmpReady) {
            initMap()
        }
    }

    inner class GetLayersTaskCallback : AerisAmpOnGetLayersTaskCompleted {
        override fun onAerisAmpGetLayersTaskCompleted(
            permissibleLayers: ArrayList<AerisAmpLayer>,
            permissions: AerisPermissions,
        ) {
            isAmpReady = true
            if (isMapReady) {
                initMap()
            }
        }
    }

    /**
     * Initializes the map with specific settings
     */
    private fun initMap() {
        _aerisMapView?.setUseMapOptions(true)

        // create a new MapOptions obj
        mapOptions = AerisMapOptions()
        aerisAmp?.getPermissibleLayers(false)
        /*
        Example of overriding the default domain for AMP
        i.e. using a custom CDN

        AerisConstants.getInstance().setBaseTileSecureUrl("https://maps.yourdomain.com/");
         */

        // set the mapOptions class's AerisAMP obj
        mapOptions?.let {
            it.aerisAMP = aerisAmp
            if (!it.getMapPreferences(this)) {
                // set default layers/data
                it.setDefaultAmpLayers()
                it.pointData = AerisPointData.NONE
                it.polygonData = AerisPolygonData.NONE

                // save the map options
                it.saveMapPreferences(this)
            }
            _aerisMapView?.map?.mapType = it.mapType
        }

        // amp layer(s)
        val aerisAmp = mapOptions?.aerisAMP
        /**
         * CUSTOM / UNDOCUMENTED LAYER
         */
        /**
         * CUSTOM / UNDOCUMENTED LAYER
         * val customAmpLayer = AerisAmpLayer("lightning-all", "lightning-all", 80)
         * AerisAmpLayer customAmpLayer = new AerisAmpLayer("pressure-msl-nam", "pressure-msl-nam", 80);
         * aerisAmp.setLayer(customAmpLayer);
         */
        /**
         * SETTING LAYER MODIFIERS
         */
        /**
         * SETTING LAYER MODIFIERS
         * AerisAmpLayer statesAmpLayer = aerisAmp.getLayerFromId("states");
         * AerisAmpLayer.Modifier stateModifier = statesAmpLayer.getLayerModifier("States Outlines");
         * stateModifier.setModifierOption("outlines", true);
         * aerisAmp.setLayer(statesAmpLayer);
         */
        /**
         * EXAMPLE: Adding 6-10day outlook with custom legend
         */
        /**
         * EXAMPLE: Adding 6-10day outlook with custom legend
         * AerisAmpLayer outlookAmpLayer = new AerisAmpLayer("temperatures-outlook-6-10d-cpc","temperatures-outlook-6-10d-cpc",100);
         * outlookAmpLayer.setCustomLayerLegend(R.drawable.legend_temp_outlook);
         * aerisAmp.setLayer(outlookAmpLayer);
         */
        if ((aerisAmp?.activeMapLayers?.size ?: 0) < 1) {
            aerisAmp?.setDefaultLayers()
        }
        _aerisMapView?.addLayer(aerisAmp)

        // point data layer(s)
        _aerisMapView?.addLayer(mapOptions?.pointData)
        /**
         * SAMPLE: TROPICAL CYCLONES POINT DATA
         */
        /**
         * SAMPLE: TROPICAL CYCLONES POINT DATA
         */
        // _aerisMapView.addLayer(AerisPointData.TROPICAL_CYCLONES);

        // polygon layer(s)
        /**
         * SAMPLE: DAY TWO CONVECTIVE
         */
        /**
         * SAMPLE: DAY TWO CONVECTIVE
         * AerisPolygonData aerisPolygonData = m_mapOptions.getPolygon();
         * aerisPolygonData.setConvectiveOutlookParameters(new ParameterBuilder()
         * .withFilter(Filter.DAY_TWO.getCode() + "," + Filter.GEO_POLY.getCode())
         * .withCustomParameter("from", "today"));
         * _aerisMapView.addLayer(aerisPolygonData);
         */
        /**
         * SAMPLE: TROPICAL CYCLONES ERROR CONES
         */
        /**
         * SAMPLE: TROPICAL CYCLONES ERROR CONES
         */

        /*
        AerisPolygonData aerisPolygonData = AerisPolygonData.TROPICAL_CYCLONE_ERROR_CONES;
        _aerisMapView.addLayer(aerisPolygonData);
         */

        // get a new marker option object
        val markerOptions = MarkerOptions()

        // get a stored location if there is one
        var myLocation: Location? = null

        myPlace?.let {
            // we found a stored location so move the map to it
            _aerisMapView?.moveToLocation(LatLng(it.latitude, it.longitude), 9f)

            // set the marker location
            markerOptions.position(LatLng(it.latitude, it.longitude))
            myLocation = Location("").apply {
                latitude = it.latitude
                longitude = it.longitude
            }
        } ?: run {
            // we didn't find a stored location, so get the current location
            LocationHelper(this).apply {
                myLocation = currentLocation
            }

            // move the map to the location
            _aerisMapView?.moveToLocation(myLocation, 9f)

            // set the marker location
            myLocation?.apply {
                markerOptions.position(
                    LatLng(
                        latitude,
                        longitude,
                    ),
                )
            }
        }

        // add the marker with specified options
        googleMap?.let {
            myLocation?.apply {
                it.addMarker(markerOptions)
            }
        }

        // do something when a user makes a long click
        _aerisMapView?.setOnAerisMapLongClickListener(this)

        /*
         * TODO Rewrite this
         */
        // setup the custom info window adapter to use
        infoAdapter = TemperatureWindowAdapter(this)
        _aerisMapView?.addWindowInfoAdapter(infoAdapter)
    }

    private fun onLocationEvent(event: MyPlaceEvent) {
        myPlace = (event as MyPlaceEvent.Current).myPlace
        myPlace?.let {
            if (isMapReady) {
                _aerisMapView?.moveToLocation(LatLng(it.latitude, it.longitude), 9f)
            } else {
                _aerisMapView?.getMapAsync(this)
            }
        }
    }

    private fun onBaseWeatherEvent(event: BaseWeatherEvent) {
        when (event) {
            is BaseWeatherEvent.Map -> loadObservation(event)
            is BaseWeatherEvent.InProgress -> {
                /* map already has a spinner */
            }
            is BaseWeatherEvent.Error -> {
                setContent {
                    ComposeSnackbar(event.msg)
                }
            }
            else -> {
                /* error ? */
            }
        }
    }

    private fun loadObservation(event: BaseWeatherEvent.Map) {
        val obResponse = event.response
        val ob = obResponse?.observation
        val relativeTo = obResponse?.relativeTo
        marker?.remove()

        ob?.let {
            relativeTo?.let {
                val data =
                    TemperatureInfoData(
                        ob.icon,
                        FormatUtil.printDegree(this, isMetrics, Pair(ob.tempC, ob.tempF)),
                    )
                marker = infoAdapter?.addGoogleMarker(
                    _aerisMapView?.map,
                    relativeTo.lat,
                    relativeTo.lon,
                    BitmapDescriptorFactory.fromResource(R.drawable.map_indicator_blank),
                    data,
                )
                marker?.showInfoWindow()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        _aerisMapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _aerisMapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        _aerisMapView?.onLowMemory()
    }

    override fun onMapLongClick(lat: Double, longitude: Double) {
        viewModel.requestByMapLatLong(lat, longitude)
    }

    fun onResult(type: EndpointType, response: AerisResponse) {
        if (type == EndpointType.OBSERVATIONS) {
            if (response.isSuccessfulWithResponses) {
                val obResponse = ObservationResponse(response.firstResponse)
                val ob = obResponse.observation
                val relativeTo = obResponse.relativeTo
                marker?.remove()
                val data = TemperatureInfoData(ob.icon, ob.tempF.toString())
                marker = infoAdapter?.addGoogleMarker(
                    _aerisMapView?.getMap(),
                    relativeTo.lat,
                    relativeTo.lon,
                    BitmapDescriptorFactory.fromResource(R.drawable.map_indicator_blank),
                    data,
                )
                marker?.showInfoWindow()
            }
        }
    }

    override fun earthquakeWindowPressed(response: EarthquakesResponse?, marker: AerisMarker?) {
        // do something with the response data.
        Toast.makeText(this, "Earthquake pressed!", Toast.LENGTH_SHORT).show()
    }

    override fun stormReportsWindowPressed(response: StormReportsResponse?, marker: AerisMarker?) {
        // do something with the response data.
        Toast.makeText(this, "Storm Report pressed!", Toast.LENGTH_SHORT).show()
    }

    override fun stormCellsWindowPressed(response: StormCellResponse?, marker: AerisMarker?) {
        // do something with the response data.
        Toast.makeText(this, "Storm Cell pressed!", Toast.LENGTH_SHORT).show()
    }

    override fun wildfireWindowPressed(response: FiresResponse?, marker: AerisMarker?) {
        // do something with the response data.
        Toast.makeText(this, "Wildfire pressed!", Toast.LENGTH_SHORT).show()
    }

    override fun recordsWindowPressed(response: RecordsResponse?, marker: AerisMarker?) {
        // do something with the response data.
        Toast.makeText(this, "Daily Record pressed!", Toast.LENGTH_SHORT).show()
    }
}
