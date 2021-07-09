package com.example.soketuygulamasi;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MesajGonder extends AsyncTask<String,Void,Void> {

    Socket soket;
    DataOutputStream dos;
    PrintWriter pw;


    @Override
    protected Void doInBackground(String... voids) {

        String mesaj=voids[0];

        try {
            soket = new Socket("192.168.1.35", 3000);
            pw=new PrintWriter(soket.getOutputStream());
            pw.write(mesaj);
            pw.flush();
            pw.close();
            soket.close();

        }catch (Exception ex){
            System.out.println("Hata: "+ex.toString());
        }
        return null;
    }
}
