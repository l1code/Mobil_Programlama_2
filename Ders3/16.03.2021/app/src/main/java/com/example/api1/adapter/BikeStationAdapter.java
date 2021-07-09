package com.example.api1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api1.R;
import com.example.api1.listener.ItemClickListener;
import com.example.api1.model.BikeStation;

import java.util.List;

public class BikeStationAdapter
    extends RecyclerView.Adapter<BikeStationAdapter.StationViewHolder>
        implements Filterable{


    private List<BikeStation> mStationList; //tüm istasyon verilerini bu dinamik dizide tut
    private List<BikeStation> mFilteredStationList;  //arama yapıldıgında arama kriterine uyanlarını bu dizide tut.
    private ItemClickListener mItemClickListener; //cardview satırlarına tıklandıgında o satırda bulunan veriyi bize geri döndüyorur.
    private LayoutInflater inflater;

    public BikeStationAdapter(Context context, List<BikeStation> stations) {
        inflater=LayoutInflater.from(context);
        this.mStationList=stations;
        this.mFilteredStationList=stations;
    }

    public void setmItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener=itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public BikeStationAdapter.StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BikeStationAdapter.StationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class StationViewHolder extends RecyclerView.ViewHolder{
        TextView istasyonAdi, doluSlot, bosSlot;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
            istasyonAdi=itemView.findViewById(R.id.tvIstasyonAdi);
            doluSlot=itemView.findViewById(R.id.tvDoluSlot);
            bosSlot=itemView.findViewById(R.id.tvBosSlot);
        }

        public void setData(final BikeStation station, final int position){
            this.istasyonAdi.setText(station.getUcusSayisi());
            this.doluSlot.setText("Dolu slot:"+station.getGorevAdi());
            this.bosSlot.setText("Boş Slot:"+station.getFirlatmaYili());
            itemView.setOnClickListener(v->mItemClickListener.onItemClick(station,position));
        }
    }
}
