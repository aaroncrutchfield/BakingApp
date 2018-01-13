package com.example.ioutd.bakingapp.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ioutd on 1/12/2018.
 */

public class NetworkUtil {

    private static String TAG = NetworkUtil.class.getSimpleName();

    public static String getJSONResponse(String url) throws IOException {
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        Log.d(TAG, "doInBackground: response=" + response.body().toString());
        return response.body().string();
    }
}
