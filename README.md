AerisAndroidSDK
================

The Aeris Android SDK allows a developer to quickly and easily add weather content and functionality to their android applications. It utilizes the Aeris API backend for data loading and is built on top of an object mapping system that efficiently loads requested weather content into third-party Android applications, greatly reducing the amount of code and development needed on the developer end.

## Getting Started 

View the latest installation and implementation details at AerisWeather under the [Aeris Android SDK toolkit documentation] (http://www.aerisweather.com/support/docs/toolkits/aeris-android-sdk/getting-started/).

### Aeris API Configuration for the AerisDemo App
Before you can begin using the Aeris Android SDK in your project, you will need to download the latest version of the SDK and ensure that you have the required Aeris API keys for your application.

Step 1: Get the files.  Download the latest version of the Aeris Android SDK. 

Step 2: Get access to the Aeris API. 

To use the Aeris API, you will need to have valid access keys. Access keys are obtained by registering your application/namespace. To register your application, log in to Aeris Weather with your account and look for "APPS". 
Don't have an Aeris account? You can get one for free here.

Step 3: Determine which components of the Aeris Android SDK you need for your project:

### Aeris Core library
This is the base library for accessing the AerisWeather API. If you are planning to use the Aeris API for data without a map component, this is all you need.

### Aeris Map library
This library provides access to AerisWeather mapping features such as radar, satellite, warnings, etc. 
The map library includes a dependency to the Aeris Core library, so you won't need to download the Core library separately. If you are planning to use Aeris to create weather maps for your Android project, this is the library for you.
Step 4: Include the Aeris Android SDK files in your project's gradle.build file(s).

## Aeris Demo App
The SDK includes a demo app to help get you started. To allow the demo project to access data using the Aeris API, you will need to configure the Aeris Demo project to use your unique credentials. Just sign up for a free developer account and register yourself and your application with AerisWeather, to get your unique client Id and secret.

### Sign up for the Aeris API service. Developer accounts are Free.
Log in to your account to register your application for an API access key. Each application requires its own unique access key. Check out the API docs for more info.*

Add the client_id and the secret_key to the res/values/strings.xml of the Demo application. Specifically, the aeris_client_id and aeris_client_secret values.
* The DemoApp namespace/package name can be found around line 3 of the manifest.xml file and will look similiar too: **com.example.demoaerisproject**<br/><br/>

### Google Maps Configuration for the AerisDemo App
The Aeris Maps library project currently supports only Google maps. To use the Google Maps API, you'll need to register for a an API key in the Google Maps API Console. Visit the Google Maps Android API page and click the "GET A KEY"  button for detailed instructions on getting your Google Maps key. 

Once you have your Google Maps API account set up:

In the Services page, verify that the “Google Maps Android API v2” is enabled.
In the left navigation bar, click API Access.
In the resulting page, click Create New Android Key.
In the resulting dialog, enter the SHA-1 fingerprint, then a semicolon, then The Demo project’s package name. For example: FE:6D:DC:73:D5:22:C1:43:67:71:9F:65:93:AE:A6:66:6D:44:5A:75;com.example.demoaerisproject
The Google API Console responds by displaying Key for Android apps (with certificates) followed by a forty-character API key.

In your Android project, update the meta tag in the Demo Project’s Manifest:
```java
<meta-data
            android:name="com.google.android.maps.v2.API_KEY"
            android:value="your_api_key" />
```
Note: Your SHA1 can be obtained in Android Studio by running the **"signingReport"** task under the **"Gradle projects"** section.

### Permissions
The following permissions are required in order to use the Aeris Android SDK in the application. Please add these to your AndroidManifest.xml:
```java
<manifest>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_COURSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
</manifest>
```
### Gradle Configuration
The Aeris Core and Aeris Maps libraries are available through Maven Central. To add these dependencies to your project add these lines to your build.gradle file. 

**Aeris Core:**
```java
repositories {
    mavenCentral()
}
dependencies {
    compile 'com.aerisweather:aeris-android-lib:#.#.#@aar'
}
``` 

**Aeris Maps:**

(Note: you do not need to to add Aeris Core seperately if you are using Aeris Maps - the core lib is referenced in the maps lib)
```java
repositories {
    mavenCentral()
}
dependencies {
    compile ('com.aerisweather:aeris-maps-lib:#.#.#@aar') {
        transitive true
    }
    compile 'com.google.android.gms:play-services-maps:#.#.#'
    compile 'com.android.support:appcompat-v7:#.#.#'
}
``` 

##Reference Links

[Aeris API Docs](http://www.aerisweather.com/support/docs/api/).<br/>
[Aeris API Signup](http://www.aerisweather.com/signup/).<br/>
[Aeris Android SDK](http://www.aerisweather.com/support/docs/toolkits/aeris-android-sdk/).<br/>
[Aeris Android Core Library Javadocs](http://www.aerisweather.com/docs/android/Aeris/index.html).<br/>
[Aeris Android Maps](http://www.aerisweather.com/support/docs/toolkits/aeris-android-sdk/getting-started/weather-maps/).<br/>
[Aeris Android Maps Library Javadocs](http://www.aerisweather.com/docs/android/AerisMap/index.html).<br/>


