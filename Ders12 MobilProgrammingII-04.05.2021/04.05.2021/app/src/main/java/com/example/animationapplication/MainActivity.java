package com.example.animationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView ivAnm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        ivAnm= findViewById(R.id.ivAnm);
        ivAnm.setBackgroundResource(R.drawable.animation);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        AnimationDrawable frameAnimation=(AnimationDrawable)ivAnm.getBackground();
        if(hasFocus){
            frameAnimation.start();
        }else{
            frameAnimation.stop();
        }
    }
}
