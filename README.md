# WebViewSDK demo project for Android

This project is used with [framework library](https://github.com/Transported-Labs/android-webview-sdk) to demonstrate CUELive Lightshow 2 SDK using page loaded to Android WebView.

## Usage of android-webview-sdk
1.Add the JitPack repository to your **settings.gradle** file
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
2.Add the dependency to **build.gradle (Module:app)**. Set up actual android-webview-sdk [version](https://github.com/Transported-Labs/android-webview-sdk/tags) 
```kotlin
dependencies {
    implementation 'com.github.Transported-Labs:android-webview-sdk:0.0.4'
}
```

## Adding sample index.html
Download sample index.html  [index.html](https://github.com/Transported-Labs/android-webview-sdk-demo/blob/main/app/src/main/assets/index.html). 
Put it to project's assets folder.

## Calling SDK methods from your code
First add import-directive to your code file
```kotlin
import com.cueaudio.webviewsdk.InvalidUrlError
import com.cueaudio.webviewsdk.WebViewController
```

Then add the Button object with the following OnClickListener to open sample index.html file:
```kotlin
val openFileButton = findViewById<Button>(R.id.openFileButton)
openFileButton.setOnClickListener {
    webViewController.navigateTo("file:///android_asset/index.html")
}
```

Then add one more Button object and EditText for URL-address of the lightshow webpage. The following code for button OnClickListener will navigate to the given webpage:
```kotlin
val urlEditText = findViewById<EditText>(R.id.urlEditText)
val navigateButton = findViewById<Button>(R.id.navigateButton)
navigateButton.setOnClickListener {
    val url = urlEditText.text.toString()
    if (url == "") {
        println("Empty URL is not allowed")
        return@setOnClickListener
    }
    try {
        webViewController.navigateTo(url)
    } catch (e: InvalidUrlError) {
        println("Error occured: ${e.message}")
    }
}
```

## Usage of the local version of android-webview-sdk for debug purposes
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
4. Check **build.gradle (Module :app)** file. It's dependencies section should contain the following:
```kotlin
//    implementation 'com.github.Transported-Labs:android-webview-sdk:0.0.4'
// Comment out the line above and uncomment the line below to use local android-webview-sdk
    implementation files('../../android-webview-sdk/lib/build/outputs/aar/lib-debug.aar')

```
5. Build and run demo-project _android-webview-sdk-demo_ in Android Studio.
