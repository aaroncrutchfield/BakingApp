package com.example.ioutd.bakingapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ioutd.bakingapp.data.AppDatabase;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.repositories.IngredientRepository;
import com.example.ioutd.bakingapp.ui.IngredientsAdapter;
import com.example.ioutd.bakingapp.utilities.GoogleImageSearch;
import com.example.ioutd.bakingapp.utilities.ImageJSONHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE_ID = "recipeID";
    public static final String RECIPE_NAME = "recipeName";

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

        // Construct the ViewModel
        AppViewModel viewModel = new AppViewModel();

        // Get the app database
        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);

        Intent intent = getIntent();

        // Don't use 0 as default value because it may actually exist
        int recipeID = intent.getIntExtra(RECIPE_ID, -1);
        String recipeName = intent.getStringExtra(RECIPE_NAME);

        actionBar.setTitle(recipeName);

        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(this);
        final IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this);

        IngredientRepository ingredientRepository = new IngredientRepository(appDatabase.ingredientDao());
        viewModel.getIngredientsByRecipeID(ingredientRepository, recipeID).observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                ingredientsAdapter.addIngredients(ingredients);
            }
        });

        rvIngredients.setLayoutManager(ingredientLayoutManager);
        rvIngredients.setAdapter(ingredientsAdapter);

//        ArrayList<Step> stepArrayList = (ArrayList<Step>) recipe.getSteps();
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(this);
//        StepAdapter stepAdapter = new StepAdapter(this, stepArrayList);

        rvSteps.setLayoutManager(stepLayoutManager);
//        rvSteps.setAdapter(stepAdapter);

        String url = GoogleImageSearch.buildSearchString(recipeName, 1, 1);

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
