package com.example.week1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvSonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSonuc=findViewById(R.id.tvSonuc);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api=retrofit.create(Api.class);
        Call<List<Gonder>> call=api.getGonders();
        call.enqueue(new Callback<List<Gonder>>() {
            @Override
            public void onResponse(Call<List<Gonder>> call, Response<List<Gonder>> response) {
                if(!response.isSuccessful()){
                    tvSonuc.setText("Kod:"+response.code());
                    return;
                }

                List<Gonder> gonders=response.body();
                for (Gonder gonder:gonders){
                    String icerik="";
                    icerik+="ID: "+gonder.getID()+"\n";
                    icerik+="Kullanici ID:"+gonder.getKullaniciID()+"\n";
                    icerik+="Başlık:"+gonder.getBaslik()+"\n";
                    icerik+="Metin:"+gonder.getMetin()+"\n";

                    tvSonuc.append(icerik);
                }
            }

            @Override
            public void onFailure(Call<List<Gonder>> call, Throwable t) {
                tvSonuc.setText(t.getMessage());
            }
        });
    }
}
