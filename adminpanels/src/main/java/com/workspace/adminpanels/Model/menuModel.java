package com.workspace.adminpanels.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class menuModel implements Parcelable {

    String judul;
    String desc;
    Integer harga;
    Integer minimal;
    String keterangan;
    String kategori;
    String katering;
    String gambar;

    public menuModel(String judul, String desc, Integer harga, Integer minimal) {
        this.judul = judul;
        this.desc = desc;
        this.harga = harga;
        this.minimal = minimal;
    }

    public menuModel(String judul, String desc, Integer harga, Integer minimal, String keterangan, String kategori, String katering, String gambar) {
        this.judul = judul;
        this.desc = desc;
        this.harga = harga;
        this.minimal = minimal;
        this.keterangan = keterangan;
        this.kategori = kategori;
        this.katering = katering;
        this.gambar = gambar;


    }

    public menuModel() {
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKatering() {
        return katering;
    }

    public void setKatering(String katering) {
        this.katering = katering;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getMinimal() {
        return minimal;
    }

    public void setMinimal(Integer minimal) {
        this.minimal = minimal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.desc);
        dest.writeValue(this.harga);
        dest.writeValue(this.minimal);
        dest.writeString(this.keterangan);
        dest.writeString(this.kategori);
        dest.writeString(this.katering);
        dest.writeString(this.gambar);
    }

    protected menuModel(Parcel in) {
        this.judul = in.readString();
        this.desc = in.readString();
        this.harga = (Integer) in.readValue(Integer.class.getClassLoader());
        this.minimal = (Integer) in.readValue(Integer.class.getClassLoader());
        this.keterangan = in.readString();
        this.kategori = in.readString();
        this.katering = in.readString();
        this.gambar = in.readString();
    }

    public static final Parcelable.Creator<menuModel> CREATOR = new Parcelable.Creator<menuModel>() {
        @Override
        public menuModel createFromParcel(Parcel source) {
            return new menuModel(source);
        }

        @Override
        public menuModel[] newArray(int size) {
            return new menuModel[size];
        }
    };
}
