package com.techjini.rxandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ashif on 17/3/17,March,2017
 * TechJini Solutions
 * Banglore,India
 */

public class CommonUtils {
    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
