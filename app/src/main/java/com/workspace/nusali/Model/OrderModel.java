package com.workspace.nusali.Model;

public class OrderModel {
   public Integer id;
    String judul;
    Integer jumlah;
    String katering;
    String tanggal;
    Integer total;
    String waktu;


    public OrderModel() {
    }

    public OrderModel(Integer id, String judul, Integer jumlah, String katering, String tanggal, Integer total, String waktu) {
        this.id = id;
        this.judul = judul;
        this.jumlah = jumlah;
        this.katering = katering;
        this.tanggal = tanggal;
        this.total = total;
        this.waktu = waktu;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getKatering() {
        return katering;
    }

    public void setKatering(String katering) {
        this.katering = katering;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }


}
