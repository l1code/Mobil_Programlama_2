package com.example.uygulama1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uygulama1.model.Kullanici;
import com.example.uygulama1.model.Post;
import com.example.uygulama1.servis.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class KayitEklemeActivity extends AppCompatActivity {

    EditText etUserName,etEmail,etCity,etWeb;
    Button kayitEkleme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekleme);

        etUserName=findViewById(R.id.etUserName);
        etEmail=findViewById(R.id.etUserEmail);
        etCity=findViewById(R.id.etUserCity);
        etWeb=findViewById(R.id.etWebPage);

        kayitEkleme=findViewById(R.id.btnKayitEkle);
        kayitEkleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUser(createPost());
            }
        });


    }


    public Kullanici  createPost(){
      Kullanici userRequest=new Kullanici();
      userRequest.setUsername(etUserName.getText().toString());
      userRequest.setEmail(etEmail.getText().toString());
      userRequest.setWebsite(etWeb.getText().toString());
      userRequest.setName(etWeb.getText().toString());

      return  userRequest;
    }


    public void SaveUser(Kullanici userRequest){
        Call<Kullanici>  userResponse= ApiClient.getUserService().saveUsers(userRequest);
        userResponse.enqueue(new Callback<Kullanici>() {
            @Override
            public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                if (response.isSuccessful()){
                    Toast.makeText(KayitEklemeActivity.this, "Eklendi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Kullanici> call, Throwable t) {
                Toast.makeText(KayitEklemeActivity.this, "Eklenme Esnasında Hata Oluştu!!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}