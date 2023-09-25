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
    }
}