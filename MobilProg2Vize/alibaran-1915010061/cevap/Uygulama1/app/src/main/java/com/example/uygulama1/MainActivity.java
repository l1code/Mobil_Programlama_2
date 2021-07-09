package com.example.uygulama1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.uygulama1.adapter.Donustur;
import com.example.uygulama1.model.Kullanici;
import com.example.uygulama1.servis.ApiClient;
import com.example.uygulama1.yardimci.DialogHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton faButton;
    private RecyclerView recyclerView;
    private Donustur donustur;
    private ArrayList<Kullanici> kullaniciArrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager glm=new GridLayoutManager(getApplicationContext(),2);
        RecyclerView.LayoutManager layoutManager=glm;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        faButton=findViewById(R.id.faBtnEkle);

        getKullaniciListeFromApi();

        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,KayitEklemeActivity.class);
                MainActivity.this.startActivity(intent);
                
            }
        });

    }

    private void getKullaniciListeFromApi(){
        progressDialog = createProgressDialog(MainActivity.this);
        Arayuz arayuz= ApiClient.getClient().create(Arayuz.class);
        Call<List<Kullanici>> call=arayuz.getUsers();
                call.enqueue(new Callback<List<Kullanici>>() {
                    @Override
                    public void onResponse(Call<List<Kullanici>> call, Response<List<Kullanici>> response) {
                        progressDialog.dismiss();
                        kullaniciArrayList=new ArrayList<>(response.body());
                        donustur=new Donustur(getApplicationContext(), kullaniciArrayList, new TıklamaDinleme() {
                            @Override
                            public void onItemClick(Kullanici user, int position) {
                                Toast.makeText(getApplicationContext(), ""+user.getUsername(), Toast.LENGTH_SHORT).show();

                            }
                        });
                        recyclerView.setAdapter(donustur);
                    }

                    @Override
                    public void onFailure(Call<List<Kullanici>> call, Throwable t) {
                        progressDialog.dismiss();
                        DialogHelper.getAlertWithMessage("Hata",t.getMessage(),MainActivity.this);
                    }
                });
    }

    public ProgressDialog createProgressDialog(Context mContext){
        ProgressDialog progressDialog=new ProgressDialog(mContext);
        try {
            progressDialog.show();
        }catch (WindowManager.BadTokenException e){

        }
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_layout);
        return progressDialog;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);

        MenuItem aramaItem=menu.findItem(R.id.menuArama);

        SearchView searchView=null;
        if (aramaItem != null){
            searchView=(SearchView) aramaItem.getActionView();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                donustur.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}