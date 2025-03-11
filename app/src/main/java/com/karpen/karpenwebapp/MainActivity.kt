package com.karpen.karpenwebapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var imgView: ImageView

    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.textView)
        imgView = findViewById(R.id.imageView)

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        webView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        textView.visibility = View.VISIBLE
        imgView.visibility = View.VISIBLE

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (isNetworkAvailable()) {
                webView.settings.javaScriptEnabled = true
                webView.loadUrl("https://karpendev.ru/mobile")

                webView.webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: android.webkit.WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        Toast.makeText(applicationContext, "Error page loading", Toast.LENGTH_LONG).show()
                        showErrorUI()
                    }

                    override fun onReceivedHttpError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        errorResponse: android.webkit.WebResourceResponse?
                    ) {
                        super.onReceivedHttpError(view, request, errorResponse)
                        Toast.makeText(applicationContext, "Error http loading", Toast.LENGTH_LONG).show()
                        showErrorUI()
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        progressBar.visibility = View.VISIBLE
                        textView.visibility = View.GONE
                        imgView.visibility = View.GONE
                        webView.visibility = View.GONE
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        progressBar.visibility = View.GONE
                        textView.visibility = View.GONE
                        imgView.visibility = View.GONE
                        webView.visibility = View.VISIBLE
                    }
                }
            } else {
                Toast.makeText(this, "Connection is unavailable", Toast.LENGTH_LONG).show()
                showErrorUI()
            }
        }, 2000)
    }

    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    @SuppressLint("SetTextI18n")
    private fun showErrorUI() {
        webView.visibility = View.GONE
        progressBar.visibility = View.GONE
        textView.visibility = View.VISIBLE
        imgView.visibility = View.VISIBLE
        textView.text = "Error page loading"
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
