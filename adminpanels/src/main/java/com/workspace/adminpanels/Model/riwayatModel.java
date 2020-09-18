package com.workspace.adminpanels.Model;

public class riwayatModel {
    public String xkey;
    private Integer id;
    private String judul;
    private Integer jumlah;
    private Integer total;
    private String tanggal;
    private String waktu;
    private String status;

    public riwayatModel() {
    }

    public riwayatModel(String xkey, Integer id, String judul, Integer jumlah, Integer total, String tanggal, String waktu, String status) {
        this.xkey = xkey;
        this.id = id;
        this.judul = judul;
        this.jumlah = jumlah;
        this.total = total;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.status = status;
    }

    public String getXkey() {
        return xkey;
    }

    public void setXkey(String xkey) {
        this.xkey = xkey;
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
