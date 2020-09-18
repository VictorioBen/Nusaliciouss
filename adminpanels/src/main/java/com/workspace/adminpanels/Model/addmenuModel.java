package com.workspace.adminpanels.Model;

public class addmenuModel {
    String judul;
    String desc;
    Integer harga;
    Integer minimal;
    String keterangan;
    String kategori;
    String katering;
    String gambar;



    public addmenuModel() {
    }

    public addmenuModel(String textPaket, String textDescs, Integer textharga, Integer textMin, String textKet, String kategori, String kateriing, String mImage) {
        this.judul = textPaket;
        this.desc = textDescs;
        this.harga = textharga;
        this.minimal = textMin;
        this.keterangan = textKet;
        this.kategori = kategori;
        this.katering = kateriing;
        this.gambar = mImage;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getKatering() {
        return katering;
    }

    public void setKatering(String katering) {
        this.katering = katering;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getkategori() {
        return kategori;
    }

    public void setkategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getMinimal() {
        return minimal;
    }

    public void setMinimal(Integer minimal) {
        this.minimal = minimal;
    }
}
