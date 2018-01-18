package com.example.ioutd.bakingapp.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ioutd on 1/16/2018.
 */

public class ImageJSONHandler {
    private static final String TAG = ImageJSONHandler.class.getSimpleName();

    private static final String ITEMS = "items";
    private static final String PAGEMAP = "pagemap";
    private static final String METATAGS = "metatags";

    private static final int META_TAGS_OBJECT = 0;
    private static final int ITEMS_OBJECT = 0;

    private static final String OG_IMAGE = "og:image";
    private static final String EMPTY_FALLBACK = "";


    public static String getImageUrl (JSONObject jsonObject) {
        String imageUrl = "";
        try {
            // Items array within base object
            JSONArray itemsArray = jsonObject.getJSONArray(ITEMS);

            // JSON object within items array
            JSONObject itemsObject = itemsArray.getJSONObject(ITEMS_OBJECT);

            // Pagemap object within items JSON object
            JSONObject pagemapObject = itemsObject.getJSONObject(PAGEMAP);

            // Metatags array within pagemap object
            JSONArray recipeArray = pagemapObject.getJSONArray(METATAGS);

            // JSON object within metatags array
            JSONObject recipeObject = recipeArray.getJSONObject(META_TAGS_OBJECT);

            // Image url within metatags JSON object
            imageUrl = recipeObject.optString(OG_IMAGE, EMPTY_FALLBACK);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getImageUrl() returned: " + imageUrl);
        return imageUrl;
    }
}
