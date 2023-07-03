package com.xweather.sdkdemo.kotlin.view.map

import android.os.Bundle
import android.widget.RadioGroup
import com.aerisweather.aeris.maps.MapOptionsActivity
import com.aerisweather.aeris.maps.R

/**
 * TODO Settings page - convert to Compose
 */
class MapOptionsLocalActivity : MapOptionsActivity(), RadioGroup.OnCheckedChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        this.setContentView(R.layout.activity_map_options)
        super.onCreate(savedInstanceState)
    }
}
