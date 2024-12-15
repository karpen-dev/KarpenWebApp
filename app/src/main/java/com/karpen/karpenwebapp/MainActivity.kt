package com.karpen.karpenwebapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.karpen.karpenwebapp.ui.theme.KarpenWebAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://res.karpendev.ru/mobile/")

        webView.setVisibility(View.GONE)
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                progressBar.visibility = View.VISIBLE
                webView.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        } else{
            super.onBackPressed()
        }
    }
}