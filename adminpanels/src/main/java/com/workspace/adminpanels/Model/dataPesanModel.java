package com.workspace.adminpanels.Model;

public class dataPesanModel {

    public String key;
    private Integer id;
    private String judul;
    private String katering;
    private Integer jumlah;
    private Integer total;
    private String tanggal;
    private String waktu;
    private String status;
    boolean xpand;

    public dataPesanModel() {
    }

    public dataPesanModel(String key, Integer id, String judul, String katering, Integer jumlah, Integer total, String tanggal, String waktu, String status) {
        this.key = key;
        this.id = id;
        this.judul = judul;
        this.katering = katering;
        this.jumlah = jumlah;
        this.total = total;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.status = status;
        this.xpand = false;
    }

    public boolean isXpand() {
        return xpand;
    }

    public void setXpand(boolean xpand) {
        this.xpand = xpand;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getKatering() {
        return katering;
    }

    public void setKatering(String katering) {
        this.katering = katering;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
