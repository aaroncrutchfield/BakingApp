package com.example.ioutd.bakingapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.utilities.JSONDataHandler;
import com.example.ioutd.bakingapp.utilities.NetworkUtil;
import com.example.ioutd.bakingapp.utilities.RecipeLoader;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    public static final int RECIPE_LOADER = 1;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
//        String url = "https://api.themoviedb.org/3/movie/343611?api_key=35a2c8b5ef8960c539ecc989877bc80e&append_to_response=reviews";

        Bundle bundle = new Bundle();
        bundle.putString("request_url", url);

        //Load the JSON from the URL
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> recipeLoader = loaderManager.getLoader(RECIPE_LOADER);

        if (recipeLoader == null)
            loaderManager.initLoader(RECIPE_LOADER, bundle, this).forceLoad();
        else
            loaderManager.restartLoader(RECIPE_LOADER, bundle, this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        try {
            recipeArrayList = JSONDataHandler.getRecipeArrayList(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onLoadFinished() returned: " + recipeArrayList.toString());
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
