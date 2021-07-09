package com.example.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YenidenKullanim {

    @SerializedName("core")
    @Expose
    public boolean cekirdek;

    @SerializedName("side_core1")
    @Expose
    public boolean kenarCekirdek1;

    @SerializedName("side_core2")
    @Expose
    public boolean kenarCekirdek2;

    @SerializedName("fairings")
    @Expose
    public boolean kaplamalar;

    @SerializedName("capsule")
    @Expose
    public boolean kapsul;
}
