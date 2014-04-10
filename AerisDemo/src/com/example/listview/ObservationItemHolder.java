package com.example.listview;

import org.apache.commons.lang3.text.WordUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoaerisproject.R;
import com.example.util.FormatUtil;
import com.hamweather.aeris.response.ObservationResponse;
import com.hamweather.aeris.util.FileUtil;

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
		place.setText(WordUtils.capitalize(t.getPlace().name));
		time.setText(FormatUtil.getTimehhmmFromISO(t.getObservation().dateTimeISO));
		weatherDesc.setText(t.getObservation().weatherShort);
		temp.setText(t.getObservation().tempF.toString());
	}

}
