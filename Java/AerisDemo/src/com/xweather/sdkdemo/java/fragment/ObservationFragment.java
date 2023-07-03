package com.xweather.sdkdemo.java.fragment;

import java.util.Locale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerisweather.aeris.model.ConditionPeriod;
import com.aerisweather.aeris.response.ConditionsResponse;
import com.xweather.sdkdemo.java.demoaerisproject.R;
import com.xweather.sdkdemo.java.view.DayNightView;
import com.xweather.sdkdemo.java.view.SmallForecastView;
import com.xweather.sdkdemo.java.view.TwoPartView;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.model.ForecastPeriod;
import com.aerisweather.aeris.model.Place;
import com.aerisweather.aeris.response.ForecastsResponse;
import com.aerisweather.aeris.response.PlacesResponse;
import com.aerisweather.aeris.util.FileUtil;
import com.aerisweather.aeris.util.WeatherUtil;

public class ObservationFragment extends AerisFragment {

    private static final String N_A = "N/A";
    private TextView placeTextView;
    private TextView tempTextView;
    private ImageView iconImageView;
    private TextView weatherShortTextView;
    private TextView feelslikeTextView;

    private TwoPartView humidityView;
    private TwoPartView windsView;
    private TwoPartView dewPointView;
    private TwoPartView pressureView;

    private DayNightView todayView;
    private DayNightView nightView;

    private LinearLayout forecastsLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_observation,
                container, false);

        placeTextView = rootView.findViewById(R.id.tvPlace);
        tempTextView = rootView.findViewById(R.id.tvTemperature);
        iconImageView = rootView.findViewById(R.id.ivWeatherIcon);
        weatherShortTextView = rootView
                .findViewById(R.id.tvWeatherShort);
        feelslikeTextView = rootView.findViewById(R.id.tvFeelsLike);
        humidityView = rootView.findViewById(R.id.viewHumidity);
        windsView = rootView.findViewById(R.id.viewWinds);
        dewPointView = rootView.findViewById(R.id.viewDewPoint);
        pressureView = rootView.findViewById(R.id.viewPressure);
        todayView = rootView.findViewById(R.id.viewToday);
        nightView = rootView.findViewById(R.id.viewTonight);
        forecastsLayout = rootView
                .findViewById(R.id.llObservationForecasts);
        return rootView;
    }

    @Override
    public void showProgress() {
        super.showProgress();

    }

    @Override
    public void hideProgress() {
        super.hideProgress();

    }


    private void setCondition(ConditionPeriod condPeriod) {
        tempTextView.setText(WeatherUtil.appendDegree(Math.round(condPeriod.tempF.doubleValue())));
        weatherShortTextView.setText(condPeriod.weatherPrimary);
        feelslikeTextView.setText("Feels like "
                + WeatherUtil.appendDegree(Math.round(condPeriod.feelslikeF.doubleValue())));
        iconImageView.setImageResource(FileUtil.getDrawableByName(condPeriod.icon,
                requireActivity()));

        if (condPeriod.humidity != null) {
            humidityView.setText("Humidity", condPeriod.humidity.intValue() + "%");
        } else {
            humidityView.setText("Humidity", N_A);
        }

        dewPointView.setText("Dew Point",
                WeatherUtil.appendDegree(Math.round(condPeriod.dewpointF.doubleValue())));

        if (condPeriod.pressureIN != null) {
            pressureView.setText("Pressure", condPeriod.pressureIN.doubleValue()
                    + " IN");
        } else {
            pressureView.setText("Pressure", N_A);
        }

        if (condPeriod.windDir != null) {
            windsView.setText("Winds", String.format(Locale.ENGLISH,
                    "%s %d mph", condPeriod.windDir, condPeriod.windSpeedMPH.intValue()));
        } else {
            windsView.setText("Winds", N_A);
        }

    }


    private void setPlace(Place place) {
        String temp = String.format("%s, %s, %s", place.name, place.state,
                place.country);
        placeTextView.setText(temp);

    }

    @Override
    public void onBatchResponse(AerisBatchResponse response) {
        if (todayView == null) {
            return;
        }
        if (response.isSuccessful() && response.getError() == null) {
            ConditionsResponse condResponse = new ConditionsResponse(
                    response.responses.get(0).getFirstResponse());
            ConditionPeriod condPeriod = condResponse.getPeriod(0);
            setCondition(condPeriod);

            PlacesResponse pResponse = new PlacesResponse(response.responses
                    .get(1).getFirstResponse());
            setPlace(pResponse.getPlace());

            ForecastsResponse fResponse = new ForecastsResponse(
                    response.responses.get(2).getFirstResponse());

            String dayTitle1 = "Today";
            String dayTitle2 = "Tonight";
            if (!fResponse.getPeriod(0).isDay) {
                dayTitle1 = "Tonight";
                dayTitle2 = "Tomorrow";
            }
            todayView.setPeriod(fResponse.getPeriod(0), dayTitle1);
            nightView.setPeriod(fResponse.getPeriod(1), dayTitle2);

            ForecastsResponse hoursResponse = new ForecastsResponse(
                    response.responses.get(3).getFirstResponse());

            forecastsLayout.removeAllViews();
            for (ForecastPeriod p : hoursResponse.getPeriods()) {
                SmallForecastView view = new SmallForecastView(getActivity());
                view.setForecast(p);
                forecastsLayout.addView(view);
            }

            HeadlessFragment headless = HeadlessFragment
                    .getFragment(requireActivity());
            headless.storeResponse(HeadlessFragment.DETAILED_OBSERVATION,
                    response);
        } else {
            handleError(response.getError());
        }
    }

    @Override
    void performRequest() {
        headlessFragment.performDetailedObservation(this);

    }

    @Override
    EndpointType getEndpointType() {
        return null;
    }

    @Override
    String getKey() {
        return HeadlessFragment.DETAILED_OBSERVATION;
    }
}
