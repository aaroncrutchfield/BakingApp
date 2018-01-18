package com.example.ioutd.bakingapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.ui.RecipeAdapter;
import com.example.ioutd.bakingapp.utilities.JSONDataHandler;
import com.example.ioutd.bakingapp.utilities.JSONResponseLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(this);

        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        AndroidNetworking.get(url)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
                        try {
                            recipeArrayList = JSONDataHandler.getRecipeArrayList(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecipeAdapter recipeAdapter = new RecipeAdapter(MainActivity.this, recipeArrayList);
                        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);

                        rvRecipes.setLayoutManager(layoutManager);
                        rvRecipes.setAdapter(recipeAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


}
