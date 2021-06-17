package com.narcyber.mvpbasics.helper

import android.content.Context
import android.content.SharedPreferences

class DataSaveHelper(val context: Context) {
    val sp: SharedPreferences by lazy {
        context.getSharedPreferences(ConstantHelper.UID, Context.MODE_PRIVATE)
    }

    fun writeString(key: String, value: String?) {
        if (value == null) return
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String = ""): String = sp.getString(key, "")!!

    fun removeObject(key: String = "") {
        sp.edit().remove(key).apply()
    }

    fun clear() {
        sp.edit().clear().apply()
    }


}



