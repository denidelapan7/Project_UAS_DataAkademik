package com.example.uas.model;

public class dataDosenModel {
    public String nid, nama, telepon;

    public dataDosenModel(String nid, String nama, String telepon) {
        this.nid = nid;
        this.nama = nama;
        this.telepon = telepon;
    }

    public String getNid() {
        return nid;
    }

    public String getNama() {
        return nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}
