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
    implementation 'com.github.Transported-Labs:android-webview-sdk:0.0.5'
}
```

## Integration

Simply execute the following code:

```kotlin
        navigateButton.setOnClickListener {
            val url = "<your URL from CUE>"
            try {
                webViewController.navigateTo(url)
            } catch (e: InvalidUrlError) {
                // Show invalid URL error message
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
```
## Pre-fetch

To pre-fetch lightshow resources is very similar to navigation, but we should keep sdkController hidden and add to URL preload parameter.
Just execute the following code:

```kotlin
        prefetchButton.setOnClickListener {
            val urlString = "<your URL from CUE>"
            val url = "${urlString}&preload=true"
            try {
                webViewController.prefetch(url) {
                    // You can get progress from 0 to 100 during the pre-fetch process
                    prefetchButton.text = "Fetched:$it%"
                }
            } catch (e: InvalidUrlError) {
                // Show invalid URL error message
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
```
