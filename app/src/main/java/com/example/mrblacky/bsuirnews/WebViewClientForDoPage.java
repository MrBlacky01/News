package com.example.mrblacky.bsuirnews;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Mr.Blacky on 29.07.2016.
 */
class WebViewClientForDoPage extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        view.loadUrl(url);
        return true;
    }


}
