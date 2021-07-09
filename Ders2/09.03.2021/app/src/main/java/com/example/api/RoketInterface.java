package com.example.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RoketInterface {

    @GET("v2/launches")
    Call<List<Veri>> getVeri();
}
