package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RoketInterface roketInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roketInterface=ApiIstemci.getIstemci().create(RoketInterface.class);

        Call<List<Veri>> call=roketInterface.getVeri();

        call.enqueue(new Callback<List<Veri>>() {
            @Override
            public void onResponse(Call<List<Veri>> call, Response<List<Veri>> response) {
                List<Veri> veriListesi=new ArrayList<>();
                veriListesi=response.body();

                for (int i=0;i<veriListesi.size();i++){
                    System.out.println("Roket ID:"+veriListesi.get(i).roket.rocketId+"\n");
                    System.out.println(""+veriListesi.get(i).roket.rocketName+"\n");
                    System.out.println(""+veriListesi.get(i).roket.rocketType+"\n");

                    System.out.println(""+veriListesi.get(i).baglanti.GorevYamasi+"\n");
                    System.out.println(""+veriListesi.get(i).baglanti.GorevYamasiKucuk+"\n");

                    System.out.println(""+veriListesi.get(i).baglanti.MakaleLink+"\n");

                    System.out.println(""+veriListesi.get(i).yenidenKullanim.cekirdek+"\n");
                    System.out.println(""+veriListesi.get(i).yenidenKullanim.kaplamalar+"\n");

                    System.out.println("Görev Adı (mission_name):"+veriListesi.get(i).getGorevAdi()+"\n");
                }
            }

            @Override
            public void onFailure(Call<List<Veri>> call, Throwable t) {
                System.out.println("hata meydana geldi.");
            }
        });
    }
}
