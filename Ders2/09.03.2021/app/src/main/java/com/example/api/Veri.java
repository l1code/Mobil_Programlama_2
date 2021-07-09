package com.example.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Veri {

    @SerializedName("flight_number")
    @Expose
    private Integer flightNumber;

    @SerializedName("mission_name")
    @Expose
    private String gorevAdi;

    @SerializedName("launch_year")
    @Expose
    private String launchYear;

    @SerializedName("rocket")
    @Expose
    public Roket roket;

    @SerializedName("links")
    @Expose
    public Baglanti baglanti;


    @SerializedName("reuse")
    @Expose
    public YenidenKullanim yenidenKullanim;

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getGorevAdi() {
        return gorevAdi;
    }

    public void setGorevAdi(String gorevAdi) {
        this.gorevAdi = gorevAdi;
    }

    public String getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(String launchYear) {
        this.launchYear = launchYear;
    }
}
