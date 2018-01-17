package com.example.ioutd.bakingapp.utilities;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ioutd on 1/16/2018.
 */

//http://www.basicsbehind.com/google-search-programmatically/
public class GoogleImageSearch {

    public static final String TAG = GoogleImageSearch.class.getSimpleName();

    // *********** Add your own Google API Key to use the Custom Search API ************* //
    final static String apiKey = Api.API_KEY;
    // *********** Add your own Google API Key to use the Custom Search API ************* //

    final static String customSearchEngineKey = "012158703685918192512:l9hgbsmw5eo";
    final static String searchURL = "https://www.googleapis.com/customsearch/v1?";

    public static String search(String pUrl) {
        try {
            URL url = new URL(pUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String buildSearchString(String searchString, int start, int numOfResults) {
        String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey + "&q=";

        // replace spaces in the search query with +
        String newSearchString = searchString.replace(" ", "%20");

        toSearch += newSearchString;

        // specify response format as json
        toSearch += "&alt=json";

        // specify starting result number
        toSearch += "&start=" + start;

        // specify the number of results you need from the starting position
        toSearch += "&num=" + numOfResults;

        Log.d(TAG, "buildSearchString: " + toSearch);
        return toSearch;
    }
}
