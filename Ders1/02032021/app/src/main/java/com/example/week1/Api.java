package com.example.week1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    //çekilecek apinin route bilgisi girilecek.
    @GET("posts")
    Call<List<Gonder>> getGonders();
}
