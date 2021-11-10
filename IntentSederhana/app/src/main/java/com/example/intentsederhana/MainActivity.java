package com.example.intentsederhana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bKontak = (Button) findViewById(R.id.bukakontak);
        Button bGambar = (Button) findViewById(R.id.bukagambar);
        Button bMusik = (Button) findViewById(R.id.bukamusik);
        Button bBrowser = (Button) findViewById(R.id.bukawebbrowser);
        Button bYoutube = (Button) findViewById(R.id.bukayoutube);

        bKontak.setOnClickListener(operasi);
        bGambar.setOnClickListener(operasi);
        bMusik.setOnClickListener(operasi);
        bYoutube.setOnClickListener(operasi);
        bBrowser.setOnClickListener(operasi);

    }
    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.bukakontak:bukaKontak();break;
                case R.id.bukagambar:break;
                case R.id.bukamusik:break;
                case R.id.bukayoutube:bukaYoutube();break;
                case R.id.bukawebbrowser:bukaBrowser();break;
            }
        }
    };


    void bukaKontak(){
        Intent intentku = new Intent(getBaseContext(),kontak.class);
        startActivityForResult(intentku,0);
    }

    void bukaYoutube(){
        Intent intentku = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com"));

        startActivity(intentku);
    }

    private void bukaBrowser() {
        Intent intentku = new Intent(getBaseContext(), webBrowser.class);
        startActivityForResult(intentku, 0);
    }
 }