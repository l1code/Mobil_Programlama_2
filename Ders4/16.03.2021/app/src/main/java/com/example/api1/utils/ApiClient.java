package com.example.api1.utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;
    private static String temelApiYolu="https://api.spacexdata.com/";

    public static <S> S createService(Class<S> serviceClass){
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(temelApiYolu)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(new OkHttpClient());
        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
