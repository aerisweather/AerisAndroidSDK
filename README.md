AerisAndroidLibrary
================
![Aeris SDK](http://www.hamweather.com/blog/wp-content/uploads/2014/05/AerisAndroid-BlogEntryHeader.png)

The Aeris Maps Android Library is a development toolkit for quickly integrating [HAMWeather's Aeris API](http://www.hamweather.com/support/documentation/aeris/) and map content into your Android applications in a very easy and flexible way.

This repository contains the release verion of the library (which contains class files in the libs folder similar to Google Play Services), and a Demo project that shows different parts of the library and API that can be leveraged by this library project. 

The Maps Library utilizes the Aeris Core Android library as well. This jar can be used stand alone from the maps library, so if your project does not require maps, please use it instead. This can be found [here](http://www.hamweather.com/products/aeris-mobile/pricing/). 


## Getting Started 

### Configuring the Demo App 
View the latest installation instructions via the [developers site](http://www.hamweather.com/support/documentation/mobile/android/installation-maps-demo/).


#### Installation of SDK and Demo Project from Github source (Eclipse)

1. Clone the latest version of the AerisDemo or download the latest zip file of the repository. If downloading the zip, extract the files to a location on your computer.
2. In the Eclipse menu Go to **_File > Import_** and select **_Android -> Existing Android Projects into Workspace_** and click next.<br/>
![Import existing code](http://www.hamweather.com/images/docs/aeris-android/import_existing_code.png) 
3. Click the radio button "**_Select root directory_**" and click browse. Then browse to the location of the of the Aeris Maps source code and select that folder. Press OK. Make sure both projects are checked. Click finish.<br/>
![Import Aeris Demo Maps](http://www.hamweather.com/images/docs/aeris-android/import_aerisdemo_maps.png)
4. The AerisMapsSDK-release Android project lib now needs to inherit the google-play-services_lib.  This library can be imported just like importing the AerisDemo and AerisMapsLibrary projects. Make sure the Android SDK has the latest version of the google play services. The Aeris Maps Library uses version name 4.2.42 of the google play services currently. For directions on setting up Google Play Services, view the documentation, specifically under "Install the Google Play Services SDK
5. After the google play services library has been imported into the workspace, right click on AerisMapsSDK-release and click properties.
6. Click on Android in the menu. Under the library section, make sure that "Is Libary"  is checked and press the add button and select the Google-play-services_lib to the project and press ok.Press Apply and Ok.<br/>
![Google Play](http://www.hamweather.com/images/docs/aeris-android/google_play.png)
7. Finally, right click on your DemoAerisProject and add the AerisMapsSDK-release to it just as you added the google-play-services_lib to the AerisMapsSDK. This is also how you would add the Android Maps library to a project you wish to use it with.

#### Aeris API Configuration for the AerisDemo App
You will need to configure AerisDemo to use your unique credentials in order to access the Aeris API. This requires you to [register](http://www.hamweather.com/support/documentation/aeris/) yourself and your application with the Aeris API, which will provide you with a unique client ID (to identify you) and a secret key (to identify your application). If you have not yet registered your android application with Aeris, you will need to [register](http://www.hamweather.com/support/documentation/aeris/).

1. [Sign up](http://www.hamweather.com/products/aeris-api/pricing/) for the Aeris API service. Developer accounts are Free.
2. [Log in](http://www.hamweather.com/account/member) to your account to register your application for an API access key. Each application requires its own unique access key. Refer to this [knowledge base article](http://helpdesk.hamweather.com/entries/20793392-How-do-I-access-the-Aeris-API-now-that-I-ve-signed-up-for-an-account-) for further information.
   * The DemoApp namespace/package name can be found around line 3 of the manifest.xml file and will look similiar too: **com.example.demoaerisproject**
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


## Complete Documentation 

Documentation for the Aeris Core Library is [here](http://www.hamweather.com/docs/android/Aeris/).<br/>
Documentation for the Aeris Maps SDK Library is [here](http://www.hamweather.com/docs/android/AerisMap/).


