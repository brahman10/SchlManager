package com.example.schlmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    TextView txname , txdate;
    WebView mWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle extras = getIntent().getExtras();
        final String name = extras.getString("Name");
        final String url = extras.getString("Url");
        final String dop = extras.getString("dop");

        txname = findViewById(R.id.name);
        txdate = findViewById(R.id.date);
        mWeb=findViewById(R.id.webview);

        txname.setText(name+" ");
        txdate.setText(dop+" ");
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        mWeb.getSettings().setSupportZoom(true);
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.loadUrl(url);


    }
}