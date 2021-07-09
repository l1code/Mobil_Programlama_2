package com.example.api1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BikeStation {

    @SerializedName("flight_number")
    @Expose
    private String ucusSayisi;

    @SerializedName("mission_name")
    @Expose
    private String gorevAdi;

    @SerializedName("launch_year")
    @Expose
    private String firlatmaYili;

    public String getUcusSayisi() {
        return ucusSayisi;
    }

    public void setUcusSayisi(String ucusSayisi) {
        this.ucusSayisi = ucusSayisi;
    }

    public String getGorevAdi() {
        return gorevAdi;
    }

    public void setGorevAdi(String gorevAdi) {
        this.gorevAdi = gorevAdi;
    }

    public String getFirlatmaYili() {
        return firlatmaYili;
    }

    public void setFirlatmaYili(String firlatmaYili) {
        this.firlatmaYili = firlatmaYili;
    }
}
