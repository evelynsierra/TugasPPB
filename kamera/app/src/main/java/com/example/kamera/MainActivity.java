package com.example.kamera;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
     Button bl;
     ImageView iv;
     String nmFile;
    private static final int kodekamera = 222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE = 223;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askWritePermission();
        setContentView(R.layout.activity_main);

        bl = (Button) findViewById(R.id.button);
        iv = (ImageView) findViewById(R.id.imageView);

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Hasil Foto");
                imagesFolder.mkdirs();
                Date d = new Date();
                CharSequence s = android.text.format.DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
                nmFile = imagesFolder + File.separator + s.toString() + ".jpg";
                File image = new File(nmFile);

                Uri uriSavedImage = Uri.fromFile(image);
                it.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(it,
                        kodekamera);
            }
        });
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode) {
                case(kodekamera) : prosesKamera(data); break;
            }
        }
    }
    private void prosesKamera(Intent datanya) {
        Bitmap bm;
       // bm = (Bitmap) datanya.getExtras().get("data");
        BitmapFactory.Options options;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bm = BitmapFactory.decodeFile(nmFile, options);
        iv.setImageBitmap(bm);

        Toast.makeText(this, "Data telah terload ke ImageView", Toast.LENGTH_LONG).show();
    }

    private void askWritePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);
            }
        }
    }
}