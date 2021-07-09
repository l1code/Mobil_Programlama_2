package com.example.opencvuygulama1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] secilenTercihler={"Seçiniz","Normalizasyon","Esikleme","Temel","Sobel"};
    private static String TAG = "MainActivity";

    static
    {
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"opencv ayarlandı");
        }else{
            Log.d(TAG,"opencv yuklenmedi");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenCVLoader.initDebug();

        Spinner spSecenekler=findViewById(R.id.spSecenekler);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.
                createFromResource(this,R.array.secenekler,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSecenekler.setAdapter(adapter);
        spSecenekler.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String secilen1=parent.getItemAtPosition(position).toString();
        String secilen=secilenTercihler[position];

        String indis=String.valueOf(position);
        Toast.makeText(this, "Seçilen İndis:"+indis, Toast.LENGTH_SHORT).show();

        if(secilen=="Temel"){
            TemelFonksiyonlar();
        }else{
            Toast.makeText(this, "Geçerli Bir Seçim Yapınız", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void TemelFonksiyonlar(){
        Mat img=null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.image);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.image);

        Mat hedefGri=new Mat();
        Mat hedefBulaniklastirma=new Mat();
        Mat hedefKenar=new Mat();

        Imgproc.cvtColor(img,hedefGri,Imgproc.COLOR_RGBA2GRAY);
        Imgproc.blur(hedefGri,hedefBulaniklastirma,new Size(5.,5.));
        Imgproc.Canny(hedefBulaniklastirma,hedefKenar,100,20);

        Mat islemSonucu=hedefKenar.clone();
        Bitmap img_bitmap= Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
}
