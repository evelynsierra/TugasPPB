package com.example.kontakku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ImageView add,edit,del,find,refresh;
    private kontakAdapter kAdapter;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper dbopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);
        add = (ImageView) findViewById(R.id.add);
        edit = (ImageView) findViewById(R.id.edit);
        del = (ImageView) findViewById(R.id.del);
        find = (ImageView) findViewById(R.id.find);
        refresh = (ImageView) findViewById(R.id.refresh);
        add.setOnClickListener(operasi);
        edit.setOnClickListener(operasi);
        del.setOnClickListener(operasi);
        find.setOnClickListener(operasi);
        refresh.setOnClickListener(operasi);

        ArrayList<kontak> listKontak = new ArrayList<kontak>();
        kAdapter = new kontakAdapter(this,0,listKontak);
        lv.setAdapter(kAdapter);

        dbopen = new SQLiteOpenHelper(this,"kontak.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) { }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
        };
        dbku = dbopen.getWritableDatabase();
        dbku.execSQL("create table if not exists kontak(nama TEXT, nohp TEXT);");
        ambildata();
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add:
                    tambah_data();
                    break;
                case R.id.edit:
                    edit_data();
                    break;
                case R.id.del:
                    hapus_data();
                    break;
                case R.id.find:
                    cari_data();
                    break;
                case R.id.refresh:
                    ambildata();
                    break;
            }
        }
    };

    private void insertKontak(String nm, String hp) {
        kontak newKontak = new kontak(nm,hp);
        kAdapter.add(newKontak);
    }

    private void ambildata() {
        Cursor cur = dbku.rawQuery("select * from kontak",null);
        Toast.makeText(this,"Terdapat sejumlah " + cur.getCount(), Toast.LENGTH_LONG).show();

        int i = 0;
        kAdapter.clear();
        if(cur.getCount() > 0) cur.moveToFirst();
        while(i < cur.getCount()) {
            int idx_nama = cur.getColumnIndex("nama");
            int idx_nohp = cur.getColumnIndex("nohp");
            insertKontak(cur.getString(idx_nama),cur.getString(idx_nohp));
            cur.moveToNext();
            i++;
        }
    }

    private void add_item(String nm, String hp) {
        ContentValues datanya = new ContentValues();
        datanya.put("nama",nm);
        datanya.put("nohp",hp);
        dbku.insert("kontak",null,datanya);
        kontak newKontak = new kontak(nm,hp);
        kAdapter.add(newKontak);
    }

    private void tambah_data() {
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Add Kontak");

        View vAdd = LayoutInflater.from(this).inflate(R.layout.add_kontak,null);
        final EditText nm = (EditText) vAdd.findViewById(R.id.nm);
        final EditText hp = (EditText) vAdd.findViewById(R.id.hp);

        buat.setView(vAdd);
        // Set up the buttons
        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                add_item(nm.getText().toString(),hp.getText().toString());
                Toast.makeText(getBaseContext(),"Data Tersimpan",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        buat.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        buat.show();
    }

    private void edit_item(String old_nm, String old_hp, String new_nm, String new_hp) {
        ContentValues datanya = new ContentValues();
        datanya.put("nama",new_nm);
        datanya.put("nohp",new_hp);
        dbku.update("kontak",datanya,"nama='"+old_nm+"' and nohp='"+old_hp+"'",null);
        kAdapter.clear();
        ambildata();
    }

    private void edit_data() {
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Edit Kontak");

        View vEdit = LayoutInflater.from(this).inflate(R.layout.edit_kontak,null);
        final EditText old_nm = (EditText) vEdit.findViewById(R.id.old_nm);
        final EditText old_hp = (EditText) vEdit.findViewById(R.id.old_hp);
        final EditText new_nm = (EditText) vEdit.findViewById(R.id.new_nm);
        final EditText new_hp = (EditText) vEdit.findViewById(R.id.new_hp);

        buat.setView(vEdit);
        // Set up the buttons
        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit_item(old_nm.getText().toString(),old_hp.getText().toString(),new_nm.getText().toString(),new_hp.getText().toString());
                Toast.makeText(getBaseContext(),"Data Teredit",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        buat.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        buat.show();
    }

    private void del_item(String nm, String hp) {
        dbku.delete("kontak","nama='"+nm+"' and nohp='"+hp+"'",null);
        kAdapter.clear();
        ambildata();
    }

    private void hapus_data() {
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Delete Kontak");

        View vDel = LayoutInflater.from(this).inflate(R.layout.del_kontak,null);
        final EditText nm = (EditText) vDel.findViewById(R.id.nm);
        final EditText hp = (EditText) vDel.findViewById(R.id.hp);

        buat.setView(vDel);
        // Set up the buttons
        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                del_item(nm.getText().toString(),hp.getText().toString());
                Toast.makeText(getBaseContext(),"Data Terhapus",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        buat.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        buat.show();
    }

    private void find_item(String nm, String hp) {
        Cursor cur;
        if(nm.isEmpty()) cur = dbku.rawQuery("select * from kontak where nohp like '%"+hp+"%'",null);
        else if(hp.isEmpty()) cur = dbku.rawQuery("select * from kontak where nama like '%"+nm+"%'",null);
        else cur = dbku.rawQuery("select * from kontak where nama like '%"+nm+"%' or nohp like '%"+hp+"%'",null);
        Toast.makeText(this,"Terdapat sejumlah " + cur.getCount(), Toast.LENGTH_LONG).show();

        int i = 0;
        kAdapter.clear();
        if(cur.getCount() > 0) cur.moveToFirst();
        while(i < cur.getCount()) {
            int idx_nama = cur.getColumnIndex("nama");
            int idx_nohp = cur.getColumnIndex("nohp");
            insertKontak(cur.getString(idx_nama),cur.getString(idx_nohp));
            cur.moveToNext();
            i++;
        }
    }

    private void cari_data() {
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Find Kontak");

        View vFind = LayoutInflater.from(this).inflate(R.layout.find_kontak,null);
        final EditText nm = (EditText) vFind.findViewById(R.id.nm);
        final EditText hp = (EditText) vFind.findViewById(R.id.hp);

        buat.setView(vFind);
        // Set up the buttons
        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                find_item(nm.getText().toString(),hp.getText().toString());
                dialog.dismiss();
            }
        });
        buat.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        buat.show();
    }
}