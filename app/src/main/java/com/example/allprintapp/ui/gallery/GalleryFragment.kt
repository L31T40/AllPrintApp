package com.example.allprintapp.ui.gallery

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.allprintapp.R
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private val url = "http://app.allprint.pt/"


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {

   galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)

        galleryViewModel.text.observe(viewLifecycleOwner, Observer {

            textView.text = it

            val settings = webview.settings

            // Get the web view settings instance


            // Enable java script in web view
            settings.javaScriptEnabled = true

            // Enable and setup web view cache

            val appCachePath = activity!!.cacheDir.absolutePath
//            webSettings.setAppCachePath(appCachePath)

            settings.setAppCacheEnabled(true)
            settings.cacheMode = WebSettings.LOAD_DEFAULT
//            settings.setAppCachePath(cacheDir.path)
            settings.setAppCachePath(appCachePath)


            // Enable zooming in web view
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = true


            // Zoom web view text
            //settings.textZoom = 125


            // Enable disable images in web view
            settings.blockNetworkImage = false
            // Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true


            // More web view settings
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true  // api 26
            }
            //settings.pluginState = WebSettings.PluginState.ON
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false

            // More optional settings, you can enable it by yourself
            settings.domStorageEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.loadWithOverviewMode = true
            settings.allowContentAccess = true
            settings.setGeolocationEnabled(true)
            settings.allowUniversalAccessFromFileURLs = true
            settings.mixedContentMode = MIXED_CONTENT_COMPATIBILITY_MODE

            settings.allowFileAccess = true

            // WebView settings
            webview.fitsSystemWindows = true
            webview.getSettings().setBuiltInZoomControls(true)
            webview.getSettings().setDisplayZoomControls(false)

            /* if SDK version is greater of 19 then activate hardware acceleration
            otherwise activate software acceleration  */

            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            webview.loadUrl(url)

            // Set web view client
            webview.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    // Page loading started
                    // Do something
                }

                override fun onPageFinished(view: WebView, url: String) {
                    // Page loading finished
                    // Enable disable back forward button
                }
            }


        })






        return root
    }

}
