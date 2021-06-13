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

    public void writeString(String key, String value) {
        this.editor.putString(key, value).apply();
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    @Nullable
    public T readObject(String key, Class<T> cls) {
        Gson gson = new Gson();
        String g = sp.getString(key, "");
        try {
            if (!g.isEmpty()) {
                T obj = gson.fromJson(g, cls);
                return cls.cast(obj);
            }
        } catch (JsonParseException | ClassCastException ignored) {
        }
        return null;
    }


    public List<T> getAllCurrentObjects(Class<T> cls) {
        Map<String, ?> allEntries = sp.getAll();
        List<T> all = new ArrayList<T>();
        for (Map.Entry<String, ?> oMap : allEntries.entrySet()) {
            Object obj = readObject(oMap.getKey(), cls);
            if (obj == null || obj.toString().contains("null")) continue;
            if (!oMap.getKey().equalsIgnoreCase(ConstantHelper.LOCAL_EMAIL) &&
                    !oMap.getKey().equalsIgnoreCase(ConstantHelper.LOCAL_PASSWORD)) {
                try {
                    all.add(cls.cast(obj));
                } catch (ClassCastException e) {
                }
            }
        }
        return all;
    }

    public void removeObject(String key) {
        editor.remove(key).apply();
    }

    public void clear() {
        editor.clear().apply();
    }


}
