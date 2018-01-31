package com.example.ioutd.bakingapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.ui.IngredientsAdapter;
import com.example.ioutd.bakingapp.ui.StepAdapter;
import com.example.ioutd.bakingapp.utilities.GoogleImageSearch;
import com.example.ioutd.bakingapp.utilities.ImageJSONHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE = "recipe";

    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    @BindView(R.id.iv_recipe_image)
    ImageView ivRecipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();

        Recipe recipe = intent.getParcelableExtra(RECIPE);

        actionBar.setTitle(recipe.getName());

        ArrayList<Ingredient> ingredientArrayList = (ArrayList<Ingredient>) recipe.getIngredients();
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(this);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this, ingredientArrayList);

        rvIngredients.setLayoutManager(ingredientLayoutManager);
        rvIngredients.setAdapter(ingredientsAdapter);

        ArrayList<Step> stepArrayList = (ArrayList<Step>) recipe.getSteps();
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(this);
        StepAdapter stepAdapter = new StepAdapter(this, stepArrayList);

        rvSteps.setLayoutManager(stepLayoutManager);
        rvSteps.setAdapter(stepAdapter);

        String url = GoogleImageSearch.buildSearchString(recipe.getName(), 1, 1);

        // Query the api on the background
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String imageUrl = ImageJSONHandler.getImageUrl(response);

                        // In case there were no image results from Google
                        if (imageUrl.equals("")) return;

                        Picasso.with(RecipeDetailsActivity.this)
                                .load(imageUrl)
                                .fit()
                                .into(ivRecipeImage);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
