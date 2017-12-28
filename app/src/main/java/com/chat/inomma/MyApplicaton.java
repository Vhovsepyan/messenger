package com.chat.inomma;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.res.Configuration;

import com.chat.inomma.database.AppDatabase;
import com.chat.inomma.utils.AppLog;
import com.firebase.client.Firebase;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vahe on 12/27/2017.
 */

public class MyApplicaton extends Application {

    private String TAG = MyApplicaton.class.getCanonicalName();

    private static MyApplicaton sInstance ;
    private static ThreadPoolExecutor mThreadPool;


    public MyApplicaton(){

        mThreadPool = new ThreadPoolExecutor(4,
                                            4,
                                            60L,
                                                TimeUnit.SECONDS,
                                            new LinkedBlockingQueue<Runnable>());
    }

    private static class SingletonHolder {
        static final MyApplicaton HOLDER_INSTANCE = new MyApplicaton();
    }

    public static MyApplicaton getInstance(){
        return MyApplicaton.SingletonHolder.HOLDER_INSTANCE;
    }


    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        createAppDirIfNotExists();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "root").build();

        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static ThreadPoolExecutor getMainExcecutor(){
        return mThreadPool;
    }

    private void createAppDirIfNotExists(){

        String filename = AppConstants.AVATAR_DIR;
        String filePath = getApplicationContext().getFilesDir() + "/" + filename;
        File newFile1 = new File(filePath);
        if (!newFile1.exists()){
            File file = new File(getApplicationContext().getFilesDir(), filename);
            file.mkdir();
            AppLog.i(TAG, " 1111 not exists");
        }
    }
}
