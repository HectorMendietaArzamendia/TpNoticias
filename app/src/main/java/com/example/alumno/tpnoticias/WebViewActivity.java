package com.example.alumno.tpnoticias;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Web");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        WebView wv = (WebView) findViewById(R.id.wvNoticia);
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(i.getStringExtra("url"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
