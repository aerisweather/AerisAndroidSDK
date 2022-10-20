package com.example.customendpoint;

import com.aerisweather.aeris.communication.AerisCustomResponse;

public class CustomSunmoonResponse extends
		AerisCustomResponse<CustomSunmoonModel> {

	public CustomSunmoonResponse() {
		super(CustomSunmoonModel.class);
	}

}
