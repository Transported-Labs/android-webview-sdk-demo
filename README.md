# CueLightShow demo project for Android

This project is used with [framework library](https://github.com/Transported-Labs/android-webview-sdk) to demonstrate CUELive Lightshow 2 SDK.

## Usage of CueLightShow library
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
    implementation 'com.github.Transported-Labs:android-webview-sdk:0.0.6'
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

To pre-fetch lightshow resources is very similar to navigation.
Just execute the following code:

```kotlin
        prefetchButton.setOnClickListener {
            val url = "<your URL from CUE>"
            try {
                webViewController.prefetch(url) { logLine ->
                    // You can get progress from the pre-fetch process
                    // Add logLine string to your progress log
                    logText.text.appendLine(logLine)
                }
            } catch (e: InvalidUrlError) {
                // Show invalid URL error message
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
```
