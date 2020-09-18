package com.workspace.nusali.Model;

public class OrderSelesai {
    public String key;
    public Integer id;
    private String judul;
    private Integer total;
    private Integer jumlah;
    private String tanggal;
    private String waktu;
    private String status;

    public OrderSelesai() {
    }


    public OrderSelesai(String key, Integer id, String judul, Integer total, Integer jumlah, String tanggal, String waktu, String status) {
        this.key = key;
        this.id = id;
        this.judul = judul;
        this.total = total;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.status = status;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
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
