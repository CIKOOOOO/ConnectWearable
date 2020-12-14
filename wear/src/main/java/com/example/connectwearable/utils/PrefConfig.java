package com.example.connectwearable.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.connectwearable.R;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    public void setUserLogin(boolean login, String userId, String smartphone_nodes_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), login);
        editor.putString(context.getString(R.string.pref_user_id), userId);
        editor.putString(context.getString(R.string.pref_smartphone_nodes_id), smartphone_nodes_id);
        editor.apply();
    }

    public void logOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_id), Constant.EMPTY);
        editor.putString(context.getString(R.string.pref_smartphone_nodes_id), Constant.EMPTY);
        editor.putBoolean(context.getString(R.string.pref_login_status), false);
        editor.apply();
    }

    public void setSmartphoneNodesID(String nodeID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_smartphone_nodes_id), nodeID);
        editor.apply();
    }

    public void setUserID(String userID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_id), userID);
        editor.apply();
    }

    public void setLogin(boolean login) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), login);
        editor.apply();
    }


    public boolean isLogin() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public String getUserID() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_id), Constant.EMPTY);
    }

}
