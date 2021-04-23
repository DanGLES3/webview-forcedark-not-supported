package ru.kuchanov.nightmode

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import ru.kuchanov.nightmode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener { switchAppTheme() }

        initWebView()

        setWebViewTheme()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.webViewClient = WebViewClient()
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

//        binding.webView.loadUrl("https://vk.com/")
        loadContentInWebView(binding.webView)
        loadContentInWebView(binding.webView1)
    }

    private fun loadContentInWebView(webView: WebView) {
        val htmlString = StorageUtils.readFromAssets(this, "table.html")
        val base64 = Base64.encodeToString(htmlString.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
        webView.loadData(base64, "text/html; charset=UTF-8", "base64")
    }

    private fun switchAppTheme() {
        val appTheme = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(appTheme)
    }

    private fun setWebViewTheme() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            val themeForWebView = if (isNight()) {
                WebSettingsCompat.FORCE_DARK_ON
            } else {
                WebSettingsCompat.FORCE_DARK_OFF
            }
            WebSettingsCompat.setForceDark(binding.webView.settings, themeForWebView)
            WebSettingsCompat.setForceDark(binding.webView1.settings, themeForWebView)
        } else {
            Toast.makeText(
                this,
                "WebViewFeature.FORCE_DARK is not supported!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isNight(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }
}

