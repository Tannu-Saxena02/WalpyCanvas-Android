package com.example.walpy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    com.airbnb.lottie.LottieAnimationView lottieImage;
    TextView appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        lottieImage=findViewById(R.id.lottieImage);
        appName=findViewById(R.id.appname);
        appName.animate().translationY(-570).setDuration(2000).setStartDelay(2000);

        lottieImage.animate().translationX(2000).setDuration(2000).setStartDelay(2000);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }

        }, 5*1000); // wait for 5 seconds
    }
}