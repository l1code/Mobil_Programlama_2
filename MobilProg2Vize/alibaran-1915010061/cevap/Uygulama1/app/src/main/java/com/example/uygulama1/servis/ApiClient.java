package com.example.uygulama1.servis;

import com.example.uygulama1.Arayuz;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private  static Retrofit retrofit=null;
    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Arayuz getUserService(){
        Arayuz arayuzServis=getClient().create(Arayuz.class);
        return  arayuzServis;

    }


}
