package com.chat.inomma.utils;

import android.util.Log;

import com.chat.inomma.AppConstants;

/**
 * Created by Vahe on 12/25/2017.
 */

public class AppLog {
    private static String onlyMyAppKey = "aaaaaaaa ";

    public static void i(String TAG, String msg) {
        if (AppConstants.IS_DEBUG) {
            Log.i(onlyMyAppKey + TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (AppConstants.IS_DEBUG) {
            Log.v(onlyMyAppKey + TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (AppConstants.IS_DEBUG) {
            Log.e(onlyMyAppKey + TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (AppConstants.IS_DEBUG) {
            Log.d(onlyMyAppKey + TAG, msg);
        }
    }
}
