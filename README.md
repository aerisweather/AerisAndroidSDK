AerisAndroidSDK
================
![Aeris SDK](http://www.hamweather.com/blog/wp-content/uploads/2014/05/AerisAndroid-BlogEntryHeader.png)

The Aeris Maps Android Library is a development toolkit for quickly integrating [AerisWeather's Aeris API](http://www.aerisweather.com/support/documentation/aeris/) and map content into your Android applications in an easy and flexible way.

This repository contains the release verion of the library (which contains class files in the libs folder similar to Google Play Services), and a Demo project that shows different parts of the library and API that can be leveraged by this library project. 

## Getting Started 

View the latest installation instructions via the [developers site](http://www.aerisweather.com/support/docs/toolkits/aeris-android-sdk/getting-started/).


#### Aeris API Configuration for the AerisDemo App
To access data using the Aeris API, You will need to configure the Aeris Demo project to use your unique credentials in order to access the Aeris API. This requires you to [register](http://www.aerisweather.com/signup/) yourself and your application with the Aeris API, which will provide you with a unique client ID (to identify you) and a secret key (to identify your application). If you have not yet registered your android application with Aeris, you will need to [register](http://www.aerisweather.com/signup/).

1. [Sign up](http://www.aerisweather.com/signup/) for the Aeris API service. Developer accounts are Free.
2. Log in to your account to register your application for an API access key. Each application requires its own unique access key. Check out the [API docs](http://www.aerisweather.com/support/docs/api/)  for more info.
   * The DemoApp namespace/package name can be found around line 3 of the manifest.xml file and will look similiar too: **com.example.demoaerisproject**<br/><br/>
3. Add the client_id and the secret_key to the res/values/strings.xml of the Demo application. Specifically, the aeris_client_id and aeris_client_secret values.

#### Google Maps Configuration for the AerisDemo App
1. The Aeris Maps Library project currently supports Google maps, but google maps must be configured for your work spaces SHA1 key. To do this you must setup Google Maps in the Google Maps API Console. You must configure this SHA1 [here](https://code.google.com/apis/console/?noredirect) in the console. 
2. In the Services page, verify that the “Google Maps Android API v2” is enabled.
3. In the left navigation bar, click API Access.
4. In the resulting page, click Create New Android Key.
5. In the resulting dialog, enter the SHA-1 fingerprint, then a semicolon, then The Demo project’s package name. For example:<br>BB:0D:AC:74:D3:21:E1:43:67:71:9B:62:91:AF:A1:66:6E:44:5D:75;com.example.demoaerisproject<br>
6. The Google API Console responds by displaying Key for Android apps (with certificates) followed by a forty-character API key, for example:<br>AIzaSyBdVl-cTICSwYKrZ95SuvNw7dbMuDt1KG0<br>
7. Update the meta tag in the Demo Project’s Manifest:
``` xml
<meta-data
            android:name="com.google.android.maps.v2.API_KEY"
            android:value="your_api_key" />
```
NOTE: Your SHA1 can be obtained via the eclipse environment **_Windows -> Preference -> Android -> Build_**

These permissions are required in order to use the Aeris Android SDK in the application. Please add these to your AndroidManifest.xml:
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
That’s it, the AerisDemo application should be ready to test! Review the [Getting Started](http://www.hamweather.com/support/documentation/mobile/android/starting/) section on the developers site.


#### Gradle Configuration
Importing Aeris Maps Library is available through maven central in gradle builds. These lines can be added to your build.gradle file. 
```
repositories {
    mavenCentral()
}
dependencies {
    compile 'com.hamweather:aeris-maps-library:1.1.1@aar'
}
```

## Documentation

[Aeris API Docs](http://www.aerisweather.com/support/docs/api/).<br/>
[Aeris API Signup](http://www.aerisweather.com/signup/).<br/>
[Aeris Android Library Docs](http://www.aerisweather.com/support/docs/toolkits/aeris-android-sdk/).<br/>
[Aeris Android Library Javadocs](http://www.aerisweather.com/docs/android/Aeris/index.html).<br/>
[Aeris Maps SDK Library Docs](http://www.aerisweather.com/support/docs/toolkits/aeris-android-sdk/getting-started/weather-maps/).<br/>
[Aeris Maps SDK Library Javadocs](http://www.aerisweather.com/docs/android/AerisMap/index.html).<br/>


