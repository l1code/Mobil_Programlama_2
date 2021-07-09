package com.example.uygulama1.model;

import com.google.gson.annotations.SerializedName;

public class Kullanici {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("website")
    private String website;

    @SerializedName("address")
    private Adres address;

    @SerializedName("company")
    private Sirket company;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public Adres getAddress() {
        return address;
    }

    public Sirket getCompany() {
        return company;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setAddress(Adres address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
}
