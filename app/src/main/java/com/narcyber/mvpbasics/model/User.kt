package com.narcyber.mvpbasics.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.narcyber.mvpbasics.helper.ConstantHelper
import java.io.Serializable

@Entity(
    tableName = ConstantHelper.ROOM_USER_TABLE_NAME,
    indices = [Index(value = [ConstantHelper.ROOM_USER_COLUMN_USERNAME], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_EMAIL) var email: String,
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_NAME) var fullName: String,
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_USERNAME) var userName: String,
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_PASSWORD) var password: String,
) : Serializable

