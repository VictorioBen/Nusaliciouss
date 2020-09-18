package com.workspace.nusali.Model;

public class UserDeliveryModel {
    String namaPenerima;
    String nomerPenerima;
    String alamat;
    String petunjuk;

    public UserDeliveryModel() {
    }

    public UserDeliveryModel(String namaPenerima, String nomerPenerima, String alamat, String petunjuk) {
        this.namaPenerima = namaPenerima;
        this.nomerPenerima = nomerPenerima;
        this.alamat = alamat;
        this.petunjuk = petunjuk;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPetunjuk() {
        return petunjuk;
    }

    public void setPetunjuk(String petunjuk) {
        this.petunjuk = petunjuk;
    }
}
