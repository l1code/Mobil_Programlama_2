package com.example.uygulama1;

import com.example.uygulama1.model.Kullanici;
import com.example.uygulama1.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Arayuz {
    @GET("users")
    Call<List<Kullanici>> getUsers();

    @POST("users")
    Call<Kullanici> saveUsers(@Body Kullanici post);

}
