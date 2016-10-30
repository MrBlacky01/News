package com.example.mrblacky.bsuirnews;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebViewClientForDoPage extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        view.loadUrl(url);
        return true;
    }

}
