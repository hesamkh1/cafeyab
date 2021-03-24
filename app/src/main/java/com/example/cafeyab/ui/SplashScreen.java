package com.example.cafeyab.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.cafeyab.R;
import com.example.cafeyab.ui.Login.MainActivity;

public class SplashScreen extends AppCompatActivity {

    private  static int SPASH_TIME_OUT=500;
    ImageView imagesplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setBackgroundDrawable(null);

        imagesplash=(ImageView)findViewById(R.id.imagesplash);



        Animation Imganim= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Imganim.setDuration(SPASH_TIME_OUT);
        imagesplash.startAnimation(Imganim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent=new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        },SPASH_TIME_OUT);
    }
}
