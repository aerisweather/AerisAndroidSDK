package com.example.listview;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aerisweather.aeris.model.Pollutant;
import com.aerisweather.aeris.response.AirQualityResponse;
import com.aerisweather.aeris.util.FileUtil;
import com.aerisweather.aeris.util.WeatherUtil;
import com.example.demoaerisproject.R;
import com.example.view.PollutantView;

public class AirQualityItemHolder implements AdapterHolder<AirQualityResponse> {
    TextView m_place;
    TextView m_time;
    TextView m_aqi;
    TextView m_category;
    ImageView m_aqiIcon;
    TextView m_method;
    TextView m_dominant;
    LinearLayout m_llPollutants;

    Activity m_activity;

    AirQualityItemHolder(Activity activity) {
        m_activity = activity;
    }

    @Override
    public View inflateview(LayoutInflater mInflater) {
        View v = mInflater.inflate(R.layout.view_airquality, null, false);
        m_place = (TextView) v.findViewById(R.id.tvPlace);
        m_time = (TextView) v.findViewById(R.id.tvTime);
        m_aqi = (TextView) v.findViewById(R.id.tvAirQuality);
        m_category = (TextView) v.findViewById(R.id.tvCategory);
        m_aqiIcon = (ImageView) v.findViewById(R.id.ivAirQualityIcon);
        m_method = (TextView) v.findViewById(R.id.tvMethod);
        m_dominant = (TextView) v.findViewById(R.id.tvDominant);
        m_llPollutants = (LinearLayout) v.findViewById(R.id.llPollutants);
        return v;
    }

    @Override
    public void populateView(AirQualityResponse response, int position) {
        m_place.setText(WeatherUtil.capitalize(response.getPlace().name));

        m_time.setText(WeatherUtil.getFormatFromISO(response.getPeriods().get(0).dateTimeISO, "h:mm aa"));

        String sAQI = String.valueOf(response.getPeriods().get(0).aqi);
        m_aqi.setText(sAQI);

        m_category.setText(WeatherUtil.capitalize(response.getPeriods().get(0).category));

//		m_aqiIcon.setImageResource(FileUtil.getDrawableByName(t.getObservation().icon, weatherIcon.getContext()));
        String aqiColorString = "#" + response.getPeriods().get(0).color;
        int aqiColor = Color.parseColor(aqiColorString);
        m_aqiIcon.setBackgroundColor(aqiColor);

        m_method.setText("Method of Calculation: " + WeatherUtil.capitalize(response.getPeriods().get(0).method));

        m_dominant.setText("Dominant Pollutant: " + WeatherUtil.capitalize(response.getPeriods().get(0).dominant));

        m_llPollutants.removeAllViews();
        for (Pollutant pollutant : response.getPeriods().get(0).pollutants) {
            PollutantView pollutantView = new PollutantView(m_activity, null);
            String category = pollutant.category == null ? "---" : pollutant.category;
            pollutantView.setText(
                    WeatherUtil.capitalize(pollutant.name),
                    String.valueOf(pollutant.aqi),
                    WeatherUtil.capitalize(category),
                    String.valueOf(pollutant.valuePPB),
                    String.valueOf(pollutant.valueUGM3));
            m_llPollutants.addView(pollutantView);
        }
    }

}
