package com.adx2099.bakingapp;

import android.app.Application;

public class App extends Application {
    private static App m_Instance;

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



}
