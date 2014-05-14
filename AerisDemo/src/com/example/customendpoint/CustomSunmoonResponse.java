package com.example.customendpoint;

import com.hamweather.aeris.communication.AerisCustomResponse;

public class CustomSunmoonResponse extends
		AerisCustomResponse<CustomSunmoonModel> {

	public CustomSunmoonResponse() {
		super(CustomSunmoonModel.class);
	}

}
