package com.narcyber.mvpbasics.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSaveHelper<T> {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public DataSaveHelper(Context context) {
        createDatabase(context);
    }

    private void createDatabase(Context context) {
        sp = context.getSharedPreferences(ConstantHelper.UID, Context.MODE_PRIVATE);
        editor = sp.edit();
    }


    public void writeObject(String key, T object, Class<T> cls) {
        Gson gson = new Gson();
        try {
            String o = gson.toJson(cls.cast(object));
            this.editor.putString(key, o);
            this.editor.apply();
        } catch (ClassCastException e) {
            return;
        }
    }

    @Nullable
    public T readObject(String key, Class<T> cls) {
        Gson gson = new Gson();
        String g = sp.getString(key, "");
        try {
            if (!g.isEmpty()) {
                return gson.fromJson(g, cls);
            }
        } catch (JsonParseException ignored) {
        }
        return null;
    }

    public List<T> getAllCurrentObjects(Class<T> cls) {
        Map<String, ?> allEntries = sp.getAll();
        List<T> all = new ArrayList<T>();
        for (Map.Entry<String, ?> oMap : allEntries.entrySet()) {
            T obj = readObject(oMap.getKey(), cls);
            if (obj != null) all.add(obj);
        }
        return all;
    }

    public void removeObject(String key) {
        editor.remove(key).commit();
    }

    public void clear() {
        editor.clear().apply();
    }


}
