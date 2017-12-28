package com.chat.inomma;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chat.inomma.utils.AppLog;
import com.chat.inomma.utils.SharedPrefUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Vahe on 12/25/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private String TAG = BaseActivity.class.getCanonicalName();

    protected String myUid;
    boolean isLoggedIn;

    public BaseActivity() {

    }
/*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myUid = SharedPrefUtils.loadText(this,AppConstants.REGISTERED_USER_UID);

        isLoggedIn = !myUid.isEmpty();
        AppLog.i(TAG, myUid);

        try {
            createAppDirIfNotExists();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/






}