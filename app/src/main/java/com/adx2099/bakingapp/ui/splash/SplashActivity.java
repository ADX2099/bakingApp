package com.adx2099.bakingapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adx2099.bakingapp.MainActivity;

public class SplashActivity  extends AppCompatActivity {
    private final int DURATION_SPLASH = 2500;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApp();

    }

    private void initApp(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);

            }
        },DURATION_SPLASH);
    }



}
