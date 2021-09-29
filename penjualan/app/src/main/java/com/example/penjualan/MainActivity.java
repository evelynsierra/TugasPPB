package com.example.penjualan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etNamaPelanggan, etNamaBarang, etJmlBarang, etHarga, etJmlUang;
    TextView tvNamaPembeli, tvNamaBarang, tvJmlBarang, tvHarga, tvUangBayar,
            tvTotal, tvKembalian, tvBonus, tvKeterangan;
    Button btnProses, btnHapus, btnKeluar, btnKeranjang;

    List<PenjualanModel> order = new ArrayList<>();

    String namaPelanggan, namaBarang,hargaBarangs = "",jumlahBarangs = "", namaBarangs = "";
    Integer jumlahBarang, hargaBarang, uangBayar;
    double  total, kembalian;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Aplikasi Penjualan");

        //EditText
        etNamaPelanggan = findViewById(R.id.etNamaPelanggan);
        etNamaBarang = findViewById(R.id.etNamaBarang);
        etJmlBarang = findViewById(R.id.etJmlBarang);
        etHarga = findViewById(R.id.etHarga);
        etJmlUang = findViewById(R.id.etJmlUang);

        //TextView
        tvNamaPembeli = findViewById(R.id.tvNamaPembeli);
        tvNamaBarang = findViewById(R.id.tvNamaBarang);
        tvJmlBarang = findViewById(R.id.tvJmlBarang);
        tvHarga = findViewById(R.id.tvHarga);
        tvUangBayar = findViewById(R.id.tvUangBayar);
        tvTotal = findViewById(R.id.tvTotal);
        tvKembalian = findViewById(R.id.tvKembalian);
        tvBonus = findViewById(R.id.tvBonus);
        tvKeterangan = findViewById(R.id.tvKeterangan);

        //Button
        btnProses = findViewById(R.id.btnProses);
        btnHapus = findViewById(R.id.btnHapus);
        btnKeluar = findViewById(R.id.btnKeluar);
        btnKeranjang = findViewById(R.id.btnKeranjang);

        //Proses
        btnKeranjang.setOnClickListener(view -> {
            namaBarang = etNamaBarang.getText().toString().trim();
            jumlahBarang = Integer.parseInt(etJmlBarang.getText().toString().trim());
            hargaBarang = Integer.parseInt(etHarga.getText().toString().trim());

            order.add(new PenjualanModel(namaBarang, jumlahBarang, hargaBarang));

            total = total + (jumlahBarang * hargaBarang);

            namaBarangs = namaBarangs + " " + namaBarang;
            jumlahBarangs = jumlahBarangs + " " + jumlahBarang.toString();
            hargaBarangs = hargaBarangs + " " + hargaBarang.toString();

            Toast.makeText(getApplicationContext(), "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();


            etNamaBarang.setText("");
            etJmlBarang.setText("");
            etHarga.setText("");

        });

        btnProses.setOnClickListener(view -> {
            namaPelanggan = etNamaPelanggan.getText().toString().trim();
            uangBayar = Integer.parseInt(etJmlUang.getText().toString().trim());

            tvNamaBarang.setText("Nama Barang : " + namaBarangs);
            tvJmlBarang.setText("Jumlah Barang : " + jumlahBarangs);
            tvNamaPembeli.setText("Nama Pembeli : " + namaPelanggan);
            tvHarga.setText("Harga Barang : " + hargaBarangs);
            tvUangBayar.setText("Uang bayar : " + uangBayar);

            tvTotal.setText("Total Belanja " + total);
            if (total >= 200000) {
                tvBonus.setText("Bonus : HardDisk 1TB");
            } else if (total >= 50000) {
                tvBonus.setText("Bonus : Keyboard Gaming");
            } else if (total >= 40000) {
                tvBonus.setText("Bonus : Mouse Gaming");
            } else {
                tvBonus.setText("Bonus : Tidak ada bonus!");
            }

            kembalian = (uangBayar - total);
            if (uangBayar < total) {
                tvKeterangan.setText("Keterangan : Uang bayar kurang Rp. " + (-kembalian));
                tvKembalian.setText("Uang Kembalian : Rp. 0");
            } else {
                tvKeterangan.setText("Keterangan : Tunggu kembalian");
                tvKembalian.setText("Uang Kembalian : Rp. " + kembalian);
            }

        });

        btnHapus.setOnClickListener(view -> {
            tvNamaPembeli.setText("");
            tvNamaBarang.setText("");
            tvJmlBarang.setText("");
            tvHarga.setText("");
            tvUangBayar.setText("");
            tvKembalian.setText("");
            tvKeterangan.setText("");
            tvBonus.setText("");
            tvTotal.setText("");

            Toast.makeText(getApplicationContext(), "Data sudah dihapus", Toast.LENGTH_SHORT).show();
        });

        btnKeluar.setOnClickListener(view -> moveTaskToBack(true));

    }

}