package com.cueaudio.webviewsdkdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cueaudio.webviewsdk.InvalidUrlError
import com.cueaudio.webviewsdk.WebViewController
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {

    private lateinit var urlEditText: EditText
    private lateinit var navigateButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webViewController = WebViewController(this)
        urlEditText = findViewById(R.id.urlEditText)
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
        val scanQRButton = findViewById<Button>(R.id.scanQRButton)
        scanQRButton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setBeepEnabled(false)
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
        }
        val prefetchButton = findViewById<Button>(R.id.prefetchButton)
        prefetchButton.setOnClickListener {
            val urlString = urlEditText.text.toString()
            val url = "${urlString}&preload=true"
            if (url == "") {
                println("Empty URL is not allowed")
                return@setOnClickListener
            }
            try {
                webViewController.prefetch(url) {
                    prefetchButton.text = "Fetched:$it%"
                }
            } catch (e: InvalidUrlError) {
                // Show invalid URL error message
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        navigateButton = findViewById(R.id.navigateButton)
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            val scannedData = result.contents
            urlEditText.setText(scannedData)
            if (scannedData.contains("https://")) {
                navigateButton.performClick()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}