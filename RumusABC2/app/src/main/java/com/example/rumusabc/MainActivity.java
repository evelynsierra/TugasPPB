package com.example.rumusabc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {

    private EditText edtA, edtB, edtC;
        private Button btnHitung;
        private TextView txtHasil;
    private double a1,b1,c1,hasil1,hasil2,D;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variable yang ada di layout activty_main
        edtA = (EditText) findViewById(R.id.edt_a);
        edtB = (EditText) findViewById(R.id.edt_b);
        edtC = (EditText) findViewById(R.id.edt_c);
        btnHitung = (Button) findViewById(R.id.btn_hitung);
        txtHasil = (TextView) findViewById(R.id.txt_hasil);

        getSupportActionBar().setTitle("Hitung Menggunakan Rumus ABC");

        //berikan action button hitung untuk menghitung hasil
        btnHitung.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                String a, b, c;


                a = edtA.getText().toString();
                b = edtB.getText().toString();
                c = edtC.getText().toString();

                //TextUtils berfungsi agar isian tidak kosong
                if (TextUtils.isEmpty(a)) {
                    edtA.setError("Harap mengisi nilai a");
                    edtA.requestFocus();
                }

                else if(TextUtils.isEmpty(b)){
                    edtB.setError("Harap mengisi nilai b");
                    edtB.requestFocus();
                }

                else if(TextUtils.isEmpty(c)){
                    edtC.setError("Harap mengisi nilai c");
                    edtC.requestFocus();
                }

                else {
                    //jika semuanya telah terisi maka boleh melakukan perhitungan
                    a1 = Double.parseDouble(a);
                    b1 = Double.parseDouble(b);
                    c1 = Double.parseDouble(c);
                    D = Math.sqrt(b1*b1 - (4*a1*c1))/(2*a1);
                        hasil1 = -b1 + D;
                        hasil2 = -b1 - D;

                        //Kemudian tampilkan di TextView
                        txtHasil.setText("Hasil Perhitungan x1 adalah " + hasil1 + "dan hasil perhitungan x2 adalah " + hasil2);

                }
            }
        });
    }


}