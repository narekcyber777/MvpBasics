package com.narcyber.mvpbasics.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.narcyber.mvpbasics.db.data.dao.UserDao;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.model.User;

@Database(entities = User.class, version = 1)
public abstract class RoomDb extends RoomDatabase {

    public static RoomDb INSTANCE;

    public static synchronized RoomDb getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDb.class, ConstantHelper.ROOM_DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();


}
