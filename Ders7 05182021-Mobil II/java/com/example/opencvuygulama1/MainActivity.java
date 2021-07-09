package com.example.opencvuygulama1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
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

    String[] secilenTercihler={"Seçiniz","Normalizasyon","Esikleme","Temel","Sobel","Genişletme",
            "Aşındırma","Morfolojik","Aşındırma V1",
            "Genişletme V1","Genişletme V2","Aşındırma V2","Açınım","Kapanım"};

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
        }else if (secilen=="Esikleme"){
            Esikleme();
        }else if(secilen=="Normalizasyon"){
            Normalizasyon();
        }
        else{
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


    public void Esikleme(){
        Mat img=null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.adam);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.adam);

        Mat islemSonucu=img.clone();
        Imgproc.cvtColor(img,islemSonucu,Imgproc.COLOR_RGBA2GRAY);

        Mat hedef=new Mat();

        //ikili görüntüye çevirme işlemi gerçekleştirmiş olduk.
        Imgproc.threshold(islemSonucu,hedef,0,255,Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(hedef,img_bitmap);


        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);

        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);

        Imgproc.adaptiveThreshold(islemSonucu,hedef,255,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,5,0);

        img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(hedef,img_bitmap);

        iv1=findViewById(R.id.iv1);
        iv1.setVisibility(View.VISIBLE);
        iv1.setMinimumWidth(img.width());
        iv1.setImageBitmap(img_bitmap);
    }

    public void Normalizasyon(){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.adam);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.adam);

        Mat hedef=new Mat();
        //0-255, 0-255, 0-255 rgb , kırmızı yeşil ve mavi, KYM, K kırmızı, Y=yeşil, M=mavi
        Mat islemSonucu=img.clone();
        Imgproc.cvtColor(img,islemSonucu,Imgproc.COLOR_RGBA2GRAY);

        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);



        //boş bir bitmap resmi oluşturuyoruz.
        Bitmap imgCikti=Bitmap.createBitmap(img_bitmap.getWidth(),img_bitmap.getHeight(),img_bitmap.getConfig());

        //piksel bilgileri
        int A,K,Y,M;

        int genislik=img_bitmap.getWidth();
        int yukseklik=img_bitmap.getHeight();
        int piksel;

        for (int x=0;x<genislik;x++){
            for (int y=0;y<yukseklik;y++){
                piksel=img_bitmap.getPixel(x,y);

                //bir piksele ait tüm renk ve alfa kanalı bilgilerini aldım
                A= Color.alpha(piksel);
                K=Color.red(piksel);
                Y=Color.green(piksel);
                M=Color.blue(piksel);

                if(K>210){
                    K=0;
                }else{
                    K=1;
                }

                if(Y>210){
                    Y=0;
                }else{
                    Y=1;
                }

                if(M>210){
                    M=0;
                }else{
                    M=1;
                }

                imgCikti.setPixel(x,y,Color.argb(A,K,Y,M));
            }
        }

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);

    }
}
