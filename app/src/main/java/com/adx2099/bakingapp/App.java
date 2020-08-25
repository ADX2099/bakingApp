package com.adx2099.bakingapp;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.adx2099.bakingapp.helper.BakingDbHelper;

public class App extends Application {
    private static App m_Instance;
    private static Fragment currentFrag;
    private static AppCompatActivity currentActivity;
    private static SQLiteDatabase db;
    private static BakingDbHelper dbh;
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        initBD();
    }

    public App(){
        super();
        m_Instance = this;
    }

    public static App getInstance() {
        if(m_Instance == null) {
            synchronized(App.class) {
                if(m_Instance == null) new App();
            }


        }
        return m_Instance;
    }

    public static void setCurrenActivity(AppCompatActivity arg){
        currentActivity = arg;
    }

    public static AppCompatActivity getCurrentActivity(){
        return currentActivity;
    }

    public static void setCurrentFragment(Fragment arg){
        currentFrag = arg;
    }

    public static Fragment getCurrentFragment(){
        return currentFrag;
    }

    private void initBD(){
        if(dbh == null)
            dbh = new BakingDbHelper(this);
        if(db == null)
            db = dbh.getWritableDatabase();
    }

    public static SQLiteDatabase getDb(){
        return db;
    }

    public static BakingDbHelper getBdh(){
        return dbh;
    }



}
