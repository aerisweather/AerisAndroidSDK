AerisAndroidLibrary
================

The Aeris Maps Android Library is a development toolkits for quickly integrating HAMWeather's Aeris API and map content into your Android applications in a very easy and flexible way.

This repository contains the release verion of the library (which contains its source code in the libs folder similar to Google Play Services), and a Demo project that shows different parts of the library and API that can be leveraged by this library project. 

The Maps Library utilizes the Aeris Core Android library as well. This jar can be used stand alone from the map library, so if your project does not require maps, please use it instead. This can be found [here](http://www.hamweather.com/support/documentation/mobile/android/).


## Getting Started 

### Manifest Permissions Required 
These permissions are required in order to use the Aeris Android Framework in the application. Please add these to your AndroidManifest.xml

``` xml
<manifest>
  <!-- Internet is required to make calls to the Aeris API -->
	<uses-permission android:name="android.permission.INTERNET" />
	 <!-- Aeris Library uses the network state to determine if network is availabe to make calls  -->
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  <!-- Google maps requries this now with tiles -->
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <!-- (Optional If you want location services to be used as well) -->
  <uses-permission android:name="android.permission.ACCESS_COURSE_LOCATION" />
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

</manifest>
```

## Documentation 

Documentation for the Aeris Core Library [here](http://www.hamweather.com/docs/android/Aeris/)<br/>
Documentation for the Aeris Maps SDK Library [here](http://www.hamweather.com/docs/android/AerisMap/)


