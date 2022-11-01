package com.example.cit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView sLogo;
    Handler handler= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sLogo = findViewById(R.id.sLogo);
        handler =new Handler();

        sLogo.animate().translationY(-1600).setDuration(1000).setStartDelay(1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this,PermissionChecking.class);
                startActivity(i);
            }
        },1000);
    }


}
