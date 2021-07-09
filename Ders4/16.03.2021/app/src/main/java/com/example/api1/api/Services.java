package com.example.api1.api;

import com.example.api1.model.BikeStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Services {
    @GET("v2/launches")
    Call<List<BikeStation>> getBikeStation();
}
