package com.example.matthes.farmero;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class fieldMap extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //WebView myWebView = (WebView) findViewById(R.id.webview);
        //WebSettings webSettings = myWebView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        //myWebView.setWebChromeClient(new WebChromeClient());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //myWebView.loadUrl("http://www.example.com");
        myWebView.loadUrl("http://farmero.appspot.com/");
    }

}
