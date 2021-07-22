package com.anggaari.showcase.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.anggaari.showcase.databinding.ActivityWebViewerBinding

class WebViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewerBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewerBinding.inflate(layoutInflater)

        val myWebView: WebView = binding.webview
        val url = intent.getStringExtra("url")
        if (url.isNullOrEmpty()) {
            finish()
        } else {
            myWebView.webViewClient = WebViewClient()
            myWebView.settings.javaScriptEnabled = true
            myWebView.loadUrl(url)
        }

        setContentView(binding.root)
    }
}