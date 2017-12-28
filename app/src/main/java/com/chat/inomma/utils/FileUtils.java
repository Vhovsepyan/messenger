package com.chat.inomma.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Vahe on 12/27/2017.
 */

public class FileUtils {
    public static void writeFileToStorage(Bitmap bmp, String filename ){
        FileOutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            out = new FileOutputStream(filename);
            bos = new BufferedOutputStream(out);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos); // bmp is your Bitmap instance

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
