package com.narcyber.mvpbasics.application;

import android.app.Application;

import com.narcyber.mvpbasics.db.RoomDb;

public class MvpBasicApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        RoomDb.getINSTANCE(this);

    }
}
