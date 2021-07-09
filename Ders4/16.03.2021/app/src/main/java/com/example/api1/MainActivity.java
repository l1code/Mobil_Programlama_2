package com.example.api1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.api1.adapter.BikeStationAdapter;
import com.example.api1.api.Services;
import com.example.api1.listener.ItemClickListener;
import com.example.api1.model.BikeStation;
import com.example.api1.utils.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
    implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener
{

    Services services;
    RecyclerView recyclerView;
    BikeStationAdapter adapter;
    List<BikeStation> list=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;


    //https://www.google.com/search?client=firefox-b-d&q=cardview+android
    //https://www.journaldev.com/10708/android-swiperefreshlayout-pull-swipe-refresh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rvYerlesim);
        swipeRefreshLayout=findViewById(R.id.srYerlesim);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);

        adapter=new BikeStationAdapter(this,list);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setmItemClickListener(this);

        getData();

    }

    private void getData(){
        services = ApiClient.createService(Services.class);
        Call<List<BikeStation>> call = services.getBikeStation();

        call.enqueue(new Callback<List<BikeStation>>() {
            @Override
            public void onResponse(Call<List<BikeStation>> call, Response<List<BikeStation>> response) {
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                assert response.body() != null;

                for (BikeStation station:response.body()){
                    list.add(station);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BikeStation>> call, Throwable t) {
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

                for(int i=0;i<100;i++){
                    System.out.println(i+"---");
                }

                System.out.println("Hata:"+t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_arama,menu);;

        MenuItem aramaParcasi=menu.findItem(R.id.menuArama);
        androidx.appcompat.widget.SearchView searchview=null;

        if(aramaParcasi!=null){
            searchview=(androidx.appcompat.widget.SearchView)aramaParcasi.getActionView();
        }

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        list.clear();
        getData();
    }

    @Override
    public void onItemClick(BikeStation station, int position) {
        Toast.makeText(this, station.getUcusSayisi() + " Clicked.", Toast.LENGTH_SHORT).show();
    }
}
