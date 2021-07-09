package com.example.opencvuygulama1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
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
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] secilenTercihler={"Seçiniz","Derinlik Azalt","Vurgula",
            "Parlaklik","KarEtkisi","Dondur","Sobel", "Genişletme",
            "Aşındırma","Morfolojik","Aşındırma V1",
            "Genişletme V1","Genişletme V2","Aşındırma V2","Açınım","Kapanım"};

    //yol çizgileri, inşaat çelik iskeleti üzerindeki çizgiler, bir görüntü arka planı kaldırıp orijinal renkli görüntüde var
    //olan bir nesnenin renkli halini elde etmek istiyoruz.
    //nesne takipi, tespiti,

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
        }else if(secilen=="Derinlik Azalt"){
            DerinlikAzalt(128);
        }else if(secilen=="Vurgula"){
            Vurgula();
        }else if(secilen=="Parlaklik"){
            Parlaklik();
        }
        else if(secilen=="KarEtkisi"){
            KarEtkisiUygula();
        }else if(secilen=="Dondur"){
            Dondur();
        }else if(secilen=="Sobel"){
            SobelUygulamalari();
        }else if(secilen=="Aşındırma V2"){
            AsindirmaV2();
        }
        else if(secilen=="Aşındırma V1"){
            AsindirmaV1();
        }
        else if(secilen=="Aşındırma"){
            Asindirma();
        }
        else if(secilen=="Genişletme V2"){
            GenisletmeV2();
        }
        else if(secilen=="Genişletme V1"){
            GenisletmeV1();
        }
        else if(secilen=="Genişletme"){
            Genisletme();
        }else if(secilen=="Morfolojik"){
            MorfolojikIslem();
        }else if(secilen=="Açınım"){
            Acinim();
        }else if(secilen=="Kapanım"){
            Kapanim();
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
                    K=150;
                }else{
                    K=175;
                }

                if(Y>210){
                    Y=150;
                }else{
                    Y=175;
                }

                if(M>210){
                    M=150;
                }else{
                    M=175;
                }

                imgCikti.setPixel(x,y,Color.argb(A,K,Y,M));
            }
        }

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(imgCikti);

    }

    public void DerinlikAzalt(int derinlikDegeri){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.image);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        //0-255, 0-255, 0-255 rgb , kırmızı yeşil ve mavi, KYM, K kırmızı, Y=yeşil, M=mavi
        Mat islemSonucu=img.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        Bitmap imgCikti=Bitmap.createBitmap(img_bitmap.getWidth(),img_bitmap.getHeight(),img_bitmap.getConfig());

        Bitmap bir=DerinlikAzaltma(imgCikti,img_bitmap,derinlikDegeri);
        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(bir);
    }
    public Bitmap DerinlikAzaltma(Bitmap imgCikti, Bitmap img_bitmap, int derinlikDegeri) {
        int genislik = img_bitmap.getWidth();
        int yukseklik=img_bitmap.getHeight();

        int A,K,Y,M;
        int piksel;

        for (int x=0;x<genislik;x++) {
            for (int y = 0; y < yukseklik; y++) {

                piksel=img_bitmap.getPixel(x,y);
                A=Color.alpha(piksel);
                K=Color.red(piksel);
                Y=Color.green(piksel);
                M=Color.blue(piksel);

                K=((K+(derinlikDegeri/2))-((K+(derinlikDegeri/2))%derinlikDegeri)-1);
                if(K<0){
                    K=0;
                }

                Y=((Y+(derinlikDegeri/2))-((Y+(derinlikDegeri/2))%derinlikDegeri)-1);
                if(Y<0){
                    Y=0;
                }

                M=((M+(derinlikDegeri/2))-((M+(derinlikDegeri/2))%derinlikDegeri)-1);
                if(M<0){
                    M=0;
                }

                imgCikti.setPixel(x,y,Color.argb(A,K,Y,M));
            }
        }

        return imgCikti;
    }

    public void Vurgula() {
        Mat img = null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //0-255, 0-255, 0-255 rgb , kırmızı yeşil ve mavi, KYM, K kırmızı, Y=yeşil, M=mavi
        Mat islemSonucu=img.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        Bitmap imgCikti=Bitmap.createBitmap(img_bitmap.getWidth(),img_bitmap.getHeight(),img_bitmap.getConfig());


        //boyama için canvası hazırlıyoruz.
        Canvas canvas=new Canvas(imgCikti);

        //varsayılan renk ayarlandı
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        //alfayı yakalamak için paint oluşturuldu
        Paint ptBlur=new Paint();
        ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
        int[] offsetXY=new int[2];

        //bitmap içerisindeki alfayı yakalayıp AKYM, ARGB
        Bitmap bmalpha=img_bitmap.extractAlpha(ptBlur,offsetXY);

        //renk painti oluşturuyoruz
        Paint ptAlphaColor=new Paint();
        ptAlphaColor.setColor(0xFFFFFFFF);

        //yakalanan alfa bölgesi için paint rengi
        canvas.drawBitmap(bmalpha,offsetXY[0],offsetXY[1],ptAlphaColor);

        //hafızayı boşalt garbage collector ne işe yarar, java da otomatik mi çalışır,
        //c++ dilinde garbage collector var mıdır, hafızadan nesneleri silmek için
        // androiddeki recycle kodu gibi kendimiz kod mu yazmalıyız.


        //bmalpha.recycle();

        //Bitmap.recycle (), C ++ belleğini boşaltmak için kullanılır.
        //adobe conect sunucu tabanlı sorun olabilir.

        //c++ tarafındaki belleği boşaltmak isterek bu kodu yazmamız gerekiyor.
        //yazmazsakta sorun olmaz.

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }

    public void Parlaklik() {
        /**
         * Bir resmin parlaklılığını artırma yada rengini açma,beyaz tona
         * doğru ilerlemek için renk kanalları üzerine aynı büyüklükte eşdeğer
         * bir sayı eklemekle gerçekleştirilir. Resmi koyulaştırmak için tam tersi sayı çıkarılır.
         * Burada dikkat edilmesi gereken üzerine eklenen sayılarla sonuç 255 değerini geçmemelidir
         * yada çıkarılan sayılar nedeniyle 0 altına düşmemelidir.
         * Şu şekilde formülüze edebiliriz.
         */

        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.image);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.image);
        Bitmap bir=Parlaklik(img, 50);
        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(bir);
    }

    public Bitmap Parlaklik(Mat img, int parlaklikDegeri){

        //0-255, 0-255, 0-255 rgb , kırmızı yeşil ve mavi, KYM, K kırmızı, Y=yeşil, M=mavi
        Mat islemSonucu=img.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        Bitmap imgCikti=Bitmap.createBitmap(img_bitmap.getWidth(),img_bitmap.getHeight(),img_bitmap.getConfig());

        //piksel bilgileri
        int A,K,Y,M;
        int pikselRenk;

        int genislik=img_bitmap.getWidth();
        int yukseklik=img_bitmap.getHeight();

        for(int x=0;x<genislik;x++) {
            for (int y = 0; y < yukseklik; y++) {

                pikselRenk=img_bitmap.getPixel(x,y);

                //bir piksele ait tüm renk ve alfa kanalını bilgileri aldım
                A=Color.alpha(pikselRenk);
                K=Color.red(pikselRenk);
                Y=Color.green(pikselRenk);
                M=Color.blue(pikselRenk);

                K+=parlaklikDegeri;
                if(K>255){
                    K=255;
                }else if(K<0){
                    K=0;
                }

                Y=Y+parlaklikDegeri;
                if(Y>255){
                    Y=255;
                }else if(Y<0){
                    Y=0;
                }

                M=M+parlaklikDegeri;
                if(M>255){
                    M=255;
                }else if(M<0){
                    M=0;
                }
                imgCikti.setPixel(x,y,Color.argb(A,K,Y,M));
            }
        }

        return imgCikti;
    }

    public void KarEtkisiUygula() {
        Mat img = null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.image);

        Bitmap bir=KarEtkisiUygula(img);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(bir);
    }
    public Bitmap KarEtkisiUygula(Mat img){
        //0-255, 0-255, 0-255 rgb , kırmızı yeşil ve mavi, KYM, K kırmızı, Y=yeşil, M=mavi
        Mat islemSonucu=img.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        //piksel bilgileri
        int A,K,Y,M;
        int pikselRenk;

        int genislik=img_bitmap.getWidth();
        int yukseklik=img_bitmap.getHeight();


        int[] pikseller=new int[genislik*yukseklik];

        //belirtilen kaynaktan piksel dizisi al
        img_bitmap.getPixels(pikseller,0,genislik,0,0,genislik,yukseklik);

        Random random=new Random();
        int indeks=0; int esikDegeri=50;

        for (int y=0;y<yukseklik;y++) {
            for (int x = 0; x < genislik; x++) {
                indeks=y*genislik+x;
                K=Color.red(pikseller[indeks]);
                Y=Color.green(pikseller[indeks]);
                M=Color.blue(pikseller[indeks]);

                esikDegeri=random.nextInt(128);
                if(K>esikDegeri && Y>esikDegeri && M>esikDegeri){
                    pikseller[indeks]=Color.rgb(100,100,100);
                }
            }
        }

        Bitmap imgCikti=Bitmap.createBitmap(img_bitmap.getWidth(),img_bitmap.getHeight(),Bitmap.Config.RGB_565);
        imgCikti.setPixels(pikseller,0,genislik,0,0,genislik,yukseklik);

        return imgCikti;
    }

    public void Dondur() {
        Mat img = null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.image);

        Bitmap bir=Dondur(img, 45);
        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(bir);
    }
    public Bitmap Dondur(Mat img, float derece){
        Mat islemSonucu=img.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        Matrix matriks=new Matrix(); //android.graphics kütüphanesinden olacak
        matriks.postRotate(derece);//döndürme
        //dödnüdürülmüş görüntüyü bitmap resmine yerleştiriyoruz, oluşturuyoruz.
        Bitmap imgCikti=Bitmap.createBitmap(img_bitmap,0,0,img_bitmap.getWidth(),
                img_bitmap.getHeight(),matriks,true);
        return imgCikti;
    }

    public void SobelUygulamalari() {
        Mat img = null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.image);

        Mat hedef=new Mat();
        Imgproc.Sobel(img, hedef,-1,1,1); // [-1,1,1] matrisini kullanan sobel uygulamasını yazınız.
        Imgproc.Sobel(img, hedef,-1,0,1); // [-1,0,1] matrisi kullanan sobel uygulamasını yazınız.
        Imgproc.Sobel(img,hedef,-1,1,0);//[-1,1,0]

        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }



    public void AsindirmaV2() {
        Mat img = null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.teknik);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.teknik);

        Mat hedef=new Mat();
        Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_ERODE,new Size(15,15));

        Imgproc.erode(img,hedef,kernel);
        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
    public void AsindirmaV1(){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.morfoloji1);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.morfoloji1);

        Mat hedef=new Mat();
        Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_ERODE,new Size(15,15));

        Imgproc.erode(img,hedef,kernel);
        Mat islemSonucu=hedef.clone();

        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
    public void Asindirma(){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.ornek);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.ornek);

        Mat hedef=new Mat();

        Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size((2*2)+1,(2*2)+1));
        Imgproc.erode(img,hedef,kernel);

        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }


    public void GenisletmeV2(){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.teknik);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.teknik);

        Mat hedef=new Mat();
        Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_DILATE,new Size(15,15));

        Imgproc.dilate(img,hedef,kernel);
        Mat islemSonucu=hedef.clone();

        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
    public void GenisletmeV1(){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.morfoloji1);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.morfoloji1);
        Mat hedef=new Mat();
        Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_DILATE,new Size(15,15));
        Imgproc.dilate(img,hedef,kernel);

        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
    public void Genisletme(){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.ornek);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.ornek);

        Mat hedef=new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new  Size((2*2) + 1, (2*2)+1));

        Imgproc.dilate(img,hedef,kernel);

        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }

    public void MorfolojikIslem(){
        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.morfolojik_girdi);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.morfolojik_girdi);

        Mat hedef=new Mat();
        Mat kernel = Mat.ones(5,5, CvType.CV_32F);

        Imgproc.morphologyEx(img, hedef, Imgproc.MORPH_TOPHAT, kernel);

        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
    public void Acinim(){
         /*
        Erosion (aşındırma) ve dilation (genişletme) operatörlerinin görüntü üzerine birlikte uygulanması
        ile gerçekleşir. Öncelikli olarak erosion operatörü uygulanır ve ardından
        dilation(genişletme) operatörü uygulanır. (Imgproc.MORPH_OPEN)
         */

        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.morfolojik_girdi);
        }catch(IOException ex){
            ex.printStackTrace();
        }


        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.morfolojik_girdi);

        Mat hedef=new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(25,25));

        //başka hangi işlemler ile yapabiliriz.
        Imgproc.morphologyEx(img,hedef,Imgproc.MORPH_OPEN,kernel);

        //erode, dilate işlemi aynı görüntüye, erode elde edilen çıktı, dilate işleminde girdi olarak
        //kullanılsa aynı sonuç verir mi.

        //erode, dilate işlemi aynı görüntüye, erode elde edilen çıktı, dilate işleminde girdi olarak
        //kullanılsa aynı sonuç verir mi.


        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
    public void Kapanim(){
        //Closing (Kapanım) Görüntüye dilation(genişletme) operatörü uygulanır ve
        // ardından Erosion(aşındırma) operatörü uygulanır. (Imgproc.MORPH_CLOSE)

        Mat img=null;

        try{
            img=Utils.loadResource(getApplicationContext(),R.drawable.morfolojik_girdi);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        ImageView iv1=findViewById(R.id.iv1);
        iv1.setImageResource(R.drawable.morfolojik_girdi);

        Mat hedef=new Mat();

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(5,5));
        Imgproc.morphologyEx(img, hedef, Imgproc.MORPH_CLOSE, kernel);

        Mat islemSonucu=hedef.clone();
        Bitmap img_bitmap=Bitmap.createBitmap(islemSonucu.cols(),islemSonucu.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(islemSonucu,img_bitmap);

        ImageView iv2=findViewById(R.id.iv2);
        iv2.setVisibility(View.VISIBLE);
        iv2.setMinimumWidth(img.width());
        iv2.setImageBitmap(img_bitmap);
    }
}
