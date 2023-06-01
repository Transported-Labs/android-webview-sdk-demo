# WebViewSDK demo project for Android

This project is used with [framework library](https://github.com/Transported-Labs/android-webview-sdk) to demostrate the usage of camera torch SDK from page loaded to Android WebView.

## Usage
1. Clone both _android-webview-sdk_ and _android-webview-sdk-demo_ in the same directory:
```
    android-webview-sdk
    android-webview-sdk-demo
```
2. Open Terminal in location of _android-webview-sdk_ and run the following command to build library:
```
./gradlew assemble
```
3. Open demo-project from _android-webview-sdk-demo_ in Android Studio 2022.1.1+:
4. Check **build.gradle (Module :app)** file. It's dependencies section should contain the line
```kotlin
    implementation files('../../android-webview-sdk/lib/build/outputs/aar/lib-debug.aar')
```
4. Build and run demo-project _android-webview-sdk-demo_ in Android Studio.
