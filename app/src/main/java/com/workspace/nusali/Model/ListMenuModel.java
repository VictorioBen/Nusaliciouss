package com.workspace.nusali.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListMenuModel implements Parcelable {
    String desc;
    String gambar;
    Integer harga;
    String judul;
    String kategori;
    String katering;
    String keterangan;
    Integer minimal;


    public ListMenuModel() {
    }

    public ListMenuModel(String desc, String gambar, Integer harga, String judul, String kategori, String katering, String keterangan, Integer minimal) {
        this.desc = desc;
        this.gambar = gambar;
        this.harga = harga;
        this.judul = judul;
        this.kategori = kategori;
        this.katering = katering;
        this.keterangan = keterangan;
        this.minimal = minimal;
    }

    public String getDesc() {
        return desc;
    }

    public String getGambar() {
        return gambar;
    }

    public Integer getHarga() {
        return harga;
    }

    public String getJudul() {
        return judul;
    }

    public String getKategori() {
        return kategori;
    }

    public String getKatering() {
        return katering;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public Integer getMinimal() {
        return minimal;
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
        dest.writeString(this.kategori);
        dest.writeString(this.katering);
        dest.writeString(this.gambar);
        dest.writeValue(this.minimal);
        dest.writeString(this.keterangan);

    }

    protected ListMenuModel(Parcel in) {
        this.judul = in.readString();
        this.desc = in.readString();
        this.harga = (Integer) in.readValue(Integer.class.getClassLoader());
        this.kategori = in.readString();
        this.katering = in.readString();
        this.gambar = in.readString();
        this.minimal = (Integer) in.readValue(Integer.class.getClassLoader());
        this.keterangan = in.readString();
    }

    public static final Parcelable.Creator<ListMenuModel> CREATOR = new Parcelable.Creator<ListMenuModel>() {
        @Override
        public ListMenuModel createFromParcel(Parcel source) {
            return new ListMenuModel(source);
        }

        @Override
        public ListMenuModel[] newArray(int size) {
            return new ListMenuModel[size];
        }
    };
}
