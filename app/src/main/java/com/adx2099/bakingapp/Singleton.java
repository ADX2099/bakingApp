package com.adx2099.bakingapp;

import android.app.Application;

public class Singleton extends Application {
    private static Singleton m_Instance;

    public Singleton(){
        super();
        m_Instance = this;
    }

    public static Singleton getInstance() {
        if(m_Instance == null) {
            synchronized(Singleton.class) {
                if(m_Instance == null) new Singleton();
            }


        }
        return m_Instance;
    }

}
