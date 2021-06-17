package com.narcyber.mvpbasics.application

import android.app.Application
import com.narcyber.mvpbasics.db.RoomDb

class MvpBasicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RoomDb.getINSTANCE(this)
    }
}