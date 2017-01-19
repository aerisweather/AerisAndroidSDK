package com.example.demoaerisproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aerisweather.aeris.communication.AerisPermissionsRequest;
import com.aerisweather.aeris.maps.MapOptionsActivity;
import com.aerisweather.aeris.model.AerisPermissionsBuilder;
import com.aerisweather.aeris.model.AerisPermissionsResponse;
import com.aerisweather.aeris.model.MapsPermission;
import com.aerisweather.aeris.tiles.AMPResponse;
import com.aerisweather.aeris.util.NetworkUtils;
import com.google.android.gms.maps.GoogleMap;
import com.aerisweather.aeris.maps.AerisMapOptions;
import com.aerisweather.aeris.maps.MapOptionsActivityBuilder;
import com.aerisweather.aeris.model.AerisPermissions;
import com.aerisweather.aeris.tiles.AerisOverlay;
import com.aerisweather.aeris.tiles.AerisPointData;
import com.aerisweather.aeris.tiles.AerisPolygonData;
import com.aerisweather.aeris.tiles.AerisTile;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MapOptionsLocalActivity extends Activity implements RadioGroup.OnCheckedChangeListener
{
    AerisMapOptionsLocal m_options = null;
    List<AMPResponse> m_amplayerNames;
    private List<String> m_tiles;
    private List<String> m_polygonData;
    private List<String> m_pointData;
    LinearLayout m_ampLayerGroup;
    RadioGroup m_tileGroup;
    RadioGroup m_polygonGroup;
    RadioGroup m_pointDataGroup;
    RadioGroup m_mapOptionsGroup;
    private static final int MAX_OPACITY = 255;
    private static final int MAX_ANIM = 100;
    SeekBar m_opacitySeekerBar = null;
    SeekBar m_animSeekerBar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_map_options_local);

        m_ampLayerGroup = (LinearLayout) findViewById(R.id.llMapOptionsAmpLayers);
        m_tileGroup = (RadioGroup) findViewById(R.id.optMapOptionsTiles);
        m_polygonGroup = (RadioGroup) findViewById(R.id.optMapOptionsPolygon);
        m_pointDataGroup = (RadioGroup) findViewById(R.id.optMapOptionsPointData);
        m_mapOptionsGroup = (RadioGroup) findViewById(R.id.optMapOptionsMapType);

        m_mapOptionsGroup.setOnCheckedChangeListener(this);

        m_tileGroup.setOnCheckedChangeListener(this);
        m_polygonGroup.setOnCheckedChangeListener(this);
        m_pointDataGroup.setOnCheckedChangeListener(this);

        m_opacitySeekerBar = (SeekBar) findViewById(R.id.mapoptions_sb_opacity);
        m_animSeekerBar = (SeekBar) findViewById(R.id.mapoptions_sb_animation);


        m_options = AerisMapOptionsLocal.getPreference(getBaseContext());

        //start the task to get the AMP layers
        try
        {
            new GetLayersTask().execute().get();
        }
        catch (Exception ex)
        {
            setupList(m_tiles, com.aerisweather.aeris.maps.R.id.mapoptions_rg_overlays, m_tileGroup);
        }

        /* moved to postExecute
        addRadioButtons();
        initViews();
        */
        final ImageButton button = (ImageButton) findViewById(R.id.btnSave);
        if (button != null)
        {
            button.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    try
                    {
                        onClick_btnSave();
                    }
                    catch (Exception e) { }
                }
            });
        }

        super.onCreate(savedInstanceState);
    }
    
    private void addRadioButtons()
    {
        MapOptionsActivityBuilder builder = new MapOptionsActivityBuilder();
        AerisPermissions permissions = builder.build();

        m_tiles = new ArrayList<>();

        //add the standard tiles from enum
        m_tiles.add(AerisTile.NONE.getName());
        m_tiles.add(AerisTile.RADAR.getName());
        m_tiles.add(AerisTile.ADVISORIES.getName());
        m_tiles.add(AerisTile.SAT_INFRARED.getName());
        setupList(m_tiles, R.id.optMapOptionsTiles, m_tileGroup);

        //point data
        m_pointData = getPointList(permissions);
        setupList(m_pointData, R.id.optMapOptionsPointData, m_pointDataGroup);

        //polygon data
        m_polygonData = getPolyList(permissions);
        setupList(m_polygonData, R.id.optMapOptionsPolygon, m_polygonGroup);

    }

    private void initViews()
    {
        m_opacitySeekerBar.setMax(MAX_OPACITY);
        if (m_options != null)
            m_opacitySeekerBar.setProgress(m_options.getOpacity());

        m_animSeekerBar.setMax(MAX_ANIM);
        if (m_options != null)
            m_animSeekerBar.setProgress((int) m_options.getAnimationSpeed());

        if (m_options != null)
        {
            setupChecksOnList(m_tiles, m_tileGroup, m_options.getTile());
            setupChecksOnList(m_polygonData, m_polygonGroup, m_options.getPolygon());
            setupChecksOnList(m_pointData, m_pointDataGroup, m_options.getPointData());
        }

        if (m_options != null)
        {
            if (m_options.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
            {
                setCheckedAtIndex(0, m_mapOptionsGroup);
            }
            else if (m_options.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
            {
                setCheckedAtIndex(1, m_mapOptionsGroup);
            }
            else
            {
                if (m_options.getMapType() == GoogleMap.MAP_TYPE_HYBRID)
                {
                    setCheckedAtIndex(2, m_mapOptionsGroup);
                }
            }
        }
    }

    private void setupChecksOnList(List<String> list, RadioGroup radioGroup, AerisOverlay overlay)
    {
        if (list.size() > 1)
        {
            boolean checkMade = false;

            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(i).equals(overlay.getName()))
                {
                    setCheckedAtIndex(i, radioGroup);
                    checkMade = true;
                    break;
                }
            }

            // no check made, due to permissions changes check the first.
            if (!checkMade)
            {
                setCheckedAtIndex(0, radioGroup);
            }

        }
    }

    private void setCheckedAtIndex(int index, RadioGroup group)
    {
        RadioButton radioButton = (RadioButton) group.getChildAt(index);
        radioButton.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        int radioButtonID = group.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) group.findViewById(radioButtonID);
        int index = group.indexOfChild(radioButton);

        if (group.getId() == R.id.optMapOptionsTiles)
        {
            m_options.withTile(AerisTile.getTileFromName(m_tiles.get(index)));
        }
        else if (group.getId() == R.id.optMapOptionsMapType)
        {
            String buttonText = radioButton.getText().toString();

            if (buttonText.equals(getString(R.string.map_options_standard)))
            {
                m_options.withMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
            else if (buttonText.equals(getString(R.string.map_options_satellite)))
            {
                m_options.withMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
            else if (buttonText.equals(getString(R.string.map_options_hybrid)))
                {
                    m_options.withMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
        }
        else if (group.getId() == R.id.optMapOptionsPointData)
            {
                m_options.withPointData(AerisPointData.getPointDataFromName(m_pointData.get(index)));
            }
            else if (group.getId() == R.id.optMapOptionsPolygon)
                {
                    m_options.withPolygon(AerisPolygonData.getPolygonDataFromName(m_polygonData.get(index)));
                }

    }

    private class GetLayersTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            AerisPermissionsRequest request = new AerisPermissionsRequest(getString(R.string.aeris_client_id),
                    getString(R.string.aeris_client_secret), "com.example.demoaerisproject");
            JSONObject object = NetworkUtils.getJSONZipped(request, false);
            AerisPermissionsResponse permissionsResponse = AerisPermissionsResponse.fromJSON(object);
            AerisPermissions permissions = permissionsResponse.getPermissions();

            //get the AMP layers
            m_amplayerNames = AMPResponse.getAvailableLayers();
            List<String> stringCode = new ArrayList<>();

            //see which AMP layers we have permission to access
            MapsPermission mapPermissions = permissions.maps;
            for (int iLayer = 0; iLayer < m_amplayerNames.size(); iLayer++)
            {
                if (mapPermissions.layers.hasLayerAccess(m_amplayerNames.get(iLayer).id))
                    stringCode.add(m_amplayerNames.get(iLayer).id);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            addAmpLayerCheckboxes();
            addRadioButtons();
            initViews();
        }
    }

    private void addAmpLayerCheckboxes()
    {
        try
        {
            //for each layer, add a checkbox to the linear layout
            for (int iLayer = 0; iLayer < m_amplayerNames.size(); iLayer++)
            {
                CheckBox checkBox = new CheckBox(new ContextThemeWrapper(this, R.style.WhiteCheck));
                checkBox.setText(m_amplayerNames.get(iLayer).name);
                checkBox.setTextColor(Color.WHITE);

                m_ampLayerGroup.addView(checkBox, RadioGroup.LayoutParams.MATCH_PARENT);
            }
        }
        catch (Exception ex)
        {
            m_ampLayerGroup.removeAllViews();

            //add a message to the options informing the user that there are no amp layers available
            TextView txtMsg = new TextView(getBaseContext());
            txtMsg.setText("No AMP Layers Available");
            m_ampLayerGroup.addView(txtMsg);
        }
    }

    private void setupList(List<String> list, int id, RadioGroup radioGroup)
    {
        if (list.size() > 1)
        {
            for (String overlay : list)
            {
                RadioButton button = (RadioButton) getLayoutInflater().inflate(R.layout.aeris_radio_button, null);
                final float scale = getResources().getDisplayMetrics().density;
                int pixels = (int) (getResources().getInteger(R.integer.min_click_height) * scale + 0.5f);
                button.setText(overlay);
                radioGroup.addView(button, RadioGroup.LayoutParams.MATCH_PARENT, pixels);
            }
        }
        else
        {
            findViewById(id).setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
        }
    }

    public static List<String> getPolyList(AerisPermissions permissions)
    {
        AerisPolygonData[] codes = AerisPolygonData.values();
        List<String> stringCode = new ArrayList<String>();

        for (int i = 0; i < codes.length; i++)
        {
            stringCode.add(codes[i].getName());
        }

        return stringCode;
    }

    public static List<String> getPointList(AerisPermissions permissions)
    {
        AerisPointData[] codes = AerisPointData.values();
        List<String> stringCode = new ArrayList<String>();

        for (int i = 0; i < codes.length; i++)
        {
            /*
            boolean pass = false;
            if (codes[i] == AerisPointData.LIGHTNING_STRIKES && permissions.lightning.allow)
            {
                pass = true;
            }
            else if (codes[i] == AerisPointData.EARTHQUAKES && permissions.earthquakes.allow)
            {
                pass = true;
            }
            else if (codes[i] == AerisPointData.FIRE && permissions.fires.allow)
            {
                pass = true;
            }
            else if (codes[i] == AerisPointData.STORM_CELLS && permissions.stormcells.allow)
            {
                pass = true;
            } else if (codes[i] == AerisPointData.STORM_REPORTS && permissions.stormreports.allow)
            {
                pass = true;
            }
            else if (codes[i] == AerisPointData.NONE)
            {
                pass = true;
            }
            */

            boolean pass = true; //remove when we add the permission checking
            if (pass)
            {
                stringCode.add(codes[i].getName());
            }
        }

        return stringCode;
    }

    private void onClick_btnSave()
    {
        int animSpeed = m_animSeekerBar.getProgress();
        m_options.withAnimationSpeed(animSpeed);

        int opacity = m_opacitySeekerBar.getProgress();
        m_options.withOpacity(opacity);

        m_options.setPreference(getBaseContext());
        this.finish();
    }
}
