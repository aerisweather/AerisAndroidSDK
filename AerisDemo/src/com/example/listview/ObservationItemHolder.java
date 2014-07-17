package com.example.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.hamweather.aeris.response.ObservationResponse;
import com.hamweather.aeris.util.FileUtil;
import com.hamweather.aeris.util.WeatherUtil;

public class ObservationItemHolder implements
		AdapterHolder<ObservationResponse> {

	ImageView weatherIcon;
	TextView place;
	TextView time;
	TextView weatherDesc;
	TextView temp;

	@Override
	public View inflateview(LayoutInflater mInflater) {
		View v = mInflater.inflate(R.layout.listview_nearby_obs, null, false);
		weatherIcon = (ImageView) v.findViewById(R.id.ivNearbyIcon);
		weatherDesc = (TextView) v.findViewById(R.id.tvNearbyWeather);
		temp = (TextView) v.findViewById(R.id.tvNearbyTemp);
		time = (TextView) v.findViewById(R.id.tvNearbyTime);
		place = (TextView) v.findViewById(R.id.tvNearbyPlace);
		return v;
	}

	@Override
	public void populateView(ObservationResponse t, int position) {
		weatherIcon.setImageResource(FileUtil.getDrawableByName(
				t.getObservation().icon, weatherIcon.getContext()));
		place.setText(WeatherUtil.capitalize(t.getPlace().name));
		time.setText(WeatherUtil.getFormatFromISO(
				t.getObservation().dateTimeISO, "h:mm aa"));
		weatherDesc.setText(t.getObservation().weatherShort);
		// tempF can be null with a successful response
		if (t.getObservation().tempF == null) {
			temp.setText("--");
		} else {
			temp.setText(t.getObservation().tempF.toString());
		}

	}

}
