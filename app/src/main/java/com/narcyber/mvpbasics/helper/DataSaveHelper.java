package com.narcyber.mvpbasics.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSaveHelper {
    private static final DataSaveHelper INSTANCE = new DataSaveHelper();
    private final static String UID = "com.narcyber.mvpbasics.MyDB";
   private Context context;
   private  SharedPreferences sp;
   private  SharedPreferences.Editor editor;

    public static  DataSaveHelper getINSTANCE(Context context){
        INSTANCE.context=context;
        INSTANCE.createDatabase();
        return  INSTANCE;
    }

    private  void createDatabase() {
        INSTANCE.sp = INSTANCE.context.getSharedPreferences(UID, Context.MODE_PRIVATE);
        INSTANCE.editor = INSTANCE.sp.edit();
    }

    private  DataSaveHelper(){
        //
    }

    public  void writeObject(String key,Object object,Class cls){
        Gson gson=new Gson();
        try{
            String o=gson.toJson(cls.cast(object));
            INSTANCE.editor.putString(key,o);
            INSTANCE.editor.commit();
        }catch (ClassCastException e){
            return;
        }

    }

    @Nullable
    public  Object readObject(String key,Class cls){
        Gson gson=new Gson();
        String g=INSTANCE.sp.getString(key,"");
        if(!g.isEmpty()) {
            return gson.fromJson(g,cls);
        }
        return  null;
    }

    public List<Object> getAllCurrentObjects(Class cls){
        Map<String, ?> allEntries = sp.getAll();
            List all=new ArrayList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allEntries.forEach((k,v)->{
                   Object obj= readObject(k,cls);
                    if(obj!=null) all.add(obj);
            });
        }
        return  all;
    }

    public  void removeObject(String key){
        editor.remove(key).commit();
    }

    public  void printAllObjects(){
        Map<String, ?> allEntries = sp.getAll();

        System.out.println(allEntries.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allEntries.forEach((k,v)->{
                Log.d("NAR",k+" s "+v);
            });
        }
    }

}
