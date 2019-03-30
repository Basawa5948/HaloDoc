package com.droid.basawa.halodoc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DisplayNews extends AppCompatActivity {

    WebView webView;
    private String url_to_show;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        progressDialog = new ProgressDialog(this);
        webView = (WebView) findViewById(R.id.displaynews);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        url_to_show = bundle.getString("Url");

        webView.setWebViewClient(new MyWebViewClient());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        openURL();
    }

    private void openURL() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 5000); // 5000 milliseconds delay
            webView.loadUrl(url_to_show);
            webView.requestFocus();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
