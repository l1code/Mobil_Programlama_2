package com.example.week1;

import com.google.gson.annotations.SerializedName;

public class Gonder {
    private int KullaniciID;
    private int ID;
    private String Baslik;

    @SerializedName("body")
    private String Metin;

    public String getMetin() {
        return Metin;
    }

    public void setMetin(String metin) {
        Metin = metin;
    }

    public int getKullaniciID() {
        return KullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        KullaniciID = kullaniciID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBaslik() {
        return Baslik;
    }

    public void setBaslik(String baslik) {
        Baslik = baslik;
    }
}
