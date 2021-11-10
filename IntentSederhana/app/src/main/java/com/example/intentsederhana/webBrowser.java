package com.example.intentsederhana;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class webBrowser extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        final WebView web = (WebView) findViewById(R.id.web);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);

        Button go = (Button) findViewById(R.id.go);
        Button tutup = (Button) findViewById(R.id.tutup);

        tutup.setOnClickListener(view -> finish());
        final EditText urlnya = (EditText) findViewById(R.id.urlnya);

        go.setOnClickListener(view -> {
            String urlku = urlnya.getText().toString();
            Toast.makeText(getBaseContext(), "Load uri "+urlku, Toast.LENGTH_LONG).show();
            web.loadUrl(urlku);

        });

    }
}