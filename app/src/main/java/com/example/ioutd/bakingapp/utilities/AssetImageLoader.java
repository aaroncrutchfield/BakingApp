package com.example.ioutd.bakingapp.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class AssetImageLoader {

    public Bitmap loadImage(Context context, String recipeName) {
        String filename = recipeName + ".jpg";
        InputStream is = null;
        try {
            is = context.getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }
}
