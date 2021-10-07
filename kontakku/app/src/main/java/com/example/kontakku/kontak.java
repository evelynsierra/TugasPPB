package com.example.kontakku;

public class kontak {
    private String nama;
    private String noHp;

    public kontak(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
    }

    public String getNama() {return nama;}

    public void setNama(String nama) {this.nama = nama;}

    public String getNoHp() {return noHp;}

    public void setNoHp(String noHp) {this.noHp = noHp;}
}