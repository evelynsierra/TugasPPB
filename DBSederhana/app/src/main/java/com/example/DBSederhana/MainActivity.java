package com.example.DBSederhana;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText nrp,nama;
    private Button simpan, ambildata, updatedata,deletedata;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nrp = (EditText) findViewById(R.id.nrp);
        nama = (EditText) findViewById(R.id.nama);
        simpan = (Button) findViewById(R.id.simpan);
        ambildata = (Button) findViewById(R.id.ambildata);
        updatedata = (Button) findViewById(R.id.updatedata);
        deletedata = (Button) findViewById(R.id.deletedata);

        simpan.setOnClickListener(operasi);
        ambildata.setOnClickListener(operasi);
        updatedata.setOnClickListener(operasi);
        deletedata.setOnClickListener(operasi);

        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists mhs(nrp TEXT, nama TEXT);");


    }
    @Override
    protected void onStop(){
        dbku.close();
        Opendb.close();
        super.onStop();
    }
    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.simpan:simpan();break;
                case R.id.ambildata:ambildata();break;
                case R.id.updatedata:updatedata();break;
                case R.id.deletedata:deletedata();break;
            }
        }
    };

    private void simpan() {
        ContentValues dataku = new ContentValues();

        dataku.put("nrp", nrp.getText().toString());
        dataku.put("nama", nama.getText().toString());
        dbku.insert("mhs", null, dataku);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_LONG).show();
    }

    private void ambildata(){
        Cursor cur = dbku.rawQuery("select * from mhs where nrp='" + nrp.getText().toString()+ "'", null);

        if (cur.getCount() > 0) {
            Toast.makeText(this,"Data ditemukan Sejumlah " + cur.getCount(), Toast.LENGTH_LONG).show();
            cur.moveToFirst();
            nama.setText(cur.getString(cur.getColumnIndex("nama")));
        }
        else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatedata(){
        ContentValues dataku = new ContentValues();

        dataku.put("nrp", nrp.getText().toString());
        dataku.put("nama",nama.getText().toString());
        dbku.update("mhs",dataku,"nrp='"+nrp.getText().toString()+"'",null);
        Toast.makeText(this,"Data Terupdate",Toast.LENGTH_LONG).show();
    }

    private void deletedata() {
        dbku.delete("mhs","nrp='"+nrp.getText().toString()+"'",null);
        Toast.makeText(this,"Data Terhapus",Toast.LENGTH_LONG).show();
    }
};