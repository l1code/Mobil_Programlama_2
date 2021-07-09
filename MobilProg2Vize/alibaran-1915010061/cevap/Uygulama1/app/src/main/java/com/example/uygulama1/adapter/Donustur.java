package com.example.uygulama1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uygulama1.R;
import com.example.uygulama1.TıklamaDinleme;
import com.example.uygulama1.model.Kullanici;

import java.util.ArrayList;

public class Donustur extends RecyclerView.Adapter<Donustur.mViewHolder> implements Filterable {

    private TıklamaDinleme tıklamaDinleme;
    private Context context;
    private ArrayList<Kullanici> kullaniciListe;
    private ArrayList<Kullanici> filteredKullaniciListe;

    public Donustur(Context context,ArrayList<Kullanici> kullaniciArrayList,TıklamaDinleme dinleme) {
        this.context=context;
        this.kullaniciListe=kullaniciArrayList;
        this.filteredKullaniciListe=kullaniciArrayList;
        this.tıklamaDinleme=dinleme;
    }



    @NonNull
    @Override
    public Donustur.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.liste_item,parent,false);
        final mViewHolder viewHolder=new mViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                tıklamaDinleme.onItemClick(filteredKullaniciListe.get(viewHolder.getAdapterPosition()),viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Donustur.mViewHolder holder, int position) {
        holder.userName.setText(filteredKullaniciListe.get(position).getUsername());
        holder.userCity.setText(filteredKullaniciListe.get(position).getAddress().getCity());
        holder.userWeb.setText(filteredKullaniciListe.get(position).getWebsite());
        holder.userEmail.setText(filteredKullaniciListe.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return filteredKullaniciListe.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String arananKelime=constraint.toString();
                if (arananKelime.isEmpty()){
                    filteredKullaniciListe=kullaniciListe;
                }else{
                    ArrayList<Kullanici> geciciFilteredListe=new ArrayList<>();
                    for (Kullanici kullanici:kullaniciListe){
                        if (kullanici.getUsername().toLowerCase().contains(arananKelime)){
                            geciciFilteredListe.add(kullanici);
                        }
                    }
                    filteredKullaniciListe=geciciFilteredListe;
                }
                Filter.FilterResults filterResults=new Filter.FilterResults();
                filterResults.values=filteredKullaniciListe;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredKullaniciListe=(ArrayList<Kullanici>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView userEmail;
        private TextView userCity;
        private TextView userWeb;




        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.userName);
            userEmail = (TextView)itemView.findViewById(R.id.userEmail);
            userWeb = (TextView)itemView.findViewById(R.id.userWebPage);
            userCity = (TextView)itemView.findViewById(R.id.userCity);
        }
    }


}
