package com.example.demoaerisproject.view.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.aerisweather.aeris.maps.AerisMarkerWindow
import com.aerisweather.aeris.util.FileUtil
import com.aerisweather.aeris.util.WeatherUtil
import com.example.demoaerisproject.R
import com.google.android.gms.maps.model.Marker

/**
 * Adapter that applies a new layer of tiles over the map.
 *
 * @author Ben Collins
 */
class TemperatureWindowAdapter(context: Context) :
    AerisMarkerWindow() {
    /**
     * Used to inflate the view.
     */
    private val inflater: LayoutInflater

    /**
     * Context necessary for inflating and finding resources.
     */
    private val context: Context
    override fun getView(): View {
        return inflater.inflate(
            R.layout.dialog_aeris_windowadapter, null
        ) as TextView
    }

    override fun fillView(view: View, marker: Marker) {
        val textView = view as TextView
        val data = getData(marker) as TemperatureInfoData
        textView.text = WeatherUtil.appendDegree(data.temperature)
        textView.setCompoundDrawablesWithIntrinsicBounds(
            FileUtil.getDrawableByName(data.icon, context), 0, 0, 0
        )
    }

    override fun onInfoWindowPressed(marker: Marker) {}

    /**
     * Adapter for grabbing the necessary tiles for up to date casts on the Map.
     *
     */
    init {
        inflater = LayoutInflater.from(context)
        this.context = context
    }
}