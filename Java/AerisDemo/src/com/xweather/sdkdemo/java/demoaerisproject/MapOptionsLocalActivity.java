package com.xweather.sdkdemo.java.demoaerisproject;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.aerisweather.aeris.maps.MapOptionsActivity;

public class MapOptionsLocalActivity extends MapOptionsActivity implements RadioGroup.OnCheckedChangeListener
{
    protected void onCreate(Bundle savedInstanceState) {
        this.setContentView(R.layout.activity_map_options);
        super.onCreate(savedInstanceState);
    }
}
