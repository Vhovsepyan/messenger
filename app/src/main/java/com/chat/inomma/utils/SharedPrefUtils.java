package com.chat.inomma.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vahe on 12/25/2017.
 */

public class SharedPrefUtils {

    public static void saveText(Context context, String key, String value) {
        SharedPreferences sPref = context.getApplicationContext().getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.putString(key, value);
        ed.apply();
    }

    public static String loadText(Context context, String key) {
        SharedPreferences sPref = context.getApplicationContext().getSharedPreferences(key, Context.MODE_PRIVATE);
        return sPref.getString(key, "");
    }
}
