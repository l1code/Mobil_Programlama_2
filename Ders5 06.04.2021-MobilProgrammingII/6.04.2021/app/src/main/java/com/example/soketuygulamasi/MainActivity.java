package com.example.soketuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText etMesaj;
    TextView tvMesaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMesaj=findViewById(R.id.etMesaj);
        tvMesaj=findViewById(R.id.tvMesaj);


        //netbeans den gelen veriyi okuma için thread tanımlandı.
        Thread thread=new Thread(new SunucuThread());
        thread.start();
    }

    public void Gonder(View v){
        MesajGonder mg=new MesajGonder();
        mg.execute(etMesaj.getText().toString());
        Toast.makeText(this, ""+etMesaj.getText(), Toast.LENGTH_SHORT).show();
    }

    public class SunucuThread implements Runnable{
        Socket soket;
        ServerSocket sunucuSoket;
        InputStreamReader isr;
        BufferedReader br;
        String mesaj;
        Handler h=new Handler();

        @Override
        public void run() {
           try {
               sunucuSoket = new ServerSocket(2001);
               while(true){
                   soket=sunucuSoket.accept();
                   isr=new InputStreamReader(soket.getInputStream());
                   br=new BufferedReader(isr);
                   mesaj=br.readLine();

                   h.post(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(MainActivity.this, ""+mesaj, Toast.LENGTH_SHORT).show();
                           tvMesaj.setText(mesaj.toString());
                       }
                   });
               }
           }catch(Exception ex){
               Log.i("hata",ex.toString());
               Toast.makeText(MainActivity.this, "hata"+ex.toString(), Toast.LENGTH_SHORT).show();
               System.out.println(ex.toString());
           }
        }
    }
}
