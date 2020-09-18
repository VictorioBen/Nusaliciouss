package com.workspace.adminpanels.Model;

public class pembayaran2Model {

    public String uid;
    private Integer id;
    private String namaPenerima;
    private String nomerPenerima;
    private String alamatPenerima;
    private String petunjuk;
    private String jumlah;
    private String total;
    private String metodeBayar;
    private String  tanggalBayar;

    public pembayaran2Model() {
    }

    public pembayaran2Model(Integer id, String namaPenerima, String nomerPenerima, String alamatPenerima, String petunjuk, String jumlah, String total, String metodeBayar, String tanggalBayar) {
        this.id = id;
        this.namaPenerima = namaPenerima;
        this.nomerPenerima = nomerPenerima;
        this.alamatPenerima = alamatPenerima;
        this.petunjuk = petunjuk;
        this.jumlah = jumlah;
        this.total = total;
        this.metodeBayar = metodeBayar;
        this.tanggalBayar = tanggalBayar;
    }

    public pembayaran2Model(String uid, Integer id, String namaPenerima, String total){
        this.uid = uid;
        this.id = id;
        this.namaPenerima = namaPenerima;
        this.total = total;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getNomerPenerima() {
        return nomerPenerima;
    }

    public void setNomerPenerima(String nomerPenerima) {
        this.nomerPenerima = nomerPenerima;
    }

    public String getAlamatPenerima() {
        return alamatPenerima;
    }

    public void setAlamatPenerima(String alamatPenerima) {
        this.alamatPenerima = alamatPenerima;
    }

    public String getPetunjuk() {
        return petunjuk;
    }

    public void setPetunjuk(String petunjuk) {
        this.petunjuk = petunjuk;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public void setMetodeBayar(String metodeBayar) {
        this.metodeBayar = metodeBayar;
    }

    public String getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(String tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }
}
