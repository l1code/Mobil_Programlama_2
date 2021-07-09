package com.example.opencvapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";


    static {
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"opencv ayarlandı");
        }else{
            Log.d(TAG,"opencv yüklenemedi");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenCVLoader.initDebug();
    }
}
