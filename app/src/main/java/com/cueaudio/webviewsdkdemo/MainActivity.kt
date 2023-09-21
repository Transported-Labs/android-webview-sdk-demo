package com.cueaudio.webviewsdkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cueaudio.webviewsdk.InvalidUrlError
import com.cueaudio.webviewsdk.WebViewController
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webViewController = WebViewController(this)
        val urlEditText = findViewById<EditText>(R.id.urlEditText)
        val openInBrowserButton = findViewById<Button>(R.id.openInChromeButton)
        openInBrowserButton.setOnClickListener {
            val url = urlEditText.text.toString()
            if (url == "") {
                println("Empty URL is not allowed")
                return@setOnClickListener
            }
            try {
                webViewController.openInChrome(url)
            } catch (e: InvalidUrlError) {
                // Show invalid URL error message
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        val navigateGeckoButton = findViewById<Button>(R.id.navigateGeckoButton)
        navigateGeckoButton.setOnClickListener {
            val url = urlEditText.text.toString()
            if (url == "") {
                println("Empty URL is not allowed")
                return@setOnClickListener
            }
            try {
                webViewController.navigateToGecko(url)
            } catch (e: InvalidUrlError) {
                // Show invalid URL error message
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
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
                // Show invalid URL error message
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        val openFileButton = findViewById<Button>(R.id.openFileButton)
        openFileButton.setOnClickListener {
            webViewController.navigateTo("file:///android_asset/index.html")
        }
        val openFileGeckoButton = findViewById<Button>(R.id.openFileGeckoButton)

        // Copy demo page to app's private storage
        // This is required because there is no way to match web extension's content_scripts
        // to resource://android/assets/... and jar:file://... URLs which are used for
        // communication between page and native app (by web extension in-between).
        assets.open("index.html").copyTo(File(filesDir, "index.html").outputStream())

        openFileGeckoButton.setOnClickListener {
            webViewController.navigateToGecko("file://${filesDir.absolutePath}/index.html")
        }
    }
}