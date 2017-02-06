package com.example.demoaerisproject;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.aerisweather.aeris.maps.AerisMapOptions;
import com.aerisweather.aeris.maps.MapOptionsActivity;
import com.aerisweather.aeris.tiles.AerisAmp;

public class MapOptionsLocalActivity extends MapOptionsActivity implements RadioGroup.OnCheckedChangeListener
{
    protected void onCreate(Bundle savedInstanceState) {
        this.setContentView(com.aerisweather.aeris.maps.R.layout.activity_map_options);

        super.onCreate(savedInstanceState);
    }
}
