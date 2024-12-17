package com.karpen.karpenwebapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var imgView: ImageView
    private lateinit var gitButton: Button

    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.textView)
        imgView = findViewById(R.id.imageView)
        gitButton = findViewById(R.id.button)

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        webView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        textView.visibility = View.VISIBLE
        imgView.visibility = View.VISIBLE
        gitButton.visibility = View.VISIBLE

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            webView.settings.javaScriptEnabled = true
            webView.loadUrl("https://res.karpendev.ru/mobile/")

            webView.setWebViewClient(object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    progressBar.visibility = View.VISIBLE
                    textView.visibility = View.GONE
                    imgView.visibility = View.GONE
                    gitButton.visibility = View.GONE
                    webView.visibility = View.GONE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.visibility = View.GONE
                    textView.visibility = View.GONE
                    imgView.visibility = View.GONE
                    gitButton.visibility = View.GONE
                    webView.visibility = View.VISIBLE
                }
            })
        }, 2000)


        gitButton.setOnClickListener(){
            openBrowser("https://karpendev.ru/git")
        }
    }

    private fun openBrowser(url: String){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
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