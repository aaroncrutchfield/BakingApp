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
import android.widget.FrameLayout;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnFragmentInteractionListener,
        RecipeDetailsFragment.OnFragmentInteractionListener{

    public static final String RECIPE_ID = "recipeID";
    public static final String RECIPE_NAME = "recipeName";

//    @BindView(R.id.rv_ingredients)
//    RecyclerView rvIngredients;
//
//    @BindView(R.id.rv_steps)
//    RecyclerView rvSteps;
//
//    @BindView(R.id.iv_recipe_image)
//    ImageView ivRecipeImage;

    @Nullable
    @BindView(R.id.steps_container)
    FrameLayout stepsContainer;


    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.recipe_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();

        // Don't use 0 as default value because it may actually exist
        int recipeID = intent.getIntExtra(RECIPE_ID, -1);
        String recipeName = intent.getStringExtra(RECIPE_NAME);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipeName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

            RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipeID, recipeName);

            manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.recipe_details_fragment, recipeDetailsFragment)
                    .commit();

//        if (stepsContainer != null) {
//
//            setupStepFragment(0);
//        }
//        else {
//            String url = GoogleImageSearch.buildSearchString(recipeName, 1, 1);
//
//            // Query the api on the background
//            AndroidNetworking.get(url)
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            String imageUrl = ImageJSONHandler.getImageUrl(response);
//
//                            // In case there were no image results from Google
//                            if (imageUrl.equals("")) return;
//
//                            Picasso.with(RecipeDetailsActivity.this)
//                                    .load(imageUrl)
//                                    .fit()
//                                    .into(ivRecipeImage);
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//
//                        }
//                    });
//        }
    }

//    private void setupStepFragment(final int caseKey) {
//        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
//        appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
//            @Override
//            public void onChanged(@Nullable Step step) {
//                if (step != null) {
//                    Fragment fragment = StepDetailsFragment.newInstance(step);
//                    switch (caseKey) {
//                        case 0:
//                            manager.beginTransaction()
//                                    .replace(R.id.step_fragment_container, fragment)
//                                    .commit();
//                            break;
//                        case 1:
//                            manager.beginTransaction()
//                                    .replace(R.id.step_fragment_container, fragment)
//                                    .commit();
//                            break;
//                        case 2:
//                            manager.beginTransaction()
//                                    .replace(R.id.step_fragment_container, fragment)
//                                    .commit();
//                            break;
//
//                    }
//                } else {
//                    if (caseKey == 1) {
//                        stepID--;
//                        Toast.makeText(RecipeDetailsActivity.this, "This is the last step", Toast.LENGTH_SHORT).show();
//                    }
//                    if (caseKey == 2) {
//                        stepID++;
//                        Toast.makeText(RecipeDetailsActivity.this, "This is the first step", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });
//    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }

    @Override
    public void onFragmentInteraction(int stepID) {
        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                Fragment fragment = StepDetailsFragment.newInstance(step);
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_container, fragment)
                        .commit();
            }
        });
    }
}
