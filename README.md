AerisAndroidLibrary
================

The Aeris Maps Android Library is a development toolkit for quickly integrating [HAMWeather's Aeris API](http://www.hamweather.com/support/documentation/aeris/) and map content into your Android applications in a very easy and flexible way.

This repository contains the release verion of the library (which contains its source code in the libs folder similar to Google Play Services), and a Demo project that shows different parts of the library and API that can be leveraged by this library project. 

The Maps Library utilizes the Aeris Core Android library as well. This jar can be used stand alone from the maps library, so if your project does not require maps, please use it instead. This can be found [here](http://www.hamweather.com/support/documentation/mobile/android/). 


## Getting Started 

### Configuring the Demo App 
1. The Aeris Maps Library project currently supports Google maps, but google maps must be configured for your work spaces SHA1 key. To do this you must setup Google Maps in the Google Maps API Console. You must configure this SHA1 [here](https://code.google.com/apis/console/?noredirect) in the console. Other instructions for configuring the Google Play Services are [here](https://developers.google.com/maps/documentation/android/start#install_and_configure_the_google_play_services_sdk). 
2. Google Play Services must be imported into the project workspace and added as a library to the AerisMapsLibrary-release project under properties -> Android -> Add.
3. Add the newly obtained API key from the google console to the Demo App's Android Manifest. 
``` xml
<meta-data
            android:name="com.google.android.maps.v2.API_KEY"
            android:value="your_api_key" />
```

### Manifest Permissions Required 
In order to use the Aeris Maps Library project in your own application these permissions are required in order to use the Aeris Android Framework in the application. Please add these to your AndroidManifest.xml

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

*Documentation for the Aeris Core Library is [here](http://www.hamweather.com/docs/android/Aeris/).<br/>
*Documentation for the Aeris Maps SDK Library is [here](http://www.hamweather.com/docs/android/AerisMap/).


