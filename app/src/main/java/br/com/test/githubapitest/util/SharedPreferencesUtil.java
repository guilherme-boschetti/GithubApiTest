package br.com.test.githubapitest.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {

    private static SharedPreferencesUtil instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharedPreferencesUtil(Context context) {
        preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesUtil getInstance(Context context) {
        if (SharedPreferencesUtil.instance == null) {
            SharedPreferencesUtil.instance = new SharedPreferencesUtil(context);
        }
        return SharedPreferencesUtil.instance;
    }

    /**
     * Return String value of map
     *
     * @param key
     * @param returnOnNull
     * @return
     */
    public String getValue(String key, String returnOnNull){
        return preferences.getString(key, returnOnNull);
    }

    /**
     * Return  boolean value of map
     *
     * @param key
     * @param returnOnNull
     * @return
     */
    public boolean getValue(String key, boolean returnOnNull){
        return preferences.getBoolean(key, returnOnNull);
    }

    /**
     * Return int value of map
     *
     * @param key
     * @param returnOnNull
     * @return
     */
    public int getValue(String key, int returnOnNull){
        return preferences.getInt(key, returnOnNull);
    }

    /**
     * Set String value in map
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value){
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * Set boolean value in map
     *
     * @param key
     * @param value
     */
    public void setValue(String key, boolean value){
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Set int value in map
     *
     * @param key
     * @param value
     */
    public void setValue(String key, int value){
        editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Remove a key of map
     *
     * @param key
     */
    public void remove(String key){
        editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
