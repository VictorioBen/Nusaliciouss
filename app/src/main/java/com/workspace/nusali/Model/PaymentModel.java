package com.workspace.nusali.Model;

import android.content.Intent;

public class PaymentModel {
    Integer id;
    String namaPenerima;
    String nomerPenerima;
    String alamatPenerima;
    String petunjuk;
    String jumlah;
    String total;
    String metodeBayar;
    String tanggalBayar;
    String jamBayar;
    boolean expanded;

    public PaymentModel() {
    }

    public PaymentModel(Integer id, String namaPenerima, String nomerPenerima, String alamatPenerima, String petunjuk, String jumlah, String total, String metodeBayar, String tanggalBayar, String jamBayar) {
        this.id = id;
        this.namaPenerima = namaPenerima;
        this.nomerPenerima = nomerPenerima;
        this.alamatPenerima = alamatPenerima;
        this.petunjuk = petunjuk;
        this.jumlah = jumlah;
        this.total = total;
        this.metodeBayar = metodeBayar;
        this.tanggalBayar = tanggalBayar;
        this.jamBayar = jamBayar;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Integer getId() {
        return id;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public String getNomerPenerima() {
        return nomerPenerima;
    }

    public String getAlamatPenerima() {
        return alamatPenerima;
    }

    public String getPetunjuk() {
        return petunjuk;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getTotal() {
        return total;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public String getTanggalBayar() {
        return tanggalBayar;
    }

    public String getJamBayar() {
        return jamBayar;
    }
}
