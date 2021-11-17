package com.example.kamera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
     Button bl;
     ImageView iv;
    private static final int kodekamera = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bl = (Button) findViewById(R.id.button);
        iv = (ImageView) findViewById(R.id.imageView);

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(it,
                        kodekamera);
            }
        });
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case(kodekamera) : prosesKamera(data); break;
            }
        }
    }
    private void prosesKamera(Intent datanya ){
        Bitmap bm;
        bm = (Bitmap) datanya.getExtras().get("data");
        iv.setImageBitmap(bm);
        Toast.makeText(this, "Data telah terload ke ImageView", Toast.LENGTH_SHORT).show();
    }
}