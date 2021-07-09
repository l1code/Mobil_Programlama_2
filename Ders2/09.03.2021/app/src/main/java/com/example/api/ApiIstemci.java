package com.example.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiIstemci {
    private static Retrofit retrofit=null;
    private static String Base_Url="https://api.spacexdata.com";

    public static Retrofit getIstemci(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();
            return retrofit;
        }
        return retrofit;
    }
}
