package com.example.ioutd.bakingapp.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ioutd on 1/13/2018.
 */

public class JSONResponseLoader extends AsyncTaskLoader<String> {

    private static String TAG = JSONResponseLoader.class.getSimpleName();

    private String requestUrl;

    public JSONResponseLoader(Context context, Bundle args) {
        super(context);
        this.requestUrl = args.getString("request_url");
    }

    @Override
    public String loadInBackground() {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();

            Log.d(TAG, "loadInBackground: URL= " + requestUrl);
            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
