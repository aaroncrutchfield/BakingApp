package com.example.ioutd.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.utilities.GoogleImageSearch;
import com.example.ioutd.bakingapp.utilities.ImageJSONHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnFragmentInteractionListener,
        RecipeDetailsFragment.OnFragmentInteractionListener{

    public static final String RECIPE_ID = "recipeID";
    public static final String RECIPE_NAME = "recipeName";
    public static final String STEP_ID = "stepID";

    @Nullable
    @BindView(R.id.steps_container)
    FrameLayout stepsContainer;

    @Nullable
    @BindView(R.id.iv_recipe_image)
    ImageView ivRecipeImage;

    FragmentManager manager;
    AppViewModel appViewModel;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.recipe_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        // Don't use 0 as default value because it may actually exist
        int recipeID = intent.getIntExtra(RECIPE_ID, -1);
        final int stepID = intent.getIntExtra(STEP_ID, -1);
        recipeName = intent.getStringExtra(RECIPE_NAME);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipeName);
            loadRecipeImage();
        }

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        manager = getSupportFragmentManager();

        loadRecipeDetailsFragment(recipeID, recipeName);

        if (stepsContainer != null) {
            loadStepDetailsFragment(stepID);
        }

    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }

    @Override
    public void onFragmentInteraction(int stepID) {
        if (stepsContainer != null) {
            loadStepDetailsFragment(stepID);
        } else {
            startStepDetailsActivity(stepID);
        }
    }


    private void loadRecipeImage() {
        if (recipeName != null) {
            String url = GoogleImageSearch.buildSearchString(recipeName, 1, 1);
            Log.d("RecipeDetailsFragment", "loadRecipeImage.url: " + url);
            if (!url.equals("")) {
                // Query the api on the background
                AndroidNetworking.get(url)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String imageUrl = ImageJSONHandler.getImageUrl(response);

                                // In case there were no image results from Google
                                if (imageUrl.equals("") || ivRecipeImage == null) return;

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
    }

    private void loadRecipeDetailsFragment(int recipeID, String recipeName) {
        Fragment fragment = RecipeDetailsFragment.newInstance(recipeID, recipeName);

        manager.beginTransaction()
                .replace(R.id.recipe_details_container, fragment)
                .commit();
    }

    private void loadStepDetailsFragment(int stepID) {
        appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                Fragment fragment = StepDetailsFragment.newInstance(step);
                manager.beginTransaction()
                        .replace(R.id.steps_container, fragment)
                        .commit();
            }
        });
    }

    private void startStepDetailsActivity(int stepID) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(STEP_ID, stepID);
        startActivity(intent);
    }
}
