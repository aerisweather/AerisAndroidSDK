package com.example.fragment;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.example.fragment.HeadlessFragment.HeadlessObserver;
import com.aerisweather.aeris.communication.AerisCallback;
import com.aerisweather.aeris.communication.AerisProgressListener;
import com.aerisweather.aeris.communication.BatchCallback;
import com.aerisweather.aeris.communication.EndpointType;
import com.aerisweather.aeris.model.AerisBatchResponse;
import com.aerisweather.aeris.model.AerisError;
import com.aerisweather.aeris.model.AerisResponse;

public abstract class AerisFragment extends Fragment implements
		AerisProgressListener, HeadlessObserver, AerisCallback, BatchCallback,
		RefreshInterface {

	@Override
	public void refreshPressed() {
		performRequest();
	}

	protected HeadlessFragment headlessFragment;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		headlessFragment = HeadlessFragment.getFragment(activity);
		HeadlessFragment.addObserver(this);

	}

	@Override
	public void onDetach() {
		super.onDetach();
		HeadlessFragment.removeObserver(this);

	}

	@Override
	public void notifyDataChanged() {
		loadData(false);
	}

	@Override
	public void showProgress() {
		if (getActivity() != null) {
			this.getActivity().setProgressBarIndeterminateVisibility(true);
			this.getActivity().setProgressBarIndeterminate(true);
		}
	}

	protected void handleError(AerisError error) {
		if (error != null && getActivity() != null) {
			Toast.makeText(getActivity(),
					error.code + ": " + error.description, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onBatchResponse(AerisBatchResponse response) {
	}

	@Override
	public void onResult(EndpointType endpoint, AerisResponse response) {
	}

	public void loadData(boolean withRequest) {
		if (getKey() == null) {
			return;
		}
		if (getEndpointType() != null) {
			if (headlessFragment.getResponse(getKey()) != null) {
				AerisResponse response = (AerisResponse) headlessFragment
						.getResponse(getKey());
				onResult(getEndpointType(), response);
			} else {
				if (withRequest)
					performRequest();

			}
		} else {
			if (headlessFragment.getResponse(getKey()) != null) {
				AerisBatchResponse response = (AerisBatchResponse) headlessFragment
						.getResponse(getKey());
				onBatchResponse(response);
			} else {
				if (withRequest)
					performRequest();
			}
		}
	}

	abstract void performRequest();

	abstract EndpointType getEndpointType();

	abstract String getKey();

	@Override
	public void onResume() {
		super.onResume();
		loadData(true);

	}

	@Override
	public void hideProgress() {
		if (getActivity() != null)
			this.getActivity().setProgressBarIndeterminateVisibility(false);
	}
}
