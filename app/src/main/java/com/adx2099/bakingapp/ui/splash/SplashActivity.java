package com.adx2099.bakingapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.MainActivity;
import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

public class SplashActivity  extends AppCompatActivity implements SplashView {
    private final int DURATION_SPLASH = 2500;
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashPresenter = new SplashPresenter(this);
        App.setCurrenActivity(this);
        fetchData();
    }

    private void fetchData() {
        if(splashPresenter.dbCount()){
            initApp();
        }else{
            splashPresenter.fetchRecipes();
            initApp();
        }

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


    @Override
    public void recipeLoaded(List<RecipeResponse> recipeResponses) {

    }

    @Override
    public void recipesFailed(String msg) {

    }
}
