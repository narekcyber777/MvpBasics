package com.narcyber.mvpbasics.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class MyUtils {
    public  static  String KEY_ID="id";

    public  static  void withArgumentsMoveTo(Bundle bundle, FragmentActivity  from, Class cls){
        Intent intent=new Intent(from,cls);
        intent.putExtras(bundle);
        from.startActivity(intent);
    }
    public  static  void moveTo(FragmentActivity from,Class cls){
        Intent intent=new Intent(from,cls);
        from.startActivity(intent);
    }

    public  static  void moveToAndClear(FragmentActivity from,Class cls){
        Intent intent=new Intent(from,cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        from.startActivity(intent);
    }
    
    public  static  void showInToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }
}
