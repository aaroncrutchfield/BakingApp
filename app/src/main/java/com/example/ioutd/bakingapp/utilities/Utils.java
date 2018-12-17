package com.example.ioutd.bakingapp.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Surface;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static Bitmap loadAssetImage(Context context, String recipeName) {
        String filename = recipeName + ".jpg";
        InputStream is = null;
        try {
            is = context.getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }

    // https://www.viralandroid.com/2016/01/how-to-check-android-device-screen-orientation.html
    public static int getScreenOrientation(Context context) {
        final int screenOrientation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        int spanCount;
        switch (screenOrientation) {
            case Surface.ROTATION_0:
                spanCount = 2;
                break;
            case Surface.ROTATION_90:
                spanCount = 4;
                break;
            case Surface.ROTATION_180:
                spanCount = 2;
                break;
            default:
                spanCount = 2;
        }

        return spanCount;
    }
}
