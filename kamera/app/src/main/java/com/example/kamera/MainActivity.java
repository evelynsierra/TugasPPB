package com.example.kamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
            File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Hasil Foto");
            Date d = new Date();
                    CharSequence s  =
                    android.text.format.DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
                    File image = new File(imagesFolder, s.toString() + ".jpg");
                Uri uriSavedImage = Uri.fromFile(image);
                    it.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                    startActivityForResult(it, 0);
            }
        });
    }
}