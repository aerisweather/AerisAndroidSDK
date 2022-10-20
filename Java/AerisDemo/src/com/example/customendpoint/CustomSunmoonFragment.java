package com.example.customendpoint;

import java.util.List;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.db.MyPlace;
import com.example.db.MyPlacesDb;
import com.example.demoaerisproject.R;
import com.example.fragment.RefreshInterface;
import com.aerisweather.aeris.communication.AerisCustomCommunicationTask;
import com.aerisweather.aeris.communication.AerisProgressListener;
import com.aerisweather.aeris.communication.AerisRequest;
import com.aerisweather.aeris.communication.CustomCallback;
import com.aerisweather.aeris.communication.Endpoint;
import com.aerisweather.aeris.communication.parameter.ParameterBuilder;

public class CustomSunmoonFragment extends Fragment implements
		RefreshInterface, CustomCallback, AerisProgressListener {

	private static final int NUMBER_OF_DAYS = 7;
	private ListView sunmoonListView;
	private CustomEndpointAdapter adapter;
	private AerisCustomCommunicationTask task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sunmoon, container,
				false);
		sunmoonListView = (ListView) rootView.findViewById(R.id.lvSunmoon);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		performRequest();
	}

	private void performRequest() {
		if (task == null) {
			MyPlacesDb db = new MyPlacesDb(getActivity());
			MyPlace place = db.getMyPlace();
			String id = ":auto";
			if (place != null) {
				id = place.getTextDisplay("");
			}
			ParameterBuilder builder = new ParameterBuilder()
					.withLimit(NUMBER_OF_DAYS).withFrom("now")
					.withTo("+" + NUMBER_OF_DAYS + "days");
			AerisRequest request = new AerisRequest(new Endpoint("sunmoon"),
					id, builder.build());
			request.withDebugOutput(true);
			task = new AerisCustomCommunicationTask(getActivity(), this,
					request);
			task.withProgress(this);
			task.execute();
		}
	}

	@Override
	public void refreshPressed() {
		performRequest();
	}

	@Override
	public void onResult(String custom, String response) {
		task = null;
		if ("sunmoon".equals(custom)) {
			CustomSunmoonResponse customResponse = new CustomSunmoonResponse();
			customResponse.fromJSON(response);
			if (customResponse.isSuccessful()
					&& customResponse.getError() == null) {
				List<CustomSunmoonModel> data = customResponse
						.getListOfResponse();
				if (data != null && data.size() > 0) {
					adapter = new CustomEndpointAdapter(data, getActivity());
					sunmoonListView.setAdapter(adapter);
				}
			}

		}
	}

	@Override
	public void showProgress() {
		if (getActivity() != null) {
			this.getActivity().setProgressBarIndeterminateVisibility(true);
			this.getActivity().setProgressBarIndeterminate(true);
		}
	}

	@Override
	public void hideProgress() {
		if (getActivity() != null)
			this.getActivity().setProgressBarIndeterminateVisibility(false);
	}
}
