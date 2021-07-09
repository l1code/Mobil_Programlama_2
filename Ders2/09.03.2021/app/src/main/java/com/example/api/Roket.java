package com.example.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roket {

    @SerializedName("rocket_id")
    @Expose
    public String rocketId;

    @SerializedName("rocket_name")
    @Expose
    public String rocketName;

    @SerializedName("rocket_type")
    @Expose
    public String rocketType;
}
