package com.chat.inomma.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.chat.inomma.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Vahe on 12/27/2017.
 */

public class AvatarImageLoader extends AsyncTask<String, Void, Bitmap> {

    private Listener mListener;
    private User mUser;

    public AvatarImageLoader(Listener listener, User user) {
        mUser = user;
        mListener = listener;
    }

    public interface Listener{

        void onImageLoaded(Bitmap bitmap, User user);
        void onError();
    }

    @Override
    protected Bitmap doInBackground(String... args) {

        try {

            return BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {

            mListener.onImageLoaded(bitmap, mUser);

        } else {

            mListener.onError();
        }
    }
}

