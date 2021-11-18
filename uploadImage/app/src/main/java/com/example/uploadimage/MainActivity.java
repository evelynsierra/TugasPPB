package com.example.uploadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private Button btn;
    private static final int RC_Request_code = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (android.widget.ImageView) findViewById(R.id.imageview6);
        btn = (Button)findViewById(R.id.btn_cap);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
    }
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, RC_Request_code);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==RC_Request_code){
            if (resultCode==RESULT_OK){
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                image.setScaleType (ImageView.ScaleType.FIT_CENTER);
                image.setImageBitmap(bp);
            }
            else if (resultCode==RESULT_CANCELED){
                android.widget.Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();


            }
        }
    }
}