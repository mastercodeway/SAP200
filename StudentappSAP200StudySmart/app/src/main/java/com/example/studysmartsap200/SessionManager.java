package com.example.studysmartsap200;

import android.content.Context;
import android.content.SharedPreferences;
// https://www.geeksforgeeks.org/session-management-in-android-with-example/ Kod som använts för att skapa en sessionmanager.
public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String USERNAME = "username";
    private SharedPreferences sharedPreferences;
    private static SessionManager instance;
    private SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static synchronized SessionManager getInstance(Context context){
        if(instance == null)
        {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }
    public void saveUsername(String userName){
        sharedPreferences.edit().putString(USERNAME, userName).apply();
    }
    public String getUsername(){
        return sharedPreferences.getString(USERNAME, null);
    }
    public boolean isLoggedIn(){
        return getUsername()!= null;
    }
    public void logOut(){
        sharedPreferences.edit().clear().apply();
    }

}
