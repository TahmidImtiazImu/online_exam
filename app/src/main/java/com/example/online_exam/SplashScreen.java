package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView splashImg;
    TextView splashTxt, smallTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide status bar
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        splashImg = findViewById(R.id.imgSplash);
        splashTxt = findViewById(R.id.txtSplash);
        smallTxt = findViewById(R.id.txtSmall);

        splashImg.setAnimation(topAnim);
        splashTxt.setAnimation(bottomAnim);
        smallTxt.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        },SPLASH_SCREEN);
    }
}