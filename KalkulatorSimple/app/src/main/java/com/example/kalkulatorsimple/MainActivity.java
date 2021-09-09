package com.example.kalkulatorsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText count1, count2;
    Button sub,add,div,mul;
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count1 = (EditText) findViewById(R.id.first_no);
        count2 = (EditText) findViewById(R.id.second_no);

        sub = (Button) findViewById(R.id.sub);
        add = (Button) findViewById(R.id.add);
        div = (Button) findViewById(R.id.div);
        mul = (Button) findViewById(R.id.mul);

        answer = (TextView) findViewById(R.id.answer);
    }

    public void kalkulator(View v) {
        float bil1, bil2, hasil = 0;
        Button but;

        bil1 = Float.parseFloat(count1.getText().toString());
        bil2 = Float.parseFloat(count2.getText().toString());

        switch (v.getId())
        {
            case R.id.add: hasil = bil1 + bil2; break;
            case R.id.sub: hasil = bil1 - bil2; break;
            case R.id.mul: hasil = bil1 * bil2; break;
            case R.id.div: hasil = bil1 / bil2; break;
        }

        but = (Button) findViewById(v.getId());
        answer.setText("hasil : " + hasil);
    }
}