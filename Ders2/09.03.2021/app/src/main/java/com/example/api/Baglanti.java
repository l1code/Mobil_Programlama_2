package com.example.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Baglanti {

    @SerializedName("mission_patch")
    @Expose
    public String GorevYamasi;

    @SerializedName("mission_patch_small")
    @Expose
    public String GorevYamasiKucuk;

    @SerializedName("article_link")
    @Expose
    public String MakaleLink;
}
