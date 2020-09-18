package com.workspace.adminpanels.Model;

public class incomePembayaran {

    Integer idPembayaran;
    String namaPembayar;
    String nomorPembayar;
    String jumlah;
    Integer total;
    String metodeBayar;
    String petunjuk;
    String alamatBayar;
    String tanggalBayar;

    public incomePembayaran() {
    }

    public incomePembayaran(Integer idPembayaran, String namaPembayar, String nomorPembayar, String jumlah, Integer total, String metodeBayar, String petunjuk, String alamatBayar, String tanggalBayar) {
        this.idPembayaran = idPembayaran;
        this.namaPembayar = namaPembayar;
        this.nomorPembayar = nomorPembayar;
        this.jumlah = jumlah;
        this.total = total;
        this.metodeBayar = metodeBayar;
        this.petunjuk = petunjuk;
        this.alamatBayar = alamatBayar;
        this.tanggalBayar = tanggalBayar;
    }

    public incomePembayaran(Integer idPembayaran, Integer total, String tanggalBayar) {
        this.idPembayaran = idPembayaran;
        this.total = total;
        this.tanggalBayar = tanggalBayar;
    }

    public Integer getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(Integer idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getNamaPembayar() {
        return namaPembayar;
    }

    public void setNamaPembayar(String namaPembayar) {
        this.namaPembayar = namaPembayar;
    }

    public String getNomorPembayar() {
        return nomorPembayar;
    }

    public void setNomorPembayar(String nomorPembayar) {
        this.nomorPembayar = nomorPembayar;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public void setMetodeBayar(String metodeBayar) {
        this.metodeBayar = metodeBayar;
    }

    public String getPetunjuk() {
        return petunjuk;
    }

    public void setPetunjuk(String petunjuk) {
        this.petunjuk = petunjuk;
    }

    public String getAlamatBayar() {
        return alamatBayar;
    }

    public void setAlamatBayar(String alamatBayar) {
        this.alamatBayar = alamatBayar;
    }

    public String getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(String tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }
}
