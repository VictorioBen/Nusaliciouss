package com.workspace.nusali.Model;



public class ChartModel{
    Integer id;
    String judul;
    Integer jumlah;
    String katering;
    String tanggal;
    Integer total;
    String waktu;



    public ChartModel() {
    }

    public ChartModel(Integer id, String judul, Integer jumlah, String katering, String tanggal, Integer total, String waktu) {
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

    public String getJudul() {
        return judul;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public String getKatering() {
        return katering;
    }

    public String getTanggal() {
        return tanggal;
    }

    public Integer getTotal() {
        return total;
    }

    public String getWaktu() {
        return waktu;
    }


}
