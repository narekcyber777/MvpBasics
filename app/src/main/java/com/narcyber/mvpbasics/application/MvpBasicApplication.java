package com.narcyber.mvpbasics.application;

import android.app.Application;
import android.util.Log;

import com.narcyber.mvpbasics.db.RoomDb;

public class MvpBasicApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        RoomDb.getINSTANCE(this);
        Log.d("NAR", "main is created ");

    }
}
