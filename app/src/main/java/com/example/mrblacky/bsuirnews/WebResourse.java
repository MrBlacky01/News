package com.example.mrblacky.bsuirnews;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class  WebResourse extends AppCompatActivity {

    private WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_resourse);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String product = extras.getString("HrefValue");
            //TextView tv = (TextView) findViewById(R.id.href);
            //tv.setText(product  );
            browser=(WebView)findViewById(R.id.webBrowser);
            browser.setWebViewClient(new WebViewClientForDoPage());

            browser.getSettings().setBuiltInZoomControls(true);
            browser.getSettings().setSupportZoom(true);
            browser.getSettings().setUseWideViewPort(true);
            //browser.getSettings().setLoadWithOverviewMode(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                browser.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                browser.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }

            try
            {
                browser.loadUrl(product);
            }
            catch (Exception exept)
            {

            }

        }
    }

    @Override
    public void onBackPressed() {
        if(browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
