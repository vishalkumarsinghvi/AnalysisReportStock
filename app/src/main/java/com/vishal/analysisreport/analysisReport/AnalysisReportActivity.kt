package com.vishal.analysisreport.analysisReport

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.vishal.analysisreport.databinding.ActivityAnalysisReportBinding


class AnalysisReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url: String? = intent.getStringExtra("url")
        url?.let {
            binding.webView.loadUrl(it)
            binding.webView.webViewClient = WebViewController()
            binding.webView.settings.javaScriptEnabled = true
        }
    }


    class WebViewController : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

}