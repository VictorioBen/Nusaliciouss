package com.workspace.adminpanels.Model;

public class riwayat2Model {
    private String xkey;
    private Integer id;
    private String judul;
    private String status;
    private String tanggal;
    private String waktu;

    public riwayat2Model() {
    }

    public riwayat2Model(String xkey, Integer id, String judul, String status, String tanggal, String waktu) {
        this.xkey = xkey;
        this.id = id;
        this.judul = judul;
        this.status = status;
        this.tanggal = tanggal;
        this.waktu = waktu;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
