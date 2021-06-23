package com.narcyber.mvpbasics.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.narcyber.mvpbasics.db.data.dao.UserDao
import com.narcyber.mvpbasics.helper.ConstantHelper
import com.narcyber.mvpbasics.model.User

@Database(entities = [User::class], version = 2)
abstract class RoomDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        lateinit var instance: RoomDb
        fun getINSTANCE(context: Context): RoomDb {
            return if (!this::instance.isInitialized) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java, ConstantHelper.ROOM_DB_NAME
                ).fallbackToDestructiveMigration().build().also {
                    instance = it
                }
            } else instance
        }

    }
}