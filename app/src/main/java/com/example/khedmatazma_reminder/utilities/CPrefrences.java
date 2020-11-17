package com.example.khedmatazma_reminder.utilities;

import android.content.SharedPreferences;

import com.example.khedmatazma_reminder.G;

/**
 * Created by saeed on 2/6/2016.
 */
public class CPrefrences {
    public static String MAIL_BOX =  "mailbox";
    public static void save(String key, int value){
        SharedPreferences.Editor editor= G.preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }  public static void save(String key, String value){
        SharedPreferences.Editor editor= G.preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }  public static void save(String key, boolean value){
        SharedPreferences.Editor editor= G.preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static void save(String key, float value){
        SharedPreferences.Editor editor= G.preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

}