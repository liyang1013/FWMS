package com.example.flwms.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {
    private static final String PREFS_NAME = "data";
    private static final String LAST_URL_KEY = "url";

    public static void saveLastUrl(Context context, String url) {
        SharedPreferences.Editor editor =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(LAST_URL_KEY, url);
        editor.apply();
    }

    public static String getLastUrl(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(LAST_URL_KEY, null);
    }
}